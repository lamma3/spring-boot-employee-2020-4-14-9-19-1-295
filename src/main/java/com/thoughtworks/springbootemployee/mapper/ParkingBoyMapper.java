package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.db.ParkingBoy;
import com.thoughtworks.springbootemployee.model.request.ParkingBoyRequest;
import com.thoughtworks.springbootemployee.model.response.ParkingBoyResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ParkingBoyMapper {
    ParkingBoy parkingBoyRequestToParkingBoy(ParkingBoyRequest parkingBoyRequest);
    ParkingBoyResponse parkingBoyToParkingBoyResponse(ParkingBoy parkingBoy);
    List<ParkingBoyResponse> parkingBoyListToParkingBoyResponseList(List<ParkingBoy> parkingBoys);
}
