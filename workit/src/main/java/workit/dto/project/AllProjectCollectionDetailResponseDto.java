package workit.dto.project;

import lombok.Data;
import workit.dto.collection.CollectionDetailResponseDto;

import java.util.List;

@Data
public class AllProjectCollectionDetailResponseDto {
    private String projectTitle;
    private List<CollectionDetailResponseDto> works;

    public AllProjectCollectionDetailResponseDto(String projectTitle, List<CollectionDetailResponseDto> works) {
        this.projectTitle = projectTitle;
        this.works = works;
    }
}
