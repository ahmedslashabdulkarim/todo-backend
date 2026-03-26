package org.example.todobackend.todo;

import org.example.todobackend.chatgpt.ChatGPTService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final IdService idService;

    private final ChatGPTService chatGPTService;

    public TodoService(TodoRepository todoRepository,
                       IdService idService,
                       ChatGPTService chatGPTService) {
        this.todoRepository = todoRepository;
        this.idService = idService;
        this.chatGPTService = chatGPTService;
    }
    /*
    public TodoService(TodoRepository todoRepository, IdService idService) {
        this.todoRepository = todoRepository;
        this.idService = idService;
    }
    */

    public List<Todo> findAllTodos() {
        return todoRepository.findAll();
    }



    public Todo addTodo(NewTodo newTodo) {
        String corrected = chatGPTService.checkSpelling(newTodo.description());
        String id = idService.randomId();
        Todo todoToSave = new Todo(id, corrected, newTodo.status());
        return todoRepository.save(todoToSave);
    }
    /*
    public Todo addTodo(NewTodo newTodo) {

        String id = idService.randomId();

        Todo todoToSave = new Todo(id, newTodo.description(), newTodo.status());

        return todoRepository.save(todoToSave);
    }
    */

    public Todo updateTodo(UpdateTodo todo, String id){
        Todo todoToUpdate = new Todo(id, todo.description(), todo.status());

        return todoRepository.save(todoToUpdate);
    }

    //Global Exception
    public Todo findTodoById(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo with id: " + id + " not found!"));
    }

    public void deleteTodo(String id){
        todoRepository.deleteById(id);

    }
}
