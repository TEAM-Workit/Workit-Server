package workit.dto.project;

import lombok.Data;
import workit.dto.work.WorkResponseDto;

import java.util.List;

@Data
public class AllProjectCollectionDetailResponseDto {
    private String projectTitle;
    private List<WorkResponseDto> works;

    public AllProjectCollectionDetailResponseDto(String projectTitle, List<WorkResponseDto> works) {
        this.projectTitle = projectTitle;
        this.works = works;
    }
}
