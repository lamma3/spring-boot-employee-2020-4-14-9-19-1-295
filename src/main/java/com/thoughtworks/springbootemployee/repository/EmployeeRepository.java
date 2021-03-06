package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.db.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByGenderIgnoreCase(String gender);
}
