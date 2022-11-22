package workit.dto.ability;

import lombok.Data;
import workit.dto.collection.CollectionDetailResponseDto;

import java.util.List;

@Data
public class AllAbilityCollectionDetailResponseDto {
    private String abilityName;
    private List<CollectionDetailResponseDto> works;

    public AllAbilityCollectionDetailResponseDto(String abilityName, List<CollectionDetailResponseDto> works) {
        this.abilityName = abilityName;
        this.works = works;
    }
}
