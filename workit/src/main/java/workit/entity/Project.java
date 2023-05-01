package workit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 30)
    private String title;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    List<Work> works = new ArrayList<>();

    private LocalDateTime modifiedWorkAt = LocalDateTime.now();

    public Project(User user,String title) {
        this.user = user;
        this.title = title;

    }
}
