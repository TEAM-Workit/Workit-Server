package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.work.WorkCreateRequestDto;
import workit.dto.work.WorkCreateResponseDto;
import workit.dto.work.WorkRequestDto;
import workit.entity.*;
import workit.repository.*;
import workit.util.CustomException;
import workit.util.ResponseCode;
import workit.validator.Validator;

import java.util.ArrayList;
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

    public WorkCreateResponseDto createWork(WorkCreateRequestDto request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        Project project = getProject(user, request.getProjectTitle());

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

        return new WorkCreateResponseDto(work);
    }

    public WorkCreateResponseDto modifyWork(WorkCreateRequestDto request, Long workId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        Work work = workRepository.findById(workId).orElseThrow(
                () -> new CustomException(ResponseCode.WORK_NOT_FOUND)
        );
        Validator.validateUsersWork(work, user);

        Project project = getProject(user, request.getProjectTitle());

        WorkRequestDto workRequestDto = new WorkRequestDto(
                project,
                request.getWorkTitle(),
                request.getDescription(),
                request.getDate(),
                makeWorkAbilities(work, abilityRepository.findAllById(request.getAbilities()))
        );
        work.save(workRequestDto);
        workRepository.save(work);

        return new WorkCreateResponseDto(work);
    }

    public void deleteWork(Long workId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
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
                    Project proj = new Project(projectTitle, user);
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
