package sch.frog.learn.spring.client;

import org.springframework.core.annotation.Order;
import sch.frog.learn.spring.common.constant.CommonConstant;
import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.web.request.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

//@Component
@Slf4j
//@Order(1)
public class RestTemplateTestRunner implements ApplicationRunner {

    @Resource(name = "loadBalanceRestTemplate")
    private RestTemplate loadBalanceRestTemplate;

    @Resource(name = "normalRestTemplate")
    private RestTemplate normalRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private final String serviceInstanceName = "frog-coffee";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        showServiceInstances();
        normalRestTemplateTest();

        log.info("=====================");

        readMenu();
        queryOrder(orderCoffee());
    }


    private void readMenu(){
        ParameterizedTypeReference<List<Coffee>> ptr = new ParameterizedTypeReference<List<Coffee>>() {};
        ResponseEntity<List<Coffee>> responseEntity = loadBalanceRestTemplate.exchange("http://" + serviceInstanceName + "/coffee/", HttpMethod.GET, null, ptr);
        responseEntity.getBody().forEach(c -> log.info("Coffee : {}", c));
    }


    /*
     * 创建一个coffee订单
     */
    private long orderCoffee(){
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("jkjkjk")
                .items(Arrays.asList("capuccino"))
                .build();
        RequestEntity<NewOrderRequest> request = RequestEntity.post(UriComponentsBuilder.fromUriString("http://" + serviceInstanceName + "/order/").build().toUri())
                .body(orderRequest);
        ResponseEntity<CoffeeOrder> response = loadBalanceRestTemplate.exchange(request, CoffeeOrder.class);
        log.info("Order Request Status Code : {}", response.getStatusCode());
        Long id = response.getBody().getId();
        log.info("Order id : {}", id);
        return id;
    }

    private void queryOrder(long id){
        CoffeeOrder order = loadBalanceRestTemplate.getForObject("http://" + serviceInstanceName + "/order/{id}", CoffeeOrder.class, id);
        log.info("CoffeeOrder : {}", order);
    }

    /*
     * 获取服务实例
     */
    private void showServiceInstances(){
        log.info("DiscoveryClient : {}", discoveryClient.getClass().getName());
        discoveryClient.getInstances(serviceInstanceName)
                .forEach(s -> log.info("Host : {}, Port : {}", s.getHost(), s.getPort()));
    }

    /*
     * 普通的rest template 使用演示
     */
    private void normalRestTemplateTest() {
        // -----------------query by id-------------
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/coffee/{id}").build(1);
        ResponseEntity<Coffee> entity = normalRestTemplate.getForEntity(uri, Coffee.class);
        log.info("Response status : {}, Response Headers : {}", entity.getStatusCode(), entity.getHeaders().toString());
        log.info("Coffee : {}", entity.getBody());

        // 设置请求头 content-type : application/xml
        RequestEntity<Void> requestEntity = RequestEntity.get(uri).accept(MediaType.APPLICATION_XML).build();
        ResponseEntity<String> resp = normalRestTemplate.exchange(requestEntity, String.class);
        log.info("Response status : {}, Response Headers : {}", resp.getStatusCode(), resp.getHeaders().toString());
        log.info("Coffee : {}", resp.getBody());

        // --------------create new coffee---------------
        String coffeeUri = "http://localhost:8080/coffee/";
        Coffee coffee = Coffee.builder()
                .name("jkjk")
                .price(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 12.3))  // MoneySerializer会保证jackson支持将money转为数字
                .build();
        ResponseEntity<Coffee> responseEntity = normalRestTemplate.postForEntity(coffeeUri, coffee, Coffee.class);
        log.info("New Coffee : {}", responseEntity);

        // -----------------findAll------------
        String s = normalRestTemplate.getForObject(coffeeUri, String.class);
        log.info("String : {}", s);

        // find all return list
        ParameterizedTypeReference<List<Coffee>> typeReference
                = new ParameterizedTypeReference<List<Coffee>>(){};  // 为防止泛型擦除, 这里必须传入这个对象, 另外需要注意, 语句末尾的大括号!
        ResponseEntity<List<Coffee>> listResp = normalRestTemplate.exchange(coffeeUri, HttpMethod.GET, null, typeReference);
        listResp.getBody().forEach(c -> log.info("Coffee : {}", c));


        log.info("--------------------create order------------------------");
        long id = createOrder();

        CoffeeOrder coffeeOrder = normalRestTemplate.getForObject("http://localhost:8080/order/{id}", CoffeeOrder.class, id);
        log.info("Order : {}", coffeeOrder);
    }



    private long createOrder(){
        NewOrderRequest request = NewOrderRequest.builder()
                .customer("frog")
                .items(Arrays.asList("latte", "capuccino"))
                .build();
        RequestEntity<NewOrderRequest> requestEntity = RequestEntity.post(UriComponentsBuilder.fromUriString("http://localhost:8080/order/").build().toUri())
                .body(request);

        ResponseEntity<CoffeeOrder> resp = normalRestTemplate.exchange(requestEntity, CoffeeOrder.class);
        log.info("Order Request Status Code : {}", resp.getStatusCode());
        Long id = resp.getBody().getId();
        log.info("Order ID : {}", id);
        return id;
    }

}
