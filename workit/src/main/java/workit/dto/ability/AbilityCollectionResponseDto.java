package workit.dto.ability;

import lombok.AllArgsConstructor;
import lombok.Data;
import workit.entity.Ability;

@Data
@AllArgsConstructor
public class AbilityCollectionResponseDto {
    private Long id;
    private String name;
    private Integer count;

    public AbilityCollectionResponseDto(Ability ability, int count) {
        this.id = ability.getId();
        this.name = ability.getName();
        this.count = count;
    }
}

