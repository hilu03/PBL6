package com.pbl6.bookstore.repository.fiegnclient;

import com.pbl6.bookstore.dto.request.ExchangeTokenRequestDTO;
import com.pbl6.bookstore.dto.response.ExchangeTokenResponseDTO;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "googleAuthClient", url = "https://oauth2.googleapis.com")
public interface GoogleAuthClient {

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponseDTO exchangeToken(@QueryMap ExchangeTokenRequestDTO request);

}
