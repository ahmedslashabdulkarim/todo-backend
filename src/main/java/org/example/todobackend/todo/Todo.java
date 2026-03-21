package org.example.todobackend.todo;

public record Todo(
        String id,
        String description,
        String status
) {

}
