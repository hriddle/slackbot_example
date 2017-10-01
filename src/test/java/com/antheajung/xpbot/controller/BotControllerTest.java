package com.antheajung.xpbot.controller;

import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.service.BotService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BotControllerTest {
    private BotService botService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        botService = mock(BotService.class);
        BotController botController = new BotController(botService);

        mockMvc = MockMvcBuilders.standaloneSetup(botController).build();
    }

    @Test
    public void listNames_callsBotService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/allNames"))
                .andExpect(status().isOk());

        verify(botService).getAllNames();
    }

    @Test
    public void sendMessageAsBot_callsBotService() throws Exception {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest().build();

        JSONObject xpBotRequestJson = new JSONObject(xpBotRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/sendMessageAsBot")
                .content(xpBotRequestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(botService).sendMessageAsBot(xpBotRequest);
    }
}