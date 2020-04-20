package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.db.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public List<ParkingBoy> getAll() {
        return parkingBoyRepository.findAll();
    }

    public ParkingBoy create(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }
}
