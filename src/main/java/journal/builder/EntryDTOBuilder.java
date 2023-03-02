package journal.builder;

import journal.model.EntryDTO;

public class EntryDTOBuilder {

        private Long id;
        private String title;
        private String content;

        public EntryDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        public EntryDTOBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public EntryDTOBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public EntryDTO build() {
            EntryDTO entryDTO = new EntryDTO();
            entryDTO.setId(id);
            entryDTO.setTitle(title);
            entryDTO.setContent(content);
            return entryDTO;
        }
}
