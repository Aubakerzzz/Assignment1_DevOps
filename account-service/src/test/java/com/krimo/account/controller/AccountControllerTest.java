package com.krimo.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.krimo.account.data.AccountFactory;
import com.krimo.account.dto.AccountDTO;
import com.krimo.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Slf4j
class AccountControllerTest {

    @MockBean
    private AccountService accountService;
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    AccountDTO dto = AccountFactory.dtoInit();
    String dtoJson;

    @BeforeEach
    void setUp() {

        try {
            objectMapper.registerModule(new JavaTimeModule());
            dtoJson = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("Cannot serialize DTO.");
        }

    }
    @Test
    void createAccount() throws Exception {
        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8084/api/v3/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.message",  org.hamcrest.Matchers.is("Account successfully created.")));

    }

    @Test
    void getAccounts() throws Exception {
        when(accountService.getAccounts(1, 1)).thenReturn(List.of(dto));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8084/api/v3/accounts?pageNo=1&pageSize=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data[0].id",org.hamcrest.Matchers.is(1)))
                .andExpect(jsonPath("$.message",  org.hamcrest.Matchers.is("Accounts successfully retrieved.")));
    }

    @Test
    void getAccount() throws Exception {
        when(accountService.getAccount(1L)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("http://localhost:8084/api/v3/accounts/%s",1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data.id",org.hamcrest.Matchers.is(1)))
                .andExpect(jsonPath("$.message",  org.hamcrest.Matchers.is("Account successfully retrieved.")));
    }

    @Test
    void updateAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put(String.format("http://localhost:8084/api/v3/accounts/%s", 1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message",  org.hamcrest.Matchers.is("Account successfully updated.")));
    }

    @Test
    void deleteAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(String.format("http://localhost:8084/api/v3/accounts/%s", 1)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message",  org.hamcrest.Matchers.is("Account successfully deleted.")));
    }

    @Test
    void getUserEmail() throws Exception {
        when(accountService.getUserEmail(1L)).thenReturn(dto.getEmail());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("http://localhost:8084/api/v3/accounts/%s/email",1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().string(dto.getEmail()));
    }
}