package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.db.Employee;
import com.thoughtworks.springbootemployee.model.db.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ParkingBoyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParkingBoyRepository parkingBoyRepository;

    @Before
    public void setUp() {
        ParkingBoy parkingBoyAlex = new ParkingBoy(1, "Alex", 2, new Employee());
        ParkingBoy parkingBoyBob = new ParkingBoy(2, "Bob", 3, new Employee());

        List<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(parkingBoyAlex);
        parkingBoys.add(parkingBoyBob);

        Mockito.when(parkingBoyRepository.findAll())
                .thenReturn(parkingBoys);
    }

    @Test
    public void should_return_all_parking_boys_when_get_all() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/parking-boys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nikeName", is("Alex")))
                .andExpect(jsonPath("$[0].employeeId", is(2)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nikeName", is("Bob")))
                .andExpect(jsonPath("$[1].employeeId", is(3)));
    }

    @Test
    public void should_return_new_parking_boys_when_create() throws Exception {
        ParkingBoy newParkingBoy = new ParkingBoy(3, "Test", 5, null);
        Mockito.when(parkingBoyRepository.save(Mockito.any()))
                .thenReturn(newParkingBoy);

        mvc.perform(MockMvcRequestBuilders
                .post("/parking-boys")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"nikeName\": \"Test\"," +
                        "\"employeeId\": 5" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nikeName", is("Test")))
                .andExpect(jsonPath("$.employeeId", is(5)));

        ParkingBoy preCreateParkingBoy = new ParkingBoy(null, "Test", 5, null);
        Mockito.verify(parkingBoyRepository, Mockito.times(1)).save(preCreateParkingBoy);
    }
}
