package workit.dto.project;

import lombok.Data;

import java.util.List;

@Data
public class AllProjectCollectionDetailResponseDto {
    private String projectTitle;
    private List<ProjectCollectionDetailResponseDto> works;

    public AllProjectCollectionDetailResponseDto(String projectTitle, List<ProjectCollectionDetailResponseDto> works) {
        this.projectTitle = projectTitle;
        this.works = works;
    }
}
