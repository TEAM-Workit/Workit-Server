package workit.dto.ability;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllAbilitiesResponseDto {
    private List<AbilityInfo> abilities;
}
