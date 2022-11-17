package workit.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ProjectRequestDto {
    @NotNull
    String title;
}
