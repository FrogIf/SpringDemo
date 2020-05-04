package sch.frog.learn.spring.bucks.controller;

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
import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.common.web.exception.FormValidationException;
import sch.frog.learn.spring.common.web.request.NewCoffeeRequest;
import sch.frog.learn.spring.jpademo.service.CoffeeService;

import javax.validation.Valid;
import javax.validation.ValidationException;
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

    /*
     * 添加jackson-dataformat-xml依赖之后
     * 可以根据请求头中的Accept, 判断返回:
     * application/xml
     * application/json;utf-8
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
//    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Coffee getById(@PathVariable("id") Long id){
        Coffee coffee = coffeeService.getCoffee(id);
        log.info("coffee : {}", coffee);
        return coffee;
//        return coffeeService.getCoffee(id);   // 配置hibernate5Module之后, 这样只会返回null
    }

    @GetMapping(path = "/", params = "name")
    @ResponseBody
    public Coffee getByName(@RequestParam String name){
        return coffeeService.findOneCoffee(name).orElse(null);
    }

    /*
     * 这里会使用spring mvc自己的逻辑进行coffee请求参数的解析
     */
    @PostMapping(path = "/", consumes =  MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public Coffee addCoffeeWithBindingResultForForm(@Valid NewCoffeeRequest coffee, BindingResult result){
        if(result.hasErrors()){
            throw new FormValidationException(result);
        }
        return coffeeService.saveCoffee(coffee.getName(), coffee.getPrice());
    }

    /*
     * 这里会使用jackson解析coffee请求参数
     */
    @PostMapping(path = "/", consumes =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Coffee addCoffeeWithBindingResultForJson(@Valid @RequestBody NewCoffeeRequest coffee, BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.toString());
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
