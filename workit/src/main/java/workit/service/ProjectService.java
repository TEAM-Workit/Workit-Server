package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.project.ProjectRequestDto;
import workit.dto.project.ProjectResponseDto;
import workit.entity.Project;
import workit.entity.User;
import workit.repository.ProjectRepository;
import workit.repository.UserRepository;
import workit.util.CustomException;
import workit.util.ResponseCode;

import java.util.Optional;

import static workit.validator.Validator.validateProjectTitleLength;
import static workit.validator.Validator.validateProjectTitleNull;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
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

        userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND));

        Project project = projectRepository.findByUserIdAndId(userId, projectId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_USER_PROJECT)
        );

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
}
