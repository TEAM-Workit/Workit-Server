package workit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workit.entity.Project;
import workit.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUser(User user);
    Optional<Project> findByUserAndTitle(User user, String title);
    Optional<Project> findByUserIdAndTitle(Long userId, String title);
    Optional<Project> findByUserIdAndId(Long userId, Long projectId);
}
