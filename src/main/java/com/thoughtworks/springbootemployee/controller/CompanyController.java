package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.request.CompanyRequest;
import com.thoughtworks.springbootemployee.model.response.CompanyResponse;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyService.getAll();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getAll(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companyService.getAll(page, pageSize);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse get(@PathVariable Integer companyId) {
        return companyService.get(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployees(@PathVariable Integer companyId) {
        return companyService.getEmployees(companyId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody CompanyRequest company) {
        return companyService.create(company);
    }

    @DeleteMapping("/{companyId}")
    public void deleteEmployeesInCompany(@PathVariable Integer companyId) {
        companyService.deleteEmployeesInCompany(companyId);
    }

    @PutMapping("/{companyId}")
    public CompanyResponse update(@PathVariable Integer companyId, @RequestBody CompanyRequest company) {
        return companyService.update(companyId, company);
    }
}
