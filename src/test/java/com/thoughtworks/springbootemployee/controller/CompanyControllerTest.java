package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
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
}
