package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.project.AllProjectCollectionDetailResponseDto;
import workit.dto.project.ProjectCollectionResponseDto;
import workit.dto.project.ProjectRequestDto;
import workit.dto.project.ProjectResponseDto;
import workit.dto.work.WorkResponseDto;
import workit.entity.Project;
import workit.entity.User;
import workit.entity.Work;
import workit.repository.ProjectRepository;
import workit.repository.UserRepository;
import workit.repository.WorkRepository;
import workit.util.CustomException;
import workit.util.ResponseCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static workit.validator.Validator.validateProjectTitleLength;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final WorkRepository workRepository;
    private final UserRepository userRepository;

    public ProjectResponseDto createProject(ProjectRequestDto request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        String title = request.getTitle();

        validateProjectTitleLength(title);

        Optional<Project> existProject = projectRepository.findByUserIdAndTitle(userId, title);

        if (existProject.isPresent()) {
            throw new CustomException(ResponseCode.ALREADY_EXIST_PROJECT_TITLE);
        }

        Project project = new Project(
                user,
                request.getTitle()
        );

        projectRepository.save(project);

        return new ProjectResponseDto(
                project.getId(),
                project.getTitle()
        );
    }

    public ProjectResponseDto modifyProject(Long userId, Long projectId, ProjectRequestDto request) {
        String title = request.getTitle();

        Project project = validateUserProject(userId, projectId);

        validateProjectTitleLength(title);

        Optional<Project> existProject = projectRepository.findByUserIdAndTitle(userId, title);
        if (existProject.isPresent() && !project.getTitle().equals(title)) {
            throw new CustomException(ResponseCode.ALREADY_EXIST_PROJECT_TITLE);
        }
        project.setTitle(title);
        projectRepository.save(project);

        return new ProjectResponseDto(
                project.getId(),
                project.getTitle()
        );
    }

    public void deleteProject(Long userId, Long projectId) {
        Project project = validateUserProject(userId, projectId);
        projectRepository.delete(project);
    }

    public List<ProjectResponseDto> getProjects(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        List<Project> projects = projectRepository.findByUser(user);
        List<ProjectResponseDto> responseDtos = new ArrayList<>();

        projects.stream()
                .sorted(Comparator.comparing(Project::getCreatedAt).reversed())
                .forEach(project -> {
                    ProjectResponseDto responseDto = new ProjectResponseDto(project);
                    responseDtos.add(responseDto);
                });

        return responseDtos;
    }

    public List<ProjectResponseDto> getRecentProjects(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        List<Project> projects = projectRepository.findByUser(user);
        List<ProjectResponseDto> responseDtos = new ArrayList<>();

        projects.stream()
                .sorted(Comparator.comparing(Project::getModifiedWorkAt).reversed()).limit(10)
                .forEach(project -> {
                    ProjectResponseDto responseDto = new ProjectResponseDto(project);
                    responseDtos.add(responseDto);
                });

        return responseDtos;
    }

    private Project validateUserProject(Long userId, Long projectId) {
        userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND));

        projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ResponseCode.PROJECT_NOT_FOUND));

        return projectRepository.findByUserIdAndId(userId, projectId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_USER_PROJECT)
        );
    }

    public List<ProjectCollectionResponseDto> getProjectCollection(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );

        List<Project> projects = projectRepository.findByUser(user);
        List<ProjectCollectionResponseDto> responseDtos = new ArrayList<>();

        projects.stream()
                .sorted(Comparator.comparing(Project::getTitle))
                .forEach(project -> {
                    ProjectCollectionResponseDto responseDto = new ProjectCollectionResponseDto(project);
                    responseDtos.add(responseDto);
                });

        return responseDtos;
    }

    static List<WorkResponseDto> sortCollection(List<Work> works) {
        List<WorkResponseDto> workResponseDtos = new ArrayList<>();

        works.stream().sorted(Comparator.comparing(Work::getDate).reversed()
                        .thenComparing(Comparator.comparing(Work::getCreatedAt).reversed()))
                .forEach(work -> {
                    WorkResponseDto workResponseDto = new WorkResponseDto(work);
                    workResponseDtos.add(workResponseDto);
                });

        return workResponseDtos;
    }

    static List<Date> stringToDateConverter(String start, String end) {
        Date startDate, endDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        try {
            startDate = simpleDateFormat.parse(start);
            endDate = simpleDateFormat.parse(end);
        } catch (ParseException e) {
            throw new CustomException(ResponseCode.INVALID_DATE_TYPE);
        }

        cal.setTime(endDate);
        cal.add(Calendar.DATE, 1);
        Date endPlusOne = cal.getTime();

        return new ArrayList<>(Arrays.asList(startDate, endPlusOne));
    }

    public AllProjectCollectionDetailResponseDto getProjectCollectionDetail(Long userId, Long projectId) {
        Project project = validateUserProject(userId, projectId);
        List<Work> works = workRepository.findByProject(project);
        List<Work> emptyList = Collections.emptyList();

        if (works.size() == 0) {
            return new AllProjectCollectionDetailResponseDto(project.getTitle(), sortCollection(emptyList));
        }

        return new AllProjectCollectionDetailResponseDto(project.getTitle(), sortCollection(works));
    }

    public AllProjectCollectionDetailResponseDto getProjectCollectionDetailByDateFilter
            (Long userId, Long projectId, String start, String end) {
        Project project = validateUserProject(userId, projectId);

        List<Date> convertDate = stringToDateConverter(start, end);
        Date startDate = convertDate.get(0);
        Date endPlusOne = convertDate.get(1);

        List<Work> works = workRepository.findByProjectAndDateBetween(project, startDate, endPlusOne);
        List<Work> emptyList = Collections.emptyList();

        if (works.size() == 0) {
            return new AllProjectCollectionDetailResponseDto(project.getTitle(), sortCollection(emptyList));
        }

        return new AllProjectCollectionDetailResponseDto(works.get(0).getProject().getTitle(), sortCollection(works));
    }
}
