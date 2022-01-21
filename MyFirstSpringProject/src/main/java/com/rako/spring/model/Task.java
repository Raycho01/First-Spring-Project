package com.rako.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creatorUsername")
    @Size(min = 3, message = "Minimum 3 characters required!")
    private String creatorUsername;

    @Column(name = "workerUsername")
    @Size(min = 3, message = "Minimum 3 characters required!")
    private String workerUsername;

    @Column(name = "name")
    @Size(min = 3, message = "Minimum 3 characters required!")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.BACKLOG;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Task(String name, String description, Difficulty difficulty, Status status) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public enum Status {
        BACKLOG,
        INPROGRESS,
        DONE
    }
}
