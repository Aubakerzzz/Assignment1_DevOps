package com.krimo.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krimo.account.data.AccountFactory;
import com.krimo.account.dto.AccountDTO;
import com.krimo.account.model.Account;
import com.krimo.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private static final String CACHE_NAME = "accounts";

    @Mock private AccountRepository accountRepository;

    @Mock private Cache cache;
    @Mock private CacheManager cacheManager;
    @Mock private ObjectMapper objectMapper;
    @Autowired @InjectMocks
    private AccountServiceImpl accountService;

    @Captor ArgumentCaptor<Account> captor;

    Account account = AccountFactory.accountInit();
    AccountDTO dto = AccountFactory.dtoInit();

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountRepository, cacheManager, objectMapper);
    }

    @Test
    void createAccount() {
        when(accountRepository.saveAndFlush(captor.capture())).thenReturn(account);
        accountService.createAccount(dto);
        verify(accountRepository, times(1)).saveAndFlush(captor.getValue());
        assertThat(captor.getValue()).usingRecursiveComparison()
                .ignoringFields("id", "registeredAt")
                .isEqualTo(account);

    }

    @Test
    void updateAccount() throws JsonProcessingException {
        AccountDTO dtoToCache = AccountFactory.updateDtoInit();
        String dtoCache = objectMapper.writeValueAsString(dtoToCache);

        when(accountRepository.findById(anyLong())).thenReturn(ofNullable(account));
        when(cacheManager.getCache(CACHE_NAME)).thenReturn(cache);

        accountService.updateAccount(1L, dto);
        account.setLastName(dtoToCache.getLastName());
        verify(accountRepository, times(1)).save(captor.capture());

        verify(cacheManager).getCache(CACHE_NAME);
        verify(cache).put(dto.getId(), dtoCache);

        assertThat(captor.getValue()).usingRecursiveComparison()
                .ignoringFields("registeredAt")
                .isEqualTo(account);

    }

    @Test
    void getAccount_cacheHit() throws Exception {
        when(cacheManager.getCache(CACHE_NAME)).thenReturn(cache);
        when(cache.get(1L)).thenReturn(new SimpleValueWrapper("cachedAccountDTO"));
        when(objectMapper.readValue("cachedAccountDTO", AccountDTO.class)).thenReturn(dto);

        assertEquals(dto, accountService.getAccount(1L));

        verify(cacheManager).getCache(CACHE_NAME);
        verify(objectMapper).readValue("cachedAccountDTO", AccountDTO.class);
        verifyNoInteractions(accountRepository); // Cache hit, no DB call
    }

    @Test
    void getAccount_cacheMiss() {
        when(cacheManager.getCache(CACHE_NAME)).thenReturn(cache);
        when(cache.get(1L)).thenReturn(null);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThat(dto).usingRecursiveComparison()
                .ignoringFields("registeredAt")
                .isEqualTo(accountService.getAccount(1L));

        verify(cacheManager).getCache(CACHE_NAME);
    }
    @Test
    void getUserEmail() {
        when(accountRepository.getReferenceById(1L)).thenReturn(account);
        assertEquals(account.getEmail(), accountService.getUserEmail(1L));
    }

    @Test
    void deleteAccount() {
        when(cacheManager.getCache(CACHE_NAME)).thenReturn(cache);
        accountService.deleteAccount(1L);
        verify(cache).evict(1L);
    }
}