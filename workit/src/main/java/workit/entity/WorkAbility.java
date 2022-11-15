package workit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class WorkAbility extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_ability_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "work_id")
    private Work work;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ability_id")
    private Ability ability;

    @Column
    private boolean isDeleted;
}
