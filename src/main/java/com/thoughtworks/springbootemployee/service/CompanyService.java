package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.db.Company;
import com.thoughtworks.springbootemployee.model.db.Employee;
import com.thoughtworks.springbootemployee.model.request.CompanyRequest;
import com.thoughtworks.springbootemployee.model.response.CompanyResponse;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<CompanyResponse> getAll() {
        List<Company> companies = companyRepository.findAll();
        return companyMapper.companyListToCompanyResponseList(companies);
    }

    public List<CompanyResponse> getAll(Integer page, Integer pageSize) {
        List<Company> companies = companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
        return companyMapper.companyListToCompanyResponseList(companies);
    }

    public CompanyResponse get(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        return companyMapper.companyToCompanyResponse(company);
    }

    public CompanyResponse create(CompanyRequest companyRequest) {
        Company company = companyMapper.companyRequestToCompany(companyRequest);
        Company savedCompany = companyRepository.save(company);
        return companyMapper.companyToCompanyResponse(savedCompany);
    }

    public void deleteEmployeesInCompany(Integer companyId) {
        companyRepository.findById(companyId)
                .ifPresent(company -> company.setEmployees(new ArrayList<>()));
    }

    public CompanyResponse update(Integer companyId, CompanyRequest companyRequest) {
        Company companyUpdate = companyMapper.companyRequestToCompany(companyRequest);
        Company company = companyRepository.findById(companyId).orElse(null);

        if (company == null) {
            return null;
        }

        if (companyUpdate.getCompanyName() != null) {
            company.setCompanyName(companyUpdate.getCompanyName());
        }
        if (companyUpdate.getEmployeeNumber() != null) {
            company.setEmployeeNumber(companyUpdate.getEmployeeNumber());
        }
        if (companyUpdate.getEmployees() != null) {
            company.setEmployees(companyUpdate.getEmployees());
        }
        Company savedCompany = companyRepository.save(company);
        return companyMapper.companyToCompanyResponse(savedCompany);
    }

    public List<EmployeeResponse> getEmployees(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            return null;
        }
        List<Employee> employees = company.getEmployees();
        return employeeMapper.employeeListToEmployeeResponseList(employees);
    }
}
