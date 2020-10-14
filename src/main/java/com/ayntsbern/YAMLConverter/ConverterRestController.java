package com.ayntsbern.YAMLConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Map;


@RestController
public class ConverterRestController {
    @PostMapping("/generate")
    public String login(@RequestParam(name = "sessionId", required = true) String sessionId,
                        @RequestParam(name = "method", defaultValue = "GET") String method,
                        @RequestParam(name = "URL", required = true) String URL,
                        @RequestBody(required = false) Map<String,Object> body) {
        try {
            RemoteCall remoteCall = new RemoteCall()
                    .setMethod(method);
            if (body != null) {

            }
            remoteCall
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
