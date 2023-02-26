package journal.service;

import journal.repos.EntryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EntryServiceTest {

    @Mock
    private EntryRepository entryRepository;

    @InjectMocks
    private EntryService entryService;

}
