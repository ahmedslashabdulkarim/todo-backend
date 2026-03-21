package org.example.todobackend.todo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {


    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.findAllTodos();
    }

    @PostMapping
    public Todo postTodo(@RequestBody NewTodo newTodo){
        return todoService.addTodo(newTodo);
    }
}
