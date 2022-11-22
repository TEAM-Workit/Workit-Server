package workit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ResponseMessage> getAllWorks() {
        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_ALL_ABILITY_SUCCESS,
                abilityService.getAllAbilities()
        );
    }

    @GetMapping("/collection")
    public ResponseEntity<ResponseMessage> getAbilityCollection(HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());


        abilityService.getAbilityCollection(userId);
        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_ABILITY_COLLECTION,
                abilityService.getAbilityCollection(userId)
        );
    }

    @GetMapping("/{abilityId}/collection")
    public ResponseEntity<ResponseMessage> getAbilityCollectionDetail(@PathVariable Long abilityId, HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_ABILITY_COLLECTION_DETAIL,
                abilityService.getAbilityCollectionDetail(userId, abilityId)
        );
    }

    @GetMapping("/{abilityId}/collection/date")
    public ResponseEntity<ResponseMessage> getAbilityCollectionDetailByDateFilter(@PathVariable Long abilityId,
                                                                                  @RequestParam("start") String start, @RequestParam("end") String end,
                                                                                  HttpServletRequest request) {

        Long userId = Long.valueOf(request.getUserPrincipal().getName());

        return ResponseMessage.toResponseEntity(
                ResponseCode.GET_ABILITY_COLLECTION_DETAIL_BY_DATE_FILTER,
                abilityService.getAbilityCollectionDetailByDateFilter(userId, abilityId, start, end)
        );
    }
}
