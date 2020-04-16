package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public List<Company> getAll(Integer page, Integer pageSize) {
        return companyRepository.findAll(page, pageSize);
    }

    public Company get(Integer companyId) {
        return companyRepository.findById(companyId);
    }

    public void create(Company company) {
        companyRepository.add(company);
    }

    public void deleteEmployeesInCompany(Integer companyId) {
        Company company = companyRepository.findById(companyId);
        company.setEmployees(new ArrayList<>());
    }

    public void update(Integer companyId, Company companyUpdate) {
        Company company = companyRepository.findById(companyId);
        if (companyUpdate.getCompanyName() != null) {
            company.setCompanyName(companyUpdate.getCompanyName());
        }
        if (companyUpdate.getEmployeeNumber() != null) {
            company.setEmployeeNumber(companyUpdate.getEmployeeNumber());
        }
        if (companyUpdate.getEmployees() != null) {
            company.setEmployees(companyUpdate.getEmployees());
        }
    }

    public List<Employee> getEmployees(Integer companyId) {
        Company company = companyRepository.findById(companyId);
        return company.getEmployees();
    }
}