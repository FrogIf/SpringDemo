package frog.learn.spring.bucks.controller;

import frog.learn.spring.bucks.controller.request.NewCoffeeRequest;
import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.service.CoffeeService;
import frog.learn.spring.mybatis.common.model.MCoffee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/coffee")
@Controller
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(path = "/", params = "!name")
    @ResponseBody
    public List<Coffee> getAll(){
        return coffeeService.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Coffee getById(@PathVariable("id") Long id){
        return coffeeService.getCoffee(id);
    }

    @GetMapping(path = "/", params = "name")
    @ResponseBody
    public Coffee getByName(@RequestParam String name){
        return coffeeService.findOneCoffee(name).orElse(null);
    }

    @PostMapping(path = "/", consumes =  MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public Coffee addCoffeeWithBindingResult(@Valid NewCoffeeRequest coffee, BindingResult result){
        if(result.hasErrors()){
            // TODO 优化为自定义异常
            log.warn("binding error : {}", result);
            return null;
        }
        return coffeeService.saveCoffee(coffee.getName(), coffee.getPrice());
    }

//    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ResponseBody
//    public Coffee addCoffee(@Valid NewCoffeeRequest coffee){
//        return coffeeService.saveCoffee(coffee.getName(), coffee.getPrice());
//    }

    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file){
        List<Coffee> coffees = new ArrayList<>();
        if(!file.isEmpty()){
            try {
                try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        String[] split = StringUtils.split(line,",");
                        if(split != null && split.length == 2){
                            coffees.add(Coffee.builder()
                                    .name(split[0])
                                    .price(Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(split[1])))
                                    .build()
                            );
                        }
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        // TODO save to database
        return coffees;
    }

}
