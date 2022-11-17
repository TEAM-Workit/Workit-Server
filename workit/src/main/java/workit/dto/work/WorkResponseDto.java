package workit.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;
import workit.entity.Work;

import java.util.Date;

@Data
public class WorkResponseDto {
    private Long workId;
    private String projectTitle;
    private Date date;
    private String workTitle;
    private String description;
    private String mainAbilityName;
    private int count;

    public WorkResponseDto(Work work) {
        this.workId = work.getId();
        this.projectTitle = work.getProject().getTitle();
        this.date = work.getDate();
        this.workTitle = work.getTitle();
        this.description = work.getDescription();
        this.mainAbilityName = work.getWorkAbilities().get(0).getAbility().getName();
        this.count = (int) ((long) work.getWorkAbilities().size() - 1);
    }
}
