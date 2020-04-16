package com.thoughtworks.springbootemployee.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

        Assert.assertEquals(5, response.jsonPath().getList("$").size());
        Assert.assertEquals(0, response.jsonPath().getLong("[0].id"));
        Assert.assertEquals("Xiaoming", response.jsonPath().get("[0].name"));
        Assert.assertEquals(20, response.jsonPath().getLong("[0].age"));
        Assert.assertEquals("Male", response.jsonPath().get("[0].gender"));
        Assert.assertEquals(0, response.jsonPath().getLong("[0].salary"));
    }

    @Test
    public void should_return_correct_employee_when_get_all_with_page() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .queryParam("page", 2)
                .queryParam("pageSize", 3)
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Assert.assertEquals(2, response.jsonPath().getList("$").size());
        Assert.assertEquals(3, response.jsonPath().getLong("[0].id"));
        Assert.assertEquals("Xiaogang", response.jsonPath().get("[0].name"));
        Assert.assertEquals(16, response.jsonPath().getLong("[0].age"));
        Assert.assertEquals("Male", response.jsonPath().get("[0].gender"));
        Assert.assertEquals(0, response.jsonPath().getLong("[0].salary"));
        Assert.assertEquals(4, response.jsonPath().getLong("[1].id"));
        Assert.assertEquals("Xiaoxia", response.jsonPath().get("[1].name"));
        Assert.assertEquals(15, response.jsonPath().getLong("[1].age"));
        Assert.assertEquals("Male", response.jsonPath().get("[1].gender"));
        Assert.assertEquals(0, response.jsonPath().getLong("[1].salary"));
    }

    @Test
    public void should_return_correct_employees_when_get_by_gender() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .queryParam("gender", "male")
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Assert.assertEquals(5, response.jsonPath().getList("$").size());
        Assert.assertEquals(0, response.jsonPath().getLong("[0].id"));
        Assert.assertEquals("Xiaoming", response.jsonPath().get("[0].name"));
        Assert.assertEquals(20, response.jsonPath().getLong("[0].age"));
        Assert.assertEquals("Male", response.jsonPath().get("[0].gender"));
        Assert.assertEquals(0, response.jsonPath().getLong("[0].salary"));
    }

    @Test
    public void should_return_employee_1_when_get_1() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .get("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Assert.assertEquals(1, response.jsonPath().getLong("id"));
        Assert.assertEquals("Xiaohong", response.jsonPath().get("name"));
        Assert.assertEquals(19, response.jsonPath().getLong("age"));
        Assert.assertEquals("Male", response.jsonPath().get("gender"));
        Assert.assertEquals(0, response.jsonPath().getLong("salary"));
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

        Assert.assertEquals(10, response.jsonPath().getLong("id"));
        Assert.assertEquals("Test", response.jsonPath().get("name"));
        Assert.assertEquals(19, response.jsonPath().getLong("age"));
        Assert.assertEquals("Male", response.jsonPath().get("gender"));
        Assert.assertEquals(0, response.jsonPath().getLong("salary"));
    }

    @Test
    public void should_return_200_when_delete() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .delete("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void should_return_correct_employee_when_update() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body("{" +
                        "\"name\": \"New name\"" +
                        "}")
                .put("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Assert.assertEquals(1, response.jsonPath().getLong("id"));
        Assert.assertEquals("New name", response.jsonPath().get("name"));
        Assert.assertEquals(19, response.jsonPath().getLong("age"));
        Assert.assertEquals("Male", response.jsonPath().get("gender"));
        Assert.assertEquals(0, response.jsonPath().getLong("salary"));
    }
}
