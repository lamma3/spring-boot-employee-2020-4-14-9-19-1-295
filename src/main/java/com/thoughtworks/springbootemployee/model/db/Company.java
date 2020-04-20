package com.thoughtworks.springbootemployee.model.db;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    private Integer employeeNumber;

    @OneToMany(mappedBy = "companyId", fetch = FetchType.LAZY)
    private List<Employee> employees;
}
