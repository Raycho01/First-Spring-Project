package com.rako.spring.service.iml;

import com.rako.spring.exception.TaskNotFoundException;
import com.rako.spring.model.Task;
import com.rako.spring.model.User;
import com.rako.spring.repository.TaskRepository;
import com.rako.spring.repository.UserRepository;
import com.rako.spring.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task saveTask(Task task, String creatorUsername) {
        task.setCreatorUsername(creatorUsername);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            return task.get();
        }
        else {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public void deleteTaskById(long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
        }
        else {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public User assignTaskById(long id, String username) {
        User user = userRepository.findByUsername(username);
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent() && optionalTask.get().getWorkerUsername() == null) {
            Task task = optionalTask.get();
            user.getTasks().add(task);
            task.setWorkerUsername(user.getUsername());
            task.setUser(user);
            task.setStatus(Task.Status.INPROGRESS);
            taskRepository.save(task);
            return userRepository.save(user);
        }
        else {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public Task finishTaskById(long id, String username) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent() && username.equals(optionalTask.get().getWorkerUsername())) {
            Task task = optionalTask.get();
            task.setStatus(Task.Status.DONE);
            return taskRepository.save(task);
        }
        else {
            throw new TaskNotFoundException();
        }
    }

}
