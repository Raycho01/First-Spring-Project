package com.rako.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Minimum 3 characters required!")
    @Column(name = "firstName")
    private String firstName;

    @Size(min = 3, message = "Minimum 3 characters required!")
    @Column(name = "lastName")
    private String lastName;

    @Size(min = 3, message = "Minimum 3 characters required!")
    @Column(unique = true, name = "username")
    private String username;

    @Size(min = 3, message = "Minimum 3 characters required!")
    @Column(name = "password")
    private String password;

    @Email(message = "Valid email required!")
    @Column(name = "email")
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Task> tasks;

    public enum Role {
        BOSS,
        EMPLOYEE
    }

    public User(Long id, String firstName, String lastName, String username, String password, String email, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        setPassword(password);
        this.email = email;
        this.role = role;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

}
