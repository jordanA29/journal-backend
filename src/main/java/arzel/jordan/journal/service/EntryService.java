package arzel.jordan.journal.service;

import arzel.jordan.journal.domain.Entry;
import arzel.jordan.journal.model.EntryDTO;
import arzel.jordan.journal.repos.EntryRepository;
import arzel.jordan.journal.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EntryService {

    private final EntryRepository entryRepository;

    public EntryService(final EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<EntryDTO> findAll() {
        final List<Entry> entries = entryRepository.findAll(Sort.by("id"));
        return entries.stream()
                .map((entry) -> mapToDTO(entry, new EntryDTO()))
                .collect(Collectors.toList());
    }

    public EntryDTO get(final Long id) {
        return entryRepository.findById(id)
                .map(entry -> mapToDTO(entry, new EntryDTO()))
                .orElseThrow(() -> new NotFoundException());
    }

    public Long create(final EntryDTO entryDTO) {
        final Entry entry = new Entry();
        mapToEntity(entryDTO, entry);
        return entryRepository.save(entry).getId();
    }

    public void update(final Long id, final EntryDTO entryDTO) {
        final Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(entryDTO, entry);
        entryRepository.save(entry);
    }

    public void delete(final Long id) {
        entryRepository.deleteById(id);
    }

    private EntryDTO mapToDTO(final Entry entry, final EntryDTO entryDTO) {
        entryDTO.setId(entry.getId());
        entryDTO.setTitle(entry.getTitle());
        entryDTO.setContent(entry.getContent());
        return entryDTO;
    }

    private Entry mapToEntity(final EntryDTO entryDTO, final Entry entry) {
        entry.setTitle(entryDTO.getTitle());
        entry.setContent(entryDTO.getContent());
        return entry;
    }

    public boolean titleExists(final String title) {
        return entryRepository.existsByTitleIgnoreCase(title);
    }

}
