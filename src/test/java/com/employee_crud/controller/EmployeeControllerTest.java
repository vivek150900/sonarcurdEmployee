package com.employee_crud.controller;

import com.employee_crud.model.Employee;
import com.employee_crud.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final Employee employee =
            Employee.builder()
                    .id(1L)
                    .name("Vivek")
                    .email("vivek@gmail.com")
                    .department("IT")
                    .build();

    // ✅ Create API Test
    @Test
    void testCreateEmployee() throws Exception {
        Mockito.when(service.createEmployee(Mockito.any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vivek"));
    }

    // ✅ Get All API Test
    @Test
    void testGetAllEmployees() throws Exception {
        Mockito.when(service.getAllEmployees())
                .thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // ✅ Get By ID API Test
    @Test
    void testGetEmployeeById() throws Exception {
        Mockito.when(service.getEmployeeById(1L))
                .thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("vivek@gmail.com"));
    }

    // ✅ Update API Test
    @Test
    void testUpdateEmployee() throws Exception {
        Mockito.when(service.updateEmployee(Mockito.eq(1L),
                        Mockito.any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.department").value("IT"));
    }

    // ✅ Delete API Test
    @Test
    void testDeleteEmployee() throws Exception {

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee Deleted Successfully"));
    }
}