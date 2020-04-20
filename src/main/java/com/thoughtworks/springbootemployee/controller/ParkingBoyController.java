package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.request.ParkingBoyRequest;
import com.thoughtworks.springbootemployee.model.response.ParkingBoyResponse;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService parkingBoyService;

    @GetMapping
    public List<ParkingBoyResponse> getAll() {
        return parkingBoyService.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingBoyResponse create(@RequestBody ParkingBoyRequest parkingBoy) {
        return parkingBoyService.create(parkingBoy);
    }
}
