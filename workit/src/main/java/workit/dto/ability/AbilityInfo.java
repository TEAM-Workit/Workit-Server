package workit.dto.ability;

import lombok.Data;
import workit.entity.Ability;
import workit.entity.AbilityType;

@Data
public class AbilityInfo {
    private Long abilityId;
    private String abilityName;
    private AbilityType abilityType;

    public AbilityInfo(Ability ability) {
        this.abilityId = ability.getId();
        this.abilityName = ability.getName();
        this.abilityType = ability.getAbilityType();
    }
}
