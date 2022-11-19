package workit.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import workit.entity.Project;

@Data
@AllArgsConstructor
public class ProjectResponseDto {
    private Long id;
    private String title;

    public ProjectResponseDto(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
    }
}
