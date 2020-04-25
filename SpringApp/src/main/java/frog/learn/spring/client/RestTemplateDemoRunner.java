package frog.learn.spring.client;

import frog.learn.spring.bucks.controller.request.NewOrderRequest;
import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.model.CoffeeOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 简单的restTemplate演示
 * rest template 感觉类似于封装了 http client, 用于访问web资源
 */
@Component
@Configuration
@Slf4j
public class RestTemplateDemoRunner implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // -----------------query by id-------------
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/coffee/{id}").build(1);
        ResponseEntity<Coffee> entity = restTemplate.getForEntity(uri, Coffee.class);
        log.info("Response status : {}, Response Headers : {}", entity.getStatusCode(), entity.getHeaders().toString());
        log.info("Coffee : {}", entity.getBody());

        // 设置请求头 content-type : application/xml
        RequestEntity<Void> requestEntity = RequestEntity.get(uri).accept(MediaType.APPLICATION_XML).build();
        ResponseEntity<String> resp = restTemplate.exchange(requestEntity, String.class);
        log.info("Response status : {}, Response Headers : {}", resp.getStatusCode(), resp.getHeaders().toString());
        log.info("Coffee : {}", resp.getBody());

        // --------------create new coffee---------------
        String coffeeUri = "http://localhost:8080/coffee/";
        Coffee coffee = Coffee.builder()
                .name("jkjk")
                .price(Money.of(CurrencyUnit.of("CNY"), 12.3))  // MoneySerializer会保证jackson支持将money转为数字
                .build();
        ResponseEntity<Coffee> responseEntity = restTemplate.postForEntity(coffeeUri, coffee, Coffee.class);
        log.info("New Coffee : {}", responseEntity);

        // -----------------findAll------------
        String s = restTemplate.getForObject(coffeeUri, String.class);
        log.info("String : {}", s);

        // find all return list
        ParameterizedTypeReference<List<Coffee>> typeReference
                = new ParameterizedTypeReference<List<Coffee>>(){};  // 为防止泛型擦除, 这里必须传入这个对象, 另外需要注意, 语句末尾的大括号!
        ResponseEntity<List<Coffee>> listResp = restTemplate.exchange(coffeeUri, HttpMethod.GET, null, typeReference);
        listResp.getBody().forEach(c -> log.info("Coffee : {}", c));


        log.info("--------------------create order------------------------");
        long id = createOrder();

        CoffeeOrder coffeeOrder = restTemplate.getForObject("http://localhost:8080/order/{id}", CoffeeOrder.class, id);
        log.info("Order : {}", coffeeOrder);
    }



    private long createOrder(){
        NewOrderRequest request = NewOrderRequest.builder()
                .customer("frog")
                .items(Arrays.asList("latte", "capuccino"))
                .build();
        RequestEntity<NewOrderRequest> requestEntity = RequestEntity.post(UriComponentsBuilder.fromUriString("http://localhost:8080/order/").build().toUri())
                .body(request);

        ResponseEntity<CoffeeOrder> resp = restTemplate.exchange(requestEntity, CoffeeOrder.class);
        log.info("Order Request Status Code : {}", resp.getStatusCode());
        Long id = resp.getBody().getId();
        log.info("Order ID : {}", id);
        return id;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory requestFactory(){
        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(20);

        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .evictIdleConnections(30, TimeUnit.SECONDS)
                .disableAutomaticRetries()
                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
                .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(5000))
                .requestFactory(this::requestFactory)
                .build();
    }


}
