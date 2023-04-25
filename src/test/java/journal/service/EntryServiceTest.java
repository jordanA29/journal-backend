package journal.service;

import journal.builder.EntryBuilder;
import journal.builder.EntryDTOBuilder;
import journal.domain.Entry;
import journal.model.EntryDTO;
import journal.repos.EntryRepository;
import journal.util.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntryServiceTest {

    @Mock
    private EntryRepository entryRepository;

    @InjectMocks
    private EntryService entryService;

    private static final Long ID = 1L;
    private EntryDTOBuilder entryDTOBuilder;
    private EntryBuilder entryBuilder;

    @BeforeEach
    void setUp() {
        entryDTOBuilder = new EntryDTOBuilder();
        entryBuilder = new EntryBuilder();
    }

    // read
    @Test
    void findAll_ReturnEmptyList() {
        when(entryRepository.findAll(any(Sort.class))).thenReturn(List.of());
        final List<EntryDTO> entries = entryService.findAll();
        assertEquals(0, entries.size());
    }

    @Test
    void findAll_ReturnList() {
        final Entry entry1 = entryBuilder.build();
        final Entry entry2 = entryBuilder.build();
        when(entryRepository.findAll(any(Sort.class))).thenReturn(List.of(entry1, entry2));
        final List<EntryDTO> entries = entryService.findAll();
        assertEquals(2, entries.size());
    }

    @Test
    void findAll_ReturnEntries() {
        final Entry entry1 = entryBuilder.build();
        final Entry entry2 = entryBuilder.build();
        when(entryRepository.findAll(any(Sort.class))).thenReturn(List.of(entry1, entry2));
        final List<EntryDTO> entryDTOs = entryService.findAll();
        assertEquals(2, entryDTOs.size());
        assertEquals(entry1.getId(), entryDTOs.get(0).getId());
        assertEquals(entry1.getTitle(), entryDTOs.get(0).getTitle());
        assertEquals(entry1.getContent(), entryDTOs.get(0).getContent());
        assertEquals(entry2.getId(), entryDTOs.get(1).getId());
        assertEquals(entry2.getTitle(), entryDTOs.get(1).getTitle());
        assertEquals(entry2.getContent(), entryDTOs.get(1).getContent());
    }

    @Test
    void get_WithValidId_ReturnEntry() {
        final Entry entry = entryBuilder
                .withId(ID)
                .withTitle("title")
                .withContent("content")
                .build();
        when(entryRepository.findById(any(Long.class))).thenReturn(Optional.of(entry));
        final EntryDTO entryDTO = entryService.get(ID);
        verify(entryRepository).findById(ID);
        assertEquals(entry.getId(), entryDTO.getId());
        assertEquals(entry.getTitle(), entryDTO.getTitle());
        assertEquals(entry.getContent(), entryDTO.getContent());
    }

    @Test
    void get_WithInvalidId_ThrowsNotFoundException() {
        when(entryRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> entryService.get(ID));
    }

    // creation

    @Test
    void create_WithValidEntryDTO_CreatesEntry() {
        final EntryDTO entryDTO = entryDTOBuilder
                .withTitle("title")
                .withContent("content")
                .build();
        final Entry entry = entryBuilder
                .withTitle(entryDTO.getTitle())
                .withContent(entryDTO.getContent())
                .build();

        when(entryRepository.save(any(Entry.class))).thenReturn(entry);
        entryService.create(entryDTO);

        final ArgumentCaptor<Entry> entryCaptor = ArgumentCaptor.forClass(Entry.class);
        verify(entryRepository).save(entryCaptor.capture());
        final Entry createdEntry = entryCaptor.getValue();
        Assertions.assertAll("created entry",
                () -> assertEquals(createdEntry.getTitle(), entryDTO.getTitle()),
                () -> assertEquals(createdEntry.getContent(), entryDTO.getContent()),
                () -> assertEquals(createdEntry.getId(), entryDTO.getId())
        );
    }

    // deletion
    @Test
    void delete_WithValidId_DeletesEntry() {
        final Entry entry = entryBuilder.withId(ID).build();
        when(entryRepository.findById(any(Long.class))).thenReturn(Optional.of(entry));
        entryService.delete(entry.getId());
        verify(entryRepository).deleteById(entry.getId());

    }

    @Test
    void delete_WithInvalidId_ThrowsNotFoundException() {
        final Entry entry = entryBuilder.withId(ID).build();

        Assertions.assertThrows(NotFoundException.class, () -> entryService.delete(entry.getId()));
        verify(entryRepository, times(0)).deleteById(entry.getId());

    }

    // update
    @Test
    void update_WithValidIdAndEntryDTO_UpdatesEntry() {
        final EntryDTO entryDTO = entryDTOBuilder
                .withId(ID)
                .withTitle("title_updated")
                .withContent("content_updated")
                .build();

        final Entry entry = entryBuilder
                .withId(ID)
                .build();
        when(entryRepository.findById(any(Long.class))).thenReturn(Optional.of(entry));
        when(entryRepository.save(any(Entry.class))).thenReturn(entry);
        entryService.update(entry.getId(), entryDTO);

        final ArgumentCaptor<Entry> entryCaptor = ArgumentCaptor.forClass(Entry.class);
        verify(entryRepository).save(entryCaptor.capture());
        final Entry updatedEntry = entryCaptor.getValue();
        Assertions.assertAll("updated entry",
                () -> assertEquals(updatedEntry.getTitle(), entryDTO.getTitle()),
                () -> assertEquals(updatedEntry.getContent(), entryDTO.getContent()),
                () -> assertEquals(updatedEntry.getId(), entryDTO.getId())
        );
    }

    @Test
    void update_WithInvalidId_ThrowsNotFoundException() {
        final Entry entry = entryBuilder.withId(ID).build();
        final EntryDTO entryDTO = entryDTOBuilder.build();
        Assertions.assertThrows(NotFoundException.class, () -> entryService.update(entry.getId(), entryDTO));
        verify(entryRepository, times(0)).save(entry);

    }

    // title exists
    @Test
    void titleExists_WithInvalidTitle_ReturnsFalse() {
        when(entryRepository.existsByTitleIgnoreCase("title")).thenReturn(false);
        assertFalse(entryService.titleExists("title"));
    }

    @Test
    void titleExists_WithValidTitle_ReturnsTrue() {
        when(entryRepository.existsByTitleIgnoreCase("title")).thenReturn(true);
        assertTrue(entryService.titleExists("title"));
    }

}
