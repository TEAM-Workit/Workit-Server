package workit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import workit.dto.work.WorkRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Work extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
    private List<WorkAbility> workAbilities = new ArrayList<>();

    public void save(WorkRequestDto request) {
        this.project = request.getProject();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.date = request.getDate();
        this.workAbilities = request.getWorkAbilities();
    }
}
