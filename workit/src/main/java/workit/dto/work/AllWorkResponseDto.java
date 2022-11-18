package workit.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllWorkResponseDto {
    List<WorkResponseDto> works;
}
