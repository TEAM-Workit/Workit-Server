package workit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workit.entity.Work;

import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
}
