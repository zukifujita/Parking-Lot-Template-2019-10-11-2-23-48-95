package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
public class ParkingLotControllerTest {

    @MockBean
    ParkingLotService parkingLotService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    private List<ParkingLot> parkingLotList = new ArrayList<>();

    private ParkingLot createParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setCapacity(25);
        parkingLot.setLocation("Manila");

        return parkingLot;
    }

    @Test
    void should_add_parking_lot_when_added_new_detail() throws Exception {
        ParkingLot parkingLot = createParkingLot("ParkingLotTest");

        when(parkingLotService.addParkingLot(parkingLot)).thenReturn(parkingLot);

        ResultActions resultOfExecution = mvc.perform(post("/parking-lot")
                .content(objectMapper.writeValueAsString(parkingLot))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isCreated());
    }

    @Test
    void should_delete_parking_lot_when_name_is_specified() throws Exception {
        ParkingLot parkingLot = createParkingLot("ParkingLotTest");

        when(parkingLotService.deleteParkingLot(anyString())).thenReturn(parkingLot);

        ResultActions resultOfExecution = mvc.perform(delete("/parking-lot/{id}", 1L));

        resultOfExecution.andExpect(status().isOk());
    }

    @Test
    void should_show_parking_lot_when_no_name_is_specified() throws Exception {
        ParkingLot parkingLot = createParkingLot("ParkingLotTest");

        when(parkingLotService.showAllParkingLotsByPage(anyInt(), anyInt()));
    }
}