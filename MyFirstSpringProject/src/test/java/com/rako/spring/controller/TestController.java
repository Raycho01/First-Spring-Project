package com.rako.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rako.spring.model.User;
import com.rako.spring.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    private Long empId = 0L;

    @BeforeEach
    void init() {
        User user = exampleEmployee();
        empId = user.getId();
        repository.save(user);
    }

    @AfterEach
    void after() {
        if(repository.findById(empId).isPresent()){
            repository.deleteById(empId);
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static User exampleEmployee() {
        User user = new User();
        user.setId(999L);
        user.setFirstName("TestEmployee");
        user.setLastName("Number_4");
        user.setEmail("test_object@gmail.com");

        return user;
    }

    public static User examplePutEmployee() {
        User user = new User();
        user.setId(666L);
        user.setFirstName("PutTestEmployee");
        user.setLastName("Number_4_put");
        user.setEmail("put_test_object@gmail.com");

        return user;
    }

    public static User validationTestEmployee() {
        User user = new User();
        user.setId(555L);
        user.setFirstName("");
        user.setLastName("ValidationEmployee");
        user.setEmail("should_not_create_at_all@gmail.com");

        return user;
    }

    @Test
    void getEmployeeShouldReturnStatusOk() throws Exception {
        mvc.perform(get("/api/employees")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getEmployeeByIdShouldReturnTheCorrectStatusAndName() throws Exception {
        mvc.perform(get("/api/employees/{id}", empId))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;"))
                .andExpect(jsonPath("$.firstName").value("TestEmployee"));
    }

    @Test
    public void postEmployeeShouldReturnTheCorrectStatusAndId() throws Exception {
        mvc.perform(post("/api/employees")
                        .content(asJsonString(exampleEmployee()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
        mvc.perform(delete("/api/employees/{id}", exampleEmployee().getId()))
                .andExpect(status().isAccepted());
    }

    @Test
    public void putUpdateEmployeeShouldReturnTheCorrectStatusAndCheckAllFields() throws Exception {
        mvc.perform(put("/api/employees/{id}", empId)
                        .content(asJsonString(examplePutEmployee()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("PutTestEmployee"))
                .andExpect(jsonPath("$.lastName").value("Number_4_put"))
                .andExpect(jsonPath("$.email").value("put_test_object@gmail.com"));
    }

    @Test
    public void deleteEmployeeByIdShouldReturnTheCorrectStatus() throws Exception {
        mvc.perform(delete("/api/employees/{id}", empId) )
                .andExpect(status().isAccepted());
    }

    @Test
    public void postValidationEmployeeShouldReturnCorrectResponse() throws Exception {
        mvc.perform(post("/api/employees")
                        .content(asJsonString(validationTestEmployee()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name is required"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
