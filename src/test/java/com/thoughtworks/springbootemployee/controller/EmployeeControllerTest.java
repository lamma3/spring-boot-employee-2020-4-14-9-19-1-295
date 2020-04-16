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
}
