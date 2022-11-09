package workit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
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

    @Column(length = 20)
    private String title;

    @Column
    private String description;

    @Column
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
    private List<WorkTag> workTags = new ArrayList<>();
}
