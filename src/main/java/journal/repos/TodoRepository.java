package journal.repos;

import journal.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, Long> {

    boolean existsByTitleIgnoreCase(String title);

}
