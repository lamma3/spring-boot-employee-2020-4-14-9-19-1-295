package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ParkingBoyControllerTest {

    @Autowired
    private ParkingBoyController parkingBoyController;

    @MockBean
    private ParkingBoyRepository parkingBoyRepository;

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(parkingBoyController);

        ParkingBoy parkingBoyAlex = new ParkingBoy(1, "Alex", 2, new Employee());
        ParkingBoy parkingBoyBob = new ParkingBoy(2, "Bob", 3, new Employee());

        List<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(parkingBoyAlex);
        parkingBoys.add(parkingBoyBob);

        Mockito.when(parkingBoyRepository.findAll())
                .thenReturn(parkingBoys);
    }

    @Test
    public void should_return_all_parking_boys_when_get_all() {
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .get("/parking-boys");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Assert.assertEquals(2, response.jsonPath().getList("$").size());

        Assert.assertEquals(1, response.jsonPath().getInt("[0].id"));
        Assert.assertEquals("Alex", response.jsonPath().get("[0].nikeName"));
        Assert.assertEquals(2, response.jsonPath().getInt("[0].employeeId"));

        Assert.assertEquals(2, response.jsonPath().getInt("[1].id"));
        Assert.assertEquals("Bob", response.jsonPath().get("[1].nikeName"));
        Assert.assertEquals(3, response.jsonPath().getInt("[1].employeeId"));
    }

    @Test
    public void should_return_new_parking_boys_when_create() {
        ParkingBoy newParkingBoy = new ParkingBoy(3, "Test", 5, null);
        Mockito.when(parkingBoyRepository.save(Mockito.any()))
                .thenReturn(newParkingBoy);

        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body("{" +
                        "\"nickName\": \"Test\"," +
                        "\"employeeId\": 5" +
                        "}")
                .post("/parking-boys");

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        Assert.assertEquals(3, response.jsonPath().getInt("id"));
        Assert.assertEquals("Test", response.jsonPath().get("nikeName"));
        Assert.assertEquals(5, response.jsonPath().getInt("employeeId"));
    }
}
