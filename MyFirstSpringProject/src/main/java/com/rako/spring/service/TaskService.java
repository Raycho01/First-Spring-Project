package com.rako.spring.service;

import com.rako.spring.model.Task;
import com.rako.spring.model.User;

import java.util.List;

public interface TaskService {

    Task saveTask(Task task, String creatorUsername);

    List<Task> getAllTasks();

    Task getTaskById(long id);

    void deleteTaskById(long id);

    User assignTaskById(long id, String username);

    Task finishTaskById(long id, String username);
}
