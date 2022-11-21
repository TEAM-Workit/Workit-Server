package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.project.*;
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
import static workit.validator.Validator.validateProjectTitleNull;

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

        validateProjectTitleNull(title);
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

        validateProjectTitleNull(title);
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
                .sorted(Comparator.comparing(Project::getTitle))
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
                .sorted(Comparator.comparing(Project::getCreatedAt).reversed()).limit(10)
                .sorted(Comparator.comparing(Project::getTitle))
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

    public AllProjectCollectionDetailResponseDto getProjectCollectionDetail(Long userId, Long projectId) {
        Project project = validateUserProject(userId, projectId);
        List<Work> projectWorks = workRepository.findByProject(project);

        return sortWorkCollection(projectWorks);
    }

    public AllProjectCollectionDetailResponseDto getProjectCollectionDetailByDateFilter
            (Long userId, Long projectId, String start, String end) {
        Project project = validateUserProject(userId, projectId);

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

        List<Work> projectWorks = workRepository.findByProjectAndDateBetween(project, startDate, endPlusOne);

        return sortWorkCollection(projectWorks);
    }

    private AllProjectCollectionDetailResponseDto sortWorkCollection(List<Work> projectWorks) {
        List<ProjectCollectionDetailResponseDto> works = new ArrayList<>();

        projectWorks.stream()
                .sorted(Comparator.comparing(Work::getDate)
                        .thenComparing(Comparator.comparing(Work::getCreatedAt).reversed()))
                .forEach(work -> {
                    ProjectCollectionDetailResponseDto responseDto = new ProjectCollectionDetailResponseDto(work);
                    works.add(responseDto);
                });

        return new AllProjectCollectionDetailResponseDto(projectWorks.get(0).getProject().getTitle(), works);
    }
}
