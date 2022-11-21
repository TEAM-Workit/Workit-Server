package workit.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import workit.entity.Project;

@Data
@AllArgsConstructor
public class ProjectCollectionResponseDto {
    private Long id;
    private String title;
    private Integer count;

    public ProjectCollectionResponseDto(Project project, int count) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.count = count;
    }
}

