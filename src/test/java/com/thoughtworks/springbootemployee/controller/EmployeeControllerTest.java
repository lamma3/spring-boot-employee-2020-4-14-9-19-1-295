package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee employee0 = new Employee(0, "Xiaoming", 20, "Male", 0, 0);
        Employee employee1 = new Employee(1, "Xiaohong", 19, "Male", 0, 0);
        Employee employee2 = new Employee(2, "Xiaozhi", 15, "Male", 0, 0);
        Employee employee3 = new Employee(3, "Xiaogang", 16, "Male", 0, 1);
        Employee employee4 = new Employee(4, "Xiaoxia", 15, "Male", 0, 1);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee0);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);

        Mockito.when(employeeRepository.findAll())
                .thenReturn(employees);

        Mockito.when(employeeRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(employees.subList(3, 5)));

        Mockito.when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee1));

        Mockito.when(employeeRepository.findAllByGenderIgnoreCase("male"))
                .thenReturn(employees);
    }

    @Test
    public void should_return_all_employees_when_get_all() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].name", is("Xiaoming")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].salary", is(0)));
    }

    @Test
    public void should_return_correct_employee_when_get_all_with_page() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/employees")
                .queryParam("page", "2")
                .queryParam("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].name", is("Xiaogang")))
                .andExpect(jsonPath("$[0].age", is(16)))
                .andExpect(jsonPath("$[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].salary", is(0)))
                .andExpect(jsonPath("$[1].id", is(4)))
                .andExpect(jsonPath("$[1].name", is("Xiaoxia")))
                .andExpect(jsonPath("$[1].age", is(15)))
                .andExpect(jsonPath("$[1].gender", is("Male")))
                .andExpect(jsonPath("$[1].salary", is(0)));
    }

    @Test
    public void should_return_correct_employees_when_get_by_gender() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/employees")
                .queryParam("gender", "male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].name", is("Xiaoming")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].salary", is(0)));
    }

    @Test
    public void should_return_employee_1_when_get_1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Xiaohong")))
                .andExpect(jsonPath("age", is(19)))
                .andExpect(jsonPath("gender", is("Male")))
                .andExpect(jsonPath("salary", is(0)));
    }

    @Test
    public void should_return_correct_employee_when_create() throws Exception {
        Employee newEmployee = new Employee(10, "Test", 19, "Male", 0, 1);
        Mockito.when(employeeRepository.save(Mockito.any()))
                .thenReturn(newEmployee);

        mvc.perform(MockMvcRequestBuilders
                .post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"Test\"," +
                        "\"age\": 19," +
                        "\"gender\": \"Male\"," +
                        "\"salary\": 0," +
                        "\"companyId\": 1" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(10)))
                .andExpect(jsonPath("name", is("Test")))
                .andExpect(jsonPath("age", is(19)))
                .andExpect(jsonPath("gender", is("Male")))
                .andExpect(jsonPath("salary", is(0)))
                .andExpect(jsonPath("companyId", is(1)));

        Employee preCreateEmployee = new Employee(null, "Test", 19, "Male", 0, 1);
        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        Mockito.verify(employeeRepository).save(argumentCaptor.capture());
        Assert.assertEquals(preCreateEmployee, argumentCaptor.getValue());
    }

    @Test
    public void should_return_200_when_delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_correct_employee_when_update() throws Exception {
        Employee updatedEmployee = new Employee(1, "New name", 19, "Male", 0, 0);
        Mockito.when(employeeRepository.save(Mockito.any()))
                .thenReturn(updatedEmployee);

        mvc.perform(MockMvcRequestBuilders
                .put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"New name\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("New name")))
                .andExpect(jsonPath("age", is(19)))
                .andExpect(jsonPath("gender", is("Male")))
                .andExpect(jsonPath("salary", is(0)));

        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        Mockito.verify(employeeRepository).save(argumentCaptor.capture());
        Assert.assertEquals(updatedEmployee, argumentCaptor.getValue());
    }
}
