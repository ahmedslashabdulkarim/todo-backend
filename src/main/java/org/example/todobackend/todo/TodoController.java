package org.example.todobackend.todo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {


    private final TodoService todoService;
    private final TodoRepository todoRepository;

    public TodoController(TodoService todoService, TodoRepository todoRepository) {
        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.findAllTodos();
    }

    @GetMapping("{id}")
    public Todo getTodoById(@PathVariable String id){
        return todoService.findTodoById(id);
    }


    @PostMapping
    public Todo postTodo(@RequestBody NewTodo newTodo){
        return todoService.addTodo(newTodo);
    }


    @PutMapping("/{id}")
    public Todo putTodo(@RequestBody UpdateTodo todo, @PathVariable String id){
        return todoService.updateTodo(todo, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
    }


}
