package com.thoughtworks.springbootemployee.model.request;

import com.thoughtworks.springbootemployee.model.db.Employee;
import lombok.Data;

@Data
public class ParkingBoyRequest {
    private String nikeName;
    private Integer employeeId;
    private Employee employee;
}
