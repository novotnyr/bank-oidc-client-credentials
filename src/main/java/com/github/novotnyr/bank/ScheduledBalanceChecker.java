package com.github.novotnyr.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledBalanceChecker {
    public static final Logger logger = LoggerFactory.getLogger(ScheduledBalanceChecker.class);

    @Autowired
    private WebClient webClient;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void checkBalance() {
        int accountId = 1;
        BigDecimal balance = webClient.get()
                                      .uri("http://localhost:9999/accounts/{accountId}/balance", accountId)
                                      .retrieve()
                                      .bodyToMono(BigDecimal.class)
                                      .block();
        logger.info("Account {} has balance {}", accountId, balance);
    }
}
