package sch.frog.learn.spring.bucks.remote.barista.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sch.frog.learn.spring.bucks.remote.barista.BaristaService;

@Service
public class BaristaServiceImpl implements BaristaService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String touch() {
        ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:8093/demo/touch", String.class);
        return resp.getBody();
    }

}
