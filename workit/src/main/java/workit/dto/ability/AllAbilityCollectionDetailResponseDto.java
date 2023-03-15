package workit.dto.ability;

import lombok.Data;
import workit.dto.work.WorkResponseDto;

import java.util.List;

@Data
public class AllAbilityCollectionDetailResponseDto {
    private String abilityName;
    private List<WorkResponseDto> works;

    public AllAbilityCollectionDetailResponseDto(String abilityName, List<WorkResponseDto> works) {
        this.abilityName = abilityName;
        this.works = works;
    }
}
