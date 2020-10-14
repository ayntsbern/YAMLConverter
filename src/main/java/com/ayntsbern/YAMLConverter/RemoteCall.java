package com.ayntsbern.YAMLConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RemoteCall {
    private HttpMethod method;
    private String body;
    private String URI;
    private ResponseEntity<String> responseEntity;

    public RemoteCall setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RemoteCall setMethod(String method) {
        switch (method) {
            case "GET":
                this.method = HttpMethod.GET;
                break;
            case "POST":
                this.method = HttpMethod.POST;
                break;
            case "PUT":
                this.method = HttpMethod.PUT;
                break;
            case "PATCH":
                this.method = HttpMethod.PATCH;
                break;
        }
        return this;
    }

    public RemoteCall setBody(Object object) throws JsonProcessingException {
        body = new ObjectMapper().writeValueAsString(object);
        return this;
    }

    public RemoteCall setURL(String URL) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL);
        URI = builder.toUriString();
        return this;
    }

    public void makeRequest() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> httpMessageConverter = new ArrayList<>();
        httpMessageConverter.add(
                new StringHttpMessageConverter(StandardCharsets.UTF_8)
        );
        restTemplate.setMessageConverters(httpMessageConverter);
        HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        responseEntity = restTemplate.exchange(
                URI, method, entity, String.class
        );
    }

    public String getResponseBody() {
        return responseEntity.getBody();
    }
}
