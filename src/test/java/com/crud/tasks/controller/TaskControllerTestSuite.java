package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @SpyBean
    private TaskMapper taskMapper;

    private Task createTask() {
        return new Task(1L, "Test task", "Test content");
    }

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(createTask());
        Mockito.when(dbService.getAllTasks()).thenReturn(tasks);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test content")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        Optional<Task> task = Optional.of(createTask());
        Mockito.when(dbService.getTask(ArgumentMatchers.anyLong())).thenReturn(task);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTask?taskId=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().isOk()))
                .andExpect((MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given & When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/task/deleteTask?taskId=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        Mockito.when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(createTask());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(createTask());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));
    }

    @Test
    public void testCreateTask() throws Exception {
        //Given
        Mockito.when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(null);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(createTask());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}