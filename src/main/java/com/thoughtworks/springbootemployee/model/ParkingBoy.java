package com.thoughtworks.springbootemployee.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
