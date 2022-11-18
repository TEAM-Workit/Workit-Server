package workit.dto.work;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkCreateRequestDto {
    @NotNull
    private Date date;
    @NotNull
    private String projectTitle;
    @NotNull
    private String workTitle;
    @NotNull
    private String description;
    @NotNull
    private List<Long> abilities;
}
