package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workit.service.AbilityService;
import workit.util.ResponseCode;
import workit.util.ResponseMessage;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ability")
public class AbilityController {
    private final AbilityService abilityService;

    @GetMapping
    public ResponseEntity<ResponseMessage> getAllWorks(HttpServletRequest request) {
        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_ALL_ABILITY_SUCCESS,
                abilityService.getAllAbilities()
        );
    }
}
