package workit.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Ability extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ability_id")
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private AbilityType abilityType;

    @OneToMany(mappedBy = "ability", cascade = CascadeType.ALL)
    private List<WorkAbility> workAbilities = new ArrayList<>();
}
