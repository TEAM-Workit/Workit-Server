package workit.dto.work;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkCreateRequestDto {
    @NotNull
    private Date date;
    @NotNull
    private Long projectId;
    @NotNull
    private String workTitle;
    @NotNull
    private String description;
    @NotNull
    private List<Long> abilities;
}
