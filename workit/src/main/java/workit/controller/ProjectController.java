package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import workit.dto.project.ProjectRequestDto;
import workit.service.ProjectService;
import workit.util.ResponseCode;
import workit.util.ResponseMessage;
import workit.util.ResponseNonDataMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseMessage createProject(
            @Valid @RequestBody ProjectRequestDto projectRequestDto, HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.CREATE_PROJECT_SUCCESS,
                projectService.createProject(projectRequestDto, userId)
        );
    }

    @PatchMapping("/{projectId}")
    public ResponseMessage modifyProject(@PathVariable Long projectId,
                                         @Valid @RequestBody ProjectRequestDto projectRequestDto, HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.MODIFY_PROJECT_SUCCESS,
                projectService.modifyProject(userId, projectId, projectRequestDto)
        );
    }

    @DeleteMapping("/{projectId}")
    public ResponseNonDataMessage deleteProject(@PathVariable Long projectId, HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        projectService.deleteProject(userId, projectId);

        return ResponseNonDataMessage.toResponseEntity(
                ResponseCode.DELETE_PROJECT_SUCCESS
        );
    }

    @GetMapping("/all")
    public ResponseMessage getProjects(HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_PROJECTS_SUCCESS,
                projectService.getProjects(userId)
        );
    }

    @GetMapping("/recent")
    public ResponseMessage getRecentProjects(HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_RECENT_PROJECTS_SUCCESS,
                projectService.getRecentProjects(userId)
        );
    }

    @GetMapping("/collection")
    public ResponseMessage getProjectCollection(HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_PROJECT_COLLECTION,
                projectService.getProjectCollection(userId)
        );
    }

    @GetMapping("/{projectId}/collection")
    public ResponseMessage getProjectCollectionDetail(@PathVariable Long projectId, HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_PROJECT_COLLECTION_DETAIL,
                projectService.getProjectCollectionDetail(userId, projectId)
        );
    }

    @GetMapping("/{projectId}/collection/date")
    public ResponseMessage getProjectCollectionDetailByDateFilter(@PathVariable Long projectId,
                                                                  @RequestParam("start") String start, @RequestParam("end") String end,
                                                                  HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_PROJECT_COLLECTION_DETAIL_BY_DATE_FILTER,
                projectService.getProjectCollectionDetailByDateFilter(userId, projectId, start, end)
        );
    }
}
