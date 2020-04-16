package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void should_return_all_employees_when_get_all() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(5, employees.size());
        Assert.assertEquals(0, employees.get(0).getId().longValue());
        Assert.assertEquals("Xiaoming", employees.get(0).getName());
        Assert.assertEquals(20, employees.get(0).getAge().longValue());
        Assert.assertEquals("Male", employees.get(0).getGender());
        Assert.assertEquals(0, employees.get(0).getSalary().longValue());
    }

    @Test
    public void should_return_correct_employee_when_get_all_with_page() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .queryParam("page", 2)
                .queryParam("pageSize", 3)
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(2, employees.size());

        Assert.assertEquals(3, employees.get(0).getId().longValue());
        Assert.assertEquals("Xiaogang", employees.get(0).getName());
        Assert.assertEquals(16, employees.get(0).getAge().longValue());
        Assert.assertEquals("Male", employees.get(0).getGender());
        Assert.assertEquals(0, employees.get(0).getSalary().longValue());

        Assert.assertEquals(4, employees.get(1).getId().longValue());
        Assert.assertEquals("Xiaoxia", employees.get(1).getName());
        Assert.assertEquals(15, employees.get(1).getAge().longValue());
        Assert.assertEquals("Male", employees.get(1).getGender());
        Assert.assertEquals(0, employees.get(1).getSalary().longValue());
    }

    @Test
    public void should_return_correct_employees_when_get_by_gender() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .queryParam("gender", "male")
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(5, employees.size());
        Assert.assertEquals(0, employees.get(0).getId().longValue());
        Assert.assertEquals("Xiaoming", employees.get(0).getName());
        Assert.assertEquals(20, employees.get(0).getAge().longValue());
        Assert.assertEquals("Male", employees.get(0).getGender());
        Assert.assertEquals(0, employees.get(0).getSalary().longValue());
    }

    @Test
    public void should_return_employee_1_when_get_1() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .get("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals(1, employee.getId().longValue());
        Assert.assertEquals("Xiaohong", employee.getName());
        Assert.assertEquals(19, employee.getAge().longValue());
        Assert.assertEquals("Male", employee.getGender());
        Assert.assertEquals(0, employee.getSalary().longValue());
    }

    @Test
    public void should_return_correct_employee_when_create() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body("{" +
                        "\"id\": 10," +
                        "\"name\": \"Test\"," +
                        "\"age\": 19," +
                        "\"gender\": \"Male\"," +
                        "\"salary\": 0" +
                        "}")
                .post("/employees");

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals(10, employee.getId().longValue());
        Assert.assertEquals("Test", employee.getName());
        Assert.assertEquals(19, employee.getAge().longValue());
        Assert.assertEquals("Male", employee.getGender());
        Assert.assertEquals(0, employee.getSalary().longValue());
    }

    @Test
    public void should_return_200_when_delete() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .delete("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }
}
