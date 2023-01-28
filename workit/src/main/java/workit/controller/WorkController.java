package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import workit.dto.work.WorkCreateRequestDto;
import workit.service.WorkService;
import workit.util.ResponseCode;
import workit.util.ResponseMessage;
import workit.util.ResponseNonDataMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController {
    private final WorkService workService;

    @GetMapping("/date")
    public ResponseMessage getAllWorksByDateFilter(
            @RequestParam("start") String start, @RequestParam("end") String end, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_ALL_WORKIT_SUCCESS,
                workService.getWorksByDateFilter(userId, start, end)
        );
    }

    @GetMapping
    public ResponseMessage getAllWorks(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_ALL_WORKIT_SUCCESS,
                workService.getWorks(userId)
        );
    }

    @GetMapping("/{workId}")
    public ResponseMessage getWorkDetail(@PathVariable Long workId, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_WORKIT_DETAIL_SUCCESS,
                workService.getWorkDetail(userId, workId)
        );
    }

    @PostMapping
    public ResponseMessage createWork(
            @Valid @RequestBody WorkCreateRequestDto requestDto, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.WORK_CREATE_SUCCESS,
                workService.createWork(requestDto, userId)
        );
    }

    @PutMapping("/{workId}")
    public ResponseMessage modifyWork(
            @Valid @RequestBody WorkCreateRequestDto requestDto, @PathVariable Long workId, HttpServletRequest request
    ) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.WORK_MODIFY_SUCCESS,
                workService.modifyWork(requestDto, workId, userId)
        );
    }

    @DeleteMapping("/{workId}")
    public ResponseNonDataMessage deleteWork(@PathVariable Long workId, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());
        workService.deleteWork(workId, userId);

        return ResponseNonDataMessage.toResponseEntity(ResponseCode.DELETE_WORK_SUCCESS);
    }
}
