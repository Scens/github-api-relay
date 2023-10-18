package brzezinski.rafal.githubapirelay.repository;

import brzezinski.rafal.githubapirelay.model.UserRequestCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestCountRepository extends JpaRepository<UserRequestCount, String> {
}
