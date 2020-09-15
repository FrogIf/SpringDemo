package sch.frog.learn.spring.bucks.remote.barista;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "frog-barista", contextId = "demo", path = "/demo", qualifier = "coffeeService")
public interface BaristaService {

    @RequestMapping(path = "/touch")
    String touch();
}
