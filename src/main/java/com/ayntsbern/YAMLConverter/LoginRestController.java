package com.ayntsbern.YAMLConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;


@RestController
public class LoginRestController {

    @GetMapping("/login")
    public String getSessionId(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "URL") String URL) {
        try {
            RemoteCall remoteCall = new RemoteCall()
                    .setMethod(HttpMethod.POST)
                    .setBody(new LoginRequest(username, password))
                    .setURL(URL);
            remoteCall.makeRequest();

            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            LoginResponse response = objectMapper.readValue(remoteCall.getResponseBody(),
                    LoginResponse.class);
            return response.getSessionId();
        } catch (HttpClientErrorException | JsonProcessingException httpClientErrorException) {
            return httpClientErrorException.getMessage();
        }
    }
}
