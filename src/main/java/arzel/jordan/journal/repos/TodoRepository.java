package arzel.jordan.journal.repos;

import arzel.jordan.journal.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, Long> {

    boolean existsByTitleIgnoreCase(String title);

}
