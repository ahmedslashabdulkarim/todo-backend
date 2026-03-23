package org.example.todobackend.todo;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class TodoServiceTest {

    TodoRepository todoRepository = mock(TodoRepository.class);
    IdService idService = mock(IdService.class);
    TodoService todoService = new TodoService(todoRepository, idService);


    @Test
    void findAllTodos() {
        //GIVIN
        Todo t1 = new Todo("1", "des1", TodoStatus.OPEN);
        Todo t2 = new Todo("2", "des2", TodoStatus.OPEN);
        Todo t3 = new Todo("3", "des3", TodoStatus.OPEN);
        List<Todo> todos = List.of(t1, t2, t3);

        when(todoRepository.findAll()).thenReturn(todos);

        //WHEN
        List<Todo> actual = todoService.findAllTodos();
        //THEN
        verify(todoRepository).findAll();
        assertEquals(todos, actual);
        //assertEquals(new Todo("5", actual);
    }

    @Test
    void addTodo(){

        //GIVEN
        NewTodo newTodo = new NewTodo("Test-Description", TodoStatus.OPEN);
        Todo todoToSave = new Todo("Test-Id", "Test-Description", TodoStatus.OPEN);

        when(idService.randomId()).thenReturn("Test-Id");
        when(todoRepository.save(todoToSave)).thenReturn(todoToSave);

        //WHEN
        Todo actual = todoService.addTodo(newTodo);

        //THEN
        verify(idService).randomId();
        verify(todoRepository).save(todoToSave);
        assertEquals(todoToSave, actual);
    }

    @Test
    void updateTodo(){

        //GIVEN
        String id = "123";
        UpdateTodo todoToUpdate = new UpdateTodo("test-description", TodoStatus.IN_PROGRESS);
        Todo updatedTodo = new Todo("123", "test-description", TodoStatus.IN_PROGRESS);

        when(todoRepository.save(updatedTodo)).thenReturn(updatedTodo);

        //WHEN
        Todo actual = todoService.updateTodo(todoToUpdate, id);

        //THEN
        verify(todoRepository).save(updatedTodo);
        assertEquals(updatedTodo, actual);

    }

    @Test
    void getTodoByIdTest_whenValid_ThenReturnTodo(){

        //GIVEN
        String id ="1";
        Todo todo = new Todo("1", "test-description", TodoStatus.OPEN);

        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        //WHEN
        Todo actual = todoService.findTodoById(id);

        //THEN
        verify(todoRepository).findById(id);
        assertEquals(todo, actual);
    }

    @Test
    void getTodoByIdTest_whenInvalidId_ThenThrowException(){

        //GIVEN
        String id ="1";

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN
        assertThrows(NoSuchElementException.class, () -> todoService.findTodoById(id));

        //THEN
        verify(todoRepository).findById(id);
    }


    @Test
    void deletTodo(){

        // GIVEN
        String id = "1";
        doNothing().when(todoRepository).deleteById(id);

        // WHEN
        todoService.deleteTodo(id);

        // THEN
        verify(todoRepository).deleteById(id);

    }


}