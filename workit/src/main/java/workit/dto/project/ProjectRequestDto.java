package workit.dto.project;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class ProjectRequestDto {
    @NotNull
    String title;
}


