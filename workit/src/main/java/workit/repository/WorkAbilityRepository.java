package workit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workit.entity.WorkAbility;

public interface WorkAbilityRepository extends JpaRepository<WorkAbility, Long> {
}
