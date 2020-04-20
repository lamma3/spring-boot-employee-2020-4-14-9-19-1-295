package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.mapper.ParkingBoyMapper;
import com.thoughtworks.springbootemployee.model.db.ParkingBoy;
import com.thoughtworks.springbootemployee.model.request.ParkingBoyRequest;
import com.thoughtworks.springbootemployee.model.response.ParkingBoyResponse;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingBoyMapper parkingBoyMapper;

    public List<ParkingBoyResponse> getAll() {
        List<ParkingBoy> parkingBoys = parkingBoyRepository.findAll();
        return parkingBoyMapper.parkingBoyListToParkingBoyResponseList(parkingBoys);
    }

    public ParkingBoyResponse create(ParkingBoyRequest parkingBoyRequest) {
        ParkingBoy parkingBoy = parkingBoyMapper.parkingBoyRequestToParkingBoy(parkingBoyRequest);
        ParkingBoy savedParkingBoy = parkingBoyRepository.save(parkingBoy);
        return parkingBoyMapper.parkingBoyToParkingBoyResponse(savedParkingBoy);
    }
}
