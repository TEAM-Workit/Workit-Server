package workit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workit.entity.Work;
import workit.entity.WorkAbility;

import java.util.List;

@Repository
public interface WorkAbilityRepository extends JpaRepository<WorkAbility, Long> {
    List<WorkAbility> findByWork(Work work);
}