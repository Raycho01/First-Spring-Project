package com.rako.spring.controller;

import com.rako.spring.model.Task;
import com.rako.spring.model.User;
import com.rako.spring.repository.UserRepository;
import com.rako.spring.service.TaskService;
import com.rako.spring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class Controller {
    public UserService userService;
    public TaskService taskService;

    public Controller(UserService userService, TaskService taskService) {
        super();
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllUser() {
        userService.deleteAllUser();
        return new ResponseEntity<String>("All users deleted!", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") long id, @RequestBody User user){
        return new ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<String>("User deleted successfully!", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/boss")
    public ResponseEntity<String> adminPrint() {
        return new ResponseEntity<String>("Hello, boss! You can create/delete tasks.", HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<String> userPrint() {
        return new ResponseEntity<String>("Hello, employee! You can assign tasks.", HttpStatus.OK);
    }

    @PostMapping("/boss/task/create")
    public ResponseEntity<Task> saveTask(@Valid @RequestBody Task task) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<Task>(taskService.saveTask(task, username), HttpStatus.CREATED);
    }

    @DeleteMapping("boss/task/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable("id") long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<String>("Task finished successfully.", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/task")
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<List<Task>>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) {
        return new ResponseEntity<Task>(taskService.getTaskById(id),HttpStatus.OK);
    }

    @PutMapping("/employee/task/assign/{id}")
    public ResponseEntity<User> assignTaskById(@PathVariable("id") long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<User>(taskService.assignTaskById(id, username), HttpStatus.OK);
    }

    @PutMapping("/employee/task/finish/{id}")
    public ResponseEntity<Task> finishTaskById(@PathVariable("id") long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<Task>(taskService.finishTaskById(id, username), HttpStatus.OK);
    }

}
