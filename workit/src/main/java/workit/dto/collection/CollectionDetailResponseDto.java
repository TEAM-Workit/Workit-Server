package workit.dto.collection;

import lombok.Data;
import workit.entity.Work;

import java.util.Date;

@Data
public class CollectionDetailResponseDto {
    private Long workId;
    private String projectTitle;
    private Date workDate;
    private String workTitle;
    private String workDescription;
    private String mainAbilityName;
    private Integer count;

    public CollectionDetailResponseDto(Work work) {
        this.workId = work.getId();
        this.projectTitle = work.getProject().getTitle();
        this.workDate = work.getDate();
        this.workTitle = work.getTitle();
        this.workDescription = work.getDescription();
        this.mainAbilityName = work.getWorkAbilities().get(0).getAbility().getName();
        this.count = work.getWorkAbilities().size() - 1;
    }
}
