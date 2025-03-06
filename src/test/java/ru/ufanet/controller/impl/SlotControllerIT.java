package ru.ufanet.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ufanet.mapper.SlotMapper;
import ru.ufanet.repository.SlotRepository;
import ru.ufanet.repository.UserRepository;
import ru.ufanet.util.CurrentUserUtil;
import ru.ufanet.util.CurrentYearHolidays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class SlotControllerIT {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private CurrentYearHolidays currentYearHolidays;

    @Autowired
    private SlotMapper slotMapper;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void getFreeSlots() throws Exception {

        mockMvc.perform(get("/pool/timetable/free")
                        .param("date", "2025-03-06"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Доступные записи получены")))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}