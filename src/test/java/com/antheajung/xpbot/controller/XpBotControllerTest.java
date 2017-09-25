package com.antheajung.xpbot.controller;

import com.antheajung.xpbot.domain.XpBotResponse;
import com.antheajung.xpbot.service.XpBotService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@Profile("test")
public class XpBotControllerTest {
    private XpBotService xpBotService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        xpBotService = mock(XpBotService.class);
        XpBotController xpBotController = new XpBotController(xpBotService);

        mockMvc = MockMvcBuilders.standaloneSetup(xpBotController).build();
    }

    @Test
    public void listNames_callsXpBotService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/listAllNames"));
        verify(xpBotService).sendAllNamesFrom(any());
    }

    @Test
    public void listNames_returnsAListOfNamesUsedByXpBot() throws Exception {
        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(singletonList("Anna"))
                .build();

        when(xpBotService.sendAllNamesFrom(any())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/listAllNames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("message")
                        .value(expectedResponse.getMessage().get(0)));
    }

    @Test
    public void sendMessage_callsXpBotService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sendMessage")
                .param("message", "This is a message"));

        verify(xpBotService).sendMessageAsBot("This is a message");
    }

    @Test
    public void sendMessage_returnsResponseStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sendMessage")
                .param("message", "This is a message"))
                .andExpect(status().isOk());
    }
}