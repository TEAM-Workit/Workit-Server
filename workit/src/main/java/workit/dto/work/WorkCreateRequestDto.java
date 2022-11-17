package workit.dto.work;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkCreateRequestDto {
    private Date date;
    private String projectTitle;
    private String workTitle;
    private String description;
    private List<Long> abilities;
}
