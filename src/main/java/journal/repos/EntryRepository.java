package journal.repos;

import journal.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntryRepository extends JpaRepository<Entry, Long> {

    boolean existsByTitleIgnoreCase(String title);

}
