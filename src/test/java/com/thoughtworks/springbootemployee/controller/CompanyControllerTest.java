package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(companyController);
    }

    @Test
    public void should_return_all_companies_when_get_all() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .get("/companies");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(2, companies.size());
        Assert.assertEquals(0, companies.get(0).getId().longValue());
        Assert.assertEquals("spring", companies.get(0).getCompanyName());
        Assert.assertEquals(3, companies.get(0).getEmployeeNumber().longValue());
        Assert.assertEquals(3, companies.get(0).getEmployees().size());
        Assert.assertEquals(10, companies.get(0).getEmployees().get(0).getId().longValue());
        Assert.assertEquals("spring1", companies.get(0).getEmployees().get(0).getName());
        Assert.assertEquals(20, companies.get(0).getEmployees().get(0).getAge().longValue());
        Assert.assertEquals("Male", companies.get(0).getEmployees().get(0).getGender());
        Assert.assertEquals(1000, companies.get(0).getEmployees().get(0).getSalary().longValue());
    }

    @Test
    public void should_return_correct_companies_when_get_all_with_page() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .queryParam("page", 2)
                .queryParam("pageSize", 1)
                .get("/companies");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(1, companies.get(0).getId().longValue());
        Assert.assertEquals("boot", companies.get(0).getCompanyName());
        Assert.assertEquals(2, companies.get(0).getEmployeeNumber().longValue());
        Assert.assertEquals(2, companies.get(0).getEmployees().size());
        Assert.assertEquals(13, companies.get(0).getEmployees().get(0).getId().longValue());
        Assert.assertEquals("boot1", companies.get(0).getEmployees().get(0).getName());
        Assert.assertEquals(16, companies.get(0).getEmployees().get(0).getAge().longValue());
        Assert.assertEquals("Male", companies.get(0).getEmployees().get(0).getGender());
        Assert.assertEquals(4000, companies.get(0).getEmployees().get(0).getSalary().longValue());
    }

    @Test
    public void should_return_company_1_when_get_1() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .get("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(1, company.getId().longValue());
        Assert.assertEquals("boot", company.getCompanyName());
        Assert.assertEquals(2, company.getEmployeeNumber().longValue());
        Assert.assertEquals(2, company.getEmployees().size());
        Assert.assertEquals(13, company.getEmployees().get(0).getId().longValue());
        Assert.assertEquals("boot1", company.getEmployees().get(0).getName());
        Assert.assertEquals(16, company.getEmployees().get(0).getAge().longValue());
        Assert.assertEquals("Male", company.getEmployees().get(0).getGender());
        Assert.assertEquals(4000, company.getEmployees().get(0).getSalary().longValue());
    }

    @Test
    public void should_return_employees_when_get_company_employees() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .get("/companies/1/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(2, employees.size());
        Assert.assertEquals(13, employees.get(0).getId().longValue());
        Assert.assertEquals("boot1", employees.get(0).getName());
        Assert.assertEquals(16, employees.get(0).getAge().longValue());
        Assert.assertEquals("Male", employees.get(0).getGender());
        Assert.assertEquals(4000, employees.get(0).getSalary().longValue());
    }

    @Test
    public void should_return_correct_company_when_create() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body("{" +
                        "\"id\": 10," +
                        "\"companyName\": \"Test\"," +
                        "\"employeeNumber\": 0," +
                        "\"employees\": []" +
                        "}")
                .post("/companies");

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(10, company.getId().longValue());
        Assert.assertEquals("Test", company.getCompanyName());
        Assert.assertEquals(0, company.getEmployeeNumber().longValue());
        Assert.assertEquals(0, company.getEmployees().size());
    }

    @Test
    public void should_return_200_when_delete() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .delete("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void should_return_correct_company_when_update() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body("{" +
                        "\"companyName\": \"New name\"" +
                        "}")
                .put("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(1, company.getId().longValue());
        Assert.assertEquals("New name", company.getCompanyName());
        Assert.assertEquals(2, company.getEmployeeNumber().longValue());
        Assert.assertEquals(2, company.getEmployees().size());
        Assert.assertEquals(13, company.getEmployees().get(0).getId().longValue());
        Assert.assertEquals("boot1", company.getEmployees().get(0).getName());
        Assert.assertEquals(16, company.getEmployees().get(0).getAge().longValue());
        Assert.assertEquals("Male", company.getEmployees().get(0).getGender());
        Assert.assertEquals(4000, company.getEmployees().get(0).getSalary().longValue());
    }
}
