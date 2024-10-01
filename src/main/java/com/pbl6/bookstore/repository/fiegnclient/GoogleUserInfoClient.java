package com.pbl6.bookstore.repository.fiegnclient;

import com.pbl6.bookstore.dto.response.GoogleUserInfoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleUserInfoClient", url = "https://www.googleapis.com")
public interface GoogleUserInfoClient {

    @GetMapping(value = "/oauth2/v1/userinfo")
    GoogleUserInfoResponseDTO getUserInfo(@RequestParam("alt") String alt,
                                          @RequestParam("access_token") String accessToken);

}
