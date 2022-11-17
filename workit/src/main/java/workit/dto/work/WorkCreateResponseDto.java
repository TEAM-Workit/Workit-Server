package workit.dto.work;

import lombok.Data;
import workit.entity.Work;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WorkCreateResponseDto {
    private Long workId;
    private String projectTitle;
    private String workTitle;
    private String description;
    private Date date;
    private List<Long> abilities;

    public WorkCreateResponseDto(Work work) {
        this.workId = work.getId();
        this.projectTitle = work.getProject().getTitle();
        this.workTitle = work.getTitle();
        this.description = work.getDescription();
        this.date = work.getDate();
        this.abilities = work.getWorkAbilities().stream()
                .map(workAbility -> workAbility.getAbility().getId())
                .collect(Collectors.toList());
    }
}
