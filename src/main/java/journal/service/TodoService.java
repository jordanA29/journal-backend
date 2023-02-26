package journal.service;

import journal.domain.Todo;
import journal.model.TodoDTO;
import journal.repos.TodoRepository;
import journal.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDTO> findAll() {
        final List<Todo> todos = todoRepository.findAll(Sort.by("id"));
        return todos.stream()
                .map((todo) -> mapToDTO(todo, new TodoDTO()))
                .collect(Collectors.toList());
    }

    public TodoDTO get(final Long id) {
        return todoRepository.findById(id)
                .map(todo -> mapToDTO(todo, new TodoDTO()))
                .orElseThrow(() -> new NotFoundException());
    }

    public Long create(final TodoDTO todoDTO) {
        final Todo todo = new Todo();
        mapToEntity(todoDTO, todo);
        return todoRepository.save(todo).getId();
    }

    public void update(final Long id, final TodoDTO todoDTO) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(todoDTO, todo);
        todoRepository.save(todo);
    }

    public void delete(final Long id) {
        todoRepository.deleteById(id);
    }

    private TodoDTO mapToDTO(final Todo todo, final TodoDTO todoDTO) {
        todoDTO.setId(todo.getId());
        todoDTO.setTitle(todo.getTitle());
        todoDTO.setComment(todo.getComment());
        todoDTO.setDone(todo.getDone());
        return todoDTO;
    }

    private Todo mapToEntity(final TodoDTO todoDTO, final Todo todo) {
        todo.setTitle(todoDTO.getTitle());
        todo.setComment(todoDTO.getComment());
        todo.setDone(todoDTO.getDone());
        return todo;
    }

    public boolean titleExists(final String title) {
        return todoRepository.existsByTitleIgnoreCase(title);
    }

}
