package journal.builder;

import journal.domain.Entry;

public class EntryBuilder {

        private Long id;
        private String title;
        private String content;

        public EntryBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EntryBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public EntryBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public Entry build() {
            Entry entry = new Entry();
            entry.setId(id);
            entry.setTitle(title);
            entry.setContent(content);
            return entry;
        }
}
