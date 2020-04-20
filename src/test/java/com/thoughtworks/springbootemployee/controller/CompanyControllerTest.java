package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.db.Company;
import com.thoughtworks.springbootemployee.model.db.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class CompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyRepository companyRepository;

    @Before
    public void setUp() {
        List<Employee> employeesInSpring = new ArrayList<>();
        employeesInSpring.add(new Employee(10, "spring1", 20, "Male", 1000, 0));
        employeesInSpring.add(new Employee(11, "spring2", 19, "Male", 2000, 0));
        employeesInSpring.add(new Employee(12, "spring3", 15, "Male", 3000, 0));

        List<Employee> employeesInBoot = new ArrayList<>();
        employeesInBoot.add(new Employee(13, "boot1", 16, "Male", 4000, 0));
        employeesInBoot.add(new Employee(14, "boot2", 15, "Male", 5000, 0));

        Company company0 = new Company(0, "spring", 3, employeesInSpring);
        Company company1 = new Company(1, "boot", 2, employeesInBoot);

        List<Company> companies = new ArrayList<>();
        companies.add(company0);
        companies.add(company1);

        Mockito.when(companyRepository.findAll())
                .thenReturn(companies);

        Mockito.when(companyRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(companies.subList(1, 2)));

        Mockito.when(companyRepository.findById(1))
                .thenReturn(Optional.of(company1));
    }

    @Test
    public void should_return_all_companies_when_get_all() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].companyName", is("spring")))
                .andExpect(jsonPath("$[0].employeeNumber", is(3)))
                .andExpect(jsonPath("$[0].employees", hasSize(3)))
                .andExpect(jsonPath("$[0].employees[0].id", is(10)))
                .andExpect(jsonPath("$[0].employees[0].name", is("spring1")))
                .andExpect(jsonPath("$[0].employees[0].age", is(20)))
                .andExpect(jsonPath("$[0].employees[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].employees[0].salary", is(1000)));
    }

    @Test
    public void should_return_correct_companies_when_get_all_with_page() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/companies")
                .queryParam("page", "2")
                .queryParam("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].companyName", is("boot")))
                .andExpect(jsonPath("$[0].employeeNumber", is(2)))
                .andExpect(jsonPath("$[0].employees", hasSize(2)))
                .andExpect(jsonPath("$[0].employees[0].id", is(13)))
                .andExpect(jsonPath("$[0].employees[0].name", is("boot1")))
                .andExpect(jsonPath("$[0].employees[0].age", is(16)))
                .andExpect(jsonPath("$[0].employees[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].employees[0].salary", is(4000)));
    }

    @Test
    public void should_return_company_1_when_get_1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.companyName", is("boot")))
                .andExpect(jsonPath("$.employeeNumber", is(2)))
                .andExpect(jsonPath("$.employees", hasSize(2)))
                .andExpect(jsonPath("$.employees[0].id", is(13)))
                .andExpect(jsonPath("$.employees[0].name", is("boot1")))
                .andExpect(jsonPath("$.employees[0].age", is(16)))
                .andExpect(jsonPath("$.employees[0].gender", is("Male")))
                .andExpect(jsonPath("$.employees[0].salary", is(4000)));
    }

    @Test
    public void should_return_employees_when_get_company_employees() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/companies/1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(13)))
                .andExpect(jsonPath("$[0].name", is("boot1")))
                .andExpect(jsonPath("$[0].age", is(16)))
                .andExpect(jsonPath("$[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].salary", is(4000)));
    }

    @Test
    public void should_return_correct_company_when_create() throws Exception {
        Company newCompany = new Company(10, "Test", 0, new ArrayList<>());
        Mockito.when(companyRepository.save(Mockito.any()))
                .thenReturn(newCompany);

        mvc.perform(MockMvcRequestBuilders
                .post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"companyName\": \"Test\"," +
                        "\"employeeNumber\": 0," +
                        "\"employees\": []" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.companyName", is("Test")))
                .andExpect(jsonPath("$.employeeNumber", is(0)))
                .andExpect(jsonPath("$.employees", is(new ArrayList<>())));

        Company preCreateCompany = new Company(null, "Test", 0, new ArrayList<>());
        Mockito.verify(companyRepository, Mockito.times(1)).save(preCreateCompany);
    }

    @Test
    public void should_return_200_when_delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/companies/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_correct_company_when_update() throws Exception {
        Company updatedCompany = new Company(1, "New name", 0, new ArrayList<>());
        Mockito.when(companyRepository.save(Mockito.any()))
                .thenReturn(updatedCompany);

        mvc.perform(MockMvcRequestBuilders
                .put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"companyName\": \"New name\"," +
                        "\"employeeNumber\": 0," +
                        "\"employees\": []" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.companyName", is("New name")))
                .andExpect(jsonPath("$.employeeNumber", is(0)))
                .andExpect(jsonPath("$.employees", is(new ArrayList<>())));

        Mockito.verify(companyRepository, Mockito.times(1)).save(updatedCompany);
    }
}
