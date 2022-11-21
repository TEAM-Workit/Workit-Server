package workit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workit.entity.Project;
import workit.entity.Work;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByProject(Project project);
    List<Work> findByProjectAndDateBetween(Project project, Date start, Date end);
}
