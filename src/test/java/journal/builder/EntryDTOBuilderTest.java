package journal.builder;

import journal.model.EntryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EntryDTOBuilderTest {

    @Test
    void buildEntryDTO() {
        EntryDTO entryDTO = new EntryDTOBuilder()
                .withId(1L)
                .withTitle("title")
                .withContent("content")
                .build();
        Assertions.assertEquals(1L, entryDTO.getId());
        Assertions.assertEquals("title", entryDTO.getTitle());
        Assertions.assertEquals("content", entryDTO.getContent());
    }

}
