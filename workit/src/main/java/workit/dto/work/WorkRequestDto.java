package workit.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;
import workit.entity.Project;
import workit.entity.WorkAbility;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkRequestDto {
    private Project project;
    private String title;
    private String description;
    private Date date;
    private List<WorkAbility> workAbilities;
}
