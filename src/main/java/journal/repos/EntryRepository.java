package journal.repos;

import journal.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EntryRepository extends JpaRepository<Entry, Long> {

    boolean existsByTitleIgnoreCase(String title);

    Optional<Entry> findByTitle(String title);
}
