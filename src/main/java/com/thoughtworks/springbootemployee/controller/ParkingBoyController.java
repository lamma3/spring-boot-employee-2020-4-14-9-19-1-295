package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService parkingBoyService;

    @GetMapping
    public List<ParkingBoy> getAll() {
        return parkingBoyService.getAll();
    }
}
