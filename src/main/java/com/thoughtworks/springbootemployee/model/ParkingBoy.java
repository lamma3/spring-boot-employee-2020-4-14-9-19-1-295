package com.thoughtworks.springbootemployee.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parking_boys")
public class ParkingBoy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nikeName;
    private Integer employeeId;

    @OneToOne
    @JoinColumn(name = "employeeId", updatable = false, insertable = false)
    private Employee employee;
}
