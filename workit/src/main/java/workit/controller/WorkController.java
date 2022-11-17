package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workit.dto.work.WorkCreateRequestDto;
import workit.service.WorkService;
import workit.util.ResponseCode;
import workit.util.ResponseMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController {
    private final WorkService workService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createWork(
            @Valid @RequestBody WorkCreateRequestDto requestDto, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();

        return ResponseMessage.toResponseEntity(
                ResponseCode.WORK_CREATE_SUCCESS,
                workService.createWork(requestDto, email)
        );
    }
}
