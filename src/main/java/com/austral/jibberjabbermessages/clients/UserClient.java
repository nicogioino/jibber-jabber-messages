package com.austral.jibberjabbermessages.clients;

import com.austral.jibberjabbermessages.dtos.ReducedUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {
    private final String USER_SERVICE_URL = "http://jibber-jabber-user:8080/users";
    private final RestTemplate restTemplate;


    @Autowired
    public UserClient() {
        this.restTemplate = new RestTemplate();
    }

    public ReducedUserDto getUserById(String userId) {
        String url = USER_SERVICE_URL + "/" + userId;
        ResponseEntity<ReducedUserDto> response = restTemplate.getForEntity(url, ReducedUserDto.class);
        return response.getBody();
    }
}
