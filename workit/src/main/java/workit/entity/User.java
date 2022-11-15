package workit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(length = 20)
    private String nickname;

    @Column(length = 50)
    private String email;

    @Column
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();
}
