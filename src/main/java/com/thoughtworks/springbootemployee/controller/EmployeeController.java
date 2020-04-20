package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.request.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService.getAll();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getPaginatedAll(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return employeeService.getAll(page, pageSize);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam String gender) {
        return employeeService.getByGender(gender);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse get(@PathVariable Integer employeeId) {
        return employeeService.get(employeeId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.create(employeeRequest);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Integer employeeId) {
        employeeService.delete(employeeId);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employee) {
        return employeeService.update(employeeId, employee);
    }
}
