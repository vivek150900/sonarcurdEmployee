package com.employee_crud.service;


import com.employee_crud.model.Employee;
import com.employee_crud.repository.EmployeeRepository;
import com.employee_crud.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = Employee.builder()
                .id(1L)
                .name("Vivek")
                .email("vivek@gmail.com")
                .department("IT")
                .build();
    }

    // ✅ Create Employee
    @Test
    void testCreateEmployee() {
        when(repository.save(employee)).thenReturn(employee);

        Employee saved = service.createEmployee(employee);

        assertNotNull(saved);
        assertEquals("Vivek", saved.getName());
    }

    // ✅ Get All Employees
    @Test
    void testGetAllEmployees() {
        when(repository.findAll()).thenReturn(List.of(employee));

        List<Employee> list = service.getAllEmployees();

        assertEquals(1, list.size());
    }

    // ✅ Get Employee By ID - Success
    @Test
    void testGetEmployeeById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = service.getEmployeeById(1L);

        assertEquals("Vivek", result.getName());
    }

    // ❌ Get Employee By ID - Not Found
    @Test
    void testGetEmployeeById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.getEmployeeById(1L));
    }

    // ✅ Update Employee
    @Test
    void testUpdateEmployee() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(repository.save(employee)).thenReturn(employee);

        Employee updated = service.updateEmployee(1L, employee);

        assertEquals("Vivek", updated.getName());
    }

    // ✅ Delete Employee
    @Test
    void testDeleteEmployee() {
        doNothing().when(repository).deleteById(1L);

        service.deleteEmployee(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}