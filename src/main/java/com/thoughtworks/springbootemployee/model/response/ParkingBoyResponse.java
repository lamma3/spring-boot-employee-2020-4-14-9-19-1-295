package com.thoughtworks.springbootemployee.model.response;

import com.thoughtworks.springbootemployee.model.db.Employee;
import lombok.Data;

@Data
public class ParkingBoyResponse {
    private Integer id;
    private String nikeName;
    private Integer employeeId;
    private Employee employee;
}
