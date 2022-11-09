package workit.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Tag extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private TagType tagType;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<WorkTag> workTags = new ArrayList<>();
}
