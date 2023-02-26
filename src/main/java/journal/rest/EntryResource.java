package journal.rest;

import journal.service.EntryService;
import journal.model.EntryDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/entries", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntryResource {

    private final EntryService entryService;

    public EntryResource(final EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public ResponseEntity<List<EntryDTO>> getAllEntries() {
        return ResponseEntity.ok(entryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryDTO> getEntry(@PathVariable final Long id) {
        return ResponseEntity.ok(entryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEntry(@RequestBody @Valid final EntryDTO entryDTO) {
        return new ResponseEntity<>(entryService.create(entryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntry(@PathVariable final Long id,
            @RequestBody @Valid final EntryDTO entryDTO) {
        entryService.update(id, entryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEntry(@PathVariable final Long id) {
        entryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
