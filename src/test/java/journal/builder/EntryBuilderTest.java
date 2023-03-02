package journal.builder;

import journal.domain.Entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntryBuilderTest {
    @Test
    void buildEntry() {
        Entry entry = new EntryBuilder()
                .withId(1L)
                .withTitle("title")
                .withContent("content")
                .build();
        Assertions.assertEquals(1L, entry.getId());
        Assertions.assertEquals("title", entry.getTitle());
        Assertions.assertEquals("content", entry.getContent());
    }

}
