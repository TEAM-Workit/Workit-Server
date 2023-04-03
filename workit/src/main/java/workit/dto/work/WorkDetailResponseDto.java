package workit.dto.work;

import lombok.Data;
import workit.dto.ability.AbilityInfo;
import workit.entity.Project;
import workit.entity.Work;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WorkDetailResponseDto {
    private Long workId;
    private Project project;
    private String workTitle;
    private String description;
    private Date date;
    private List<AbilityInfo> abilities;

    public WorkDetailResponseDto(Work work) {
        this.workId = work.getId();
        this.project = new Project(work.getProject());
        this.workTitle = work.getTitle();
        this.description = work.getDescription();
        this.date = work.getDate();
        this.abilities = work.getWorkAbilities().stream()
                .map(workAbility -> new AbilityInfo(workAbility.getAbility()))
                .collect(Collectors.toList());
    }

    @Data
    private static class Project {
        private Long projectId;
        private String projectTitle;

        public Project(final workit.entity.Project project) {
            this.projectId = project.getId();
            this.projectTitle = project.getTitle();
        }
    }
}
