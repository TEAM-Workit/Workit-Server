package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.work.*;
import workit.entity.*;
import workit.repository.*;
import workit.util.CustomException;
import workit.util.ResponseCode;
import workit.validator.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final WorkRepository workRepository;
    private final WorkAbilityRepository workAbilityRepository;
    private final AbilityRepository abilityRepository;

    public AllWorkResponseDto getWorksByDateFilter(Long userId, String start, String end) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat.parse(start);
            Date endDate = simpleDateFormat.parse(end);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE, 1);
            String endPlusOne = simpleDateFormat.format(calendar.getTime());
            Date finalEndDate = simpleDateFormat.parse(endPlusOne);

            List<Project> projects = projectRepository.findByUser(user);
            List<WorkResponseDto> workResponseDtos = new ArrayList<>();
            projects.forEach(project -> {
                workRepository.findByProject(project).stream()
                        .filter(work -> work.getDate().equals(startDate) || work.getDate().after(startDate))
                        .filter(work -> work.getDate().equals(finalEndDate) || work.getDate().before(finalEndDate))
                        .forEach(work -> {
                            WorkResponseDto workResponseDto = new WorkResponseDto(work);
                            workResponseDtos.add(workResponseDto);
                        });
            });
            return new AllWorkResponseDto(workResponseDtos);
        } catch (Exception e) {
            throw new CustomException(ResponseCode.INVALID_DATE_TYPE);
        }
    }

    public AllWorkResponseDto getWorks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        List<Project> projects = projectRepository.findByUser(user);
        List<WorkResponseDto> workResponseDtos = new ArrayList<>();
        projects.forEach(project -> {
            workRepository.findByProject(project)
                    .forEach(work -> {
                        WorkResponseDto workResponseDto = new WorkResponseDto(work);
                        workResponseDtos.add(workResponseDto);
                    });
        });

        return new AllWorkResponseDto(workResponseDtos);
    }

    public WorkDetailResponseDto getWorkDetail(Long userId, Long workId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );
        Work work = workRepository.findById(workId).orElseThrow(
                () -> new CustomException(ResponseCode.WORK_NOT_FOUND)
        );
        Validator.validateUsersWork(work, user);

        return new WorkDetailResponseDto(work);
    }

    public WorkDetailResponseDto createWork(WorkCreateRequestDto request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        Project project = getProject(user, request.getProjectTitle());

        if (request.getAbilities().isEmpty()) {
            throw new CustomException(ResponseCode.NO_ABILITIES);
        }

        Validator.validateWorkTitleLength(request.getWorkTitle());
        Validator.validateWorkDescriptionLength(request.getDescription());

        Work work = new Work();
        WorkRequestDto workRequestDto = new WorkRequestDto(
                project,
                request.getWorkTitle(),
                request.getDescription(),
                request.getDate(),
                makeWorkAbilities(work, abilityRepository.findAllById(request.getAbilities()))
        );
        work.save(workRequestDto);
        workRepository.save(work);

        return new WorkDetailResponseDto(work);
    }

    public WorkDetailResponseDto modifyWork(WorkCreateRequestDto request, Long workId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        Work work = workRepository.findById(workId).orElseThrow(
                () -> new CustomException(ResponseCode.WORK_NOT_FOUND)
        );
        Validator.validateUsersWork(work, user);

        Project project = getProject(user, request.getProjectTitle());

        if (request.getAbilities().isEmpty()) {
            throw new CustomException(ResponseCode.NO_ABILITIES);
        }

        WorkRequestDto workRequestDto = new WorkRequestDto(
                project,
                request.getWorkTitle(),
                request.getDescription(),
                request.getDate(),
                makeWorkAbilities(work, abilityRepository.findAllById(request.getAbilities()))
        );
        work.save(workRequestDto);
        workRepository.save(work);

        return new WorkDetailResponseDto(work);
    }

    public void deleteWork(Long workId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        Work work = workRepository.findById(workId).orElseThrow(
                () -> new CustomException(ResponseCode.WORK_NOT_FOUND)
        );
        Validator.validateUsersWork(work, user);

        workRepository.delete(work);
    }

    private Project getProject(User user, String projectTitle) {
        return projectRepository.findByUserAndTitle(user, projectTitle)
                .orElseGet(() -> {
                    Validator.validateProjectTitleLength(projectTitle);
                    Project proj = new Project(user, projectTitle);
                    projectRepository.save(proj);
                    return proj;
                });
    }

    private List<WorkAbility> makeWorkAbilities(Work work, List<Ability> abilities) {
        List<WorkAbility> workAbilities = new ArrayList<>();
        for (Ability ability: abilities) {
            WorkAbility workAbility = new WorkAbility(work, ability);
            workAbilityRepository.save(workAbility);
            workAbilities.add(workAbility);
        }

        return workAbilities;
    }
}
