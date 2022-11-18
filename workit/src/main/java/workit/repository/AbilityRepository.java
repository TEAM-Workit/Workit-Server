package workit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workit.entity.Ability;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
}
