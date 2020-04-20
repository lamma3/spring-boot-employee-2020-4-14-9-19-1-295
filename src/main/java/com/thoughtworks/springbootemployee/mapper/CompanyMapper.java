package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.db.Company;
import com.thoughtworks.springbootemployee.model.request.CompanyRequest;
import com.thoughtworks.springbootemployee.model.response.CompanyResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {
    Company companyRequestToCompany(CompanyRequest companyRequest);
    CompanyResponse companyToCompanyResponse(Company company);
    List<CompanyResponse> companyListToCompanyResponseList(List<Company> companies);
}
