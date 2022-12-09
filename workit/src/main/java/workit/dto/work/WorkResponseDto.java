package workit.dto.work;

import lombok.Data;
import workit.dto.ability.AbilityInfo;
import workit.entity.Work;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WorkResponseDto {
    private Long workId;
    private String projectTitle;
    private Date date;
    private String workTitle;
    private String description;
    private List<AbilityInfo> abilityInfos;

    public WorkResponseDto(Work work) {
        this.workId = work.getId();
        this.projectTitle = work.getProject().getTitle();
        this.date = work.getDate();
        this.workTitle = work.getTitle();
        this.description = work.getDescription();
        this.abilityInfos = work.getWorkAbilities().stream()
                .map(workAbility -> new AbilityInfo(workAbility.getAbility()))
                .collect(Collectors.toList());
    }
}
