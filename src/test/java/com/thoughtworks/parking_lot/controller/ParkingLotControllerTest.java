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
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        ResultActions resultOfExecution = mvc.perform(delete("/parking-lot/{name}", "ParkingLotTest"));

        resultOfExecution.andExpect(status().isOk());
    }

    @Test
    void should_show_parking_lot_when_no_name_is_specified() throws Exception {
        ParkingLot parkingLot = createParkingLot("ParkingLotTest");

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "1");
        requestParams.add("pageSize", "5");

        parkingLotList.add(parkingLot);

        when(parkingLotService.showAllParkingLotsByPage(anyInt(), anyInt())).thenReturn(parkingLotList);

        ResultActions resultOfExecution = mvc.perform(get("/parking-lot").params(requestParams));

        resultOfExecution.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is("ParkingLotTest")));
    }

    @Test
    void should_show_parking_lot_when_entered_specific_name() throws Exception {
        ParkingLot parkingLot = createParkingLot("ParkingLotTest");

        when(parkingLotService.showParkingLotByName(anyString())).thenReturn(parkingLot);

        ResultActions resultOfExecution = mvc.perform(get("/parking-lot/{name}", "ParkingLotTest"));

        resultOfExecution.andExpect(status().isOk());
    }

    @Test
    void should_update_parking_lot_when_new_detail_is_entered() throws Exception {
        ParkingLot parkingLot = createParkingLot("ParkingLotTest");
        ParkingLot newParkingLot = createParkingLot("ParkingLotTest2");

        when(parkingLotService.editParkingLot(anyString(), any())).thenReturn(newParkingLot);

        ResultActions resultOfExecution = mvc.perform(patch("/parking-lot/{name}", "ParkingLotTest")
                .content(objectMapper.writeValueAsString(parkingLot))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isOk())
            .andExpect(jsonPath("$.name" , is(newParkingLot.getName())));
    }
}