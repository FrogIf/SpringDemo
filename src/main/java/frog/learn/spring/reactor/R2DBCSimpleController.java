package frog.learn.spring.reactor;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.reactor.repository.R2DBCCoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("r2dbc")
@Slf4j
public class R2DBCSimpleController {

    @Autowired
    private DatabaseClient client;

    /**
     * r2dbc jdbc demo
     * @return
     */
    @RequestMapping("demo")
    public String demo(){
        client.execute()
                .sql("select * from t_coffee")
                .as(Coffee.class)
                .fetch()
                .first()
                .subscribe(c -> log.info("Fetch execute() {}", c));

        client.select()
                .from("t_coffee")
                .orderBy(Sort.by(Sort.Direction.DESC, "id"))
                .page(PageRequest.of(0, 3))
                .as(Coffee.class)
                .fetch()
                .all()
                .subscribe(c -> log.info("Fetch select() {}", c));

        log.info("starting.");
        return "success";
    }
    
    @Autowired
    private R2DBCCoffeeRepository r2DBCCoffeeRepository;

    /**
     * r2dbc repository demo
     */
    @RequestMapping("query")
    public String query(String name){
        r2DBCCoffeeRepository.findAllById(Flux.just(1L, 2L))
                .map(c -> c.getName() + "-" + c.getPrice().toString())
                .subscribe(c -> log.info("Find {}", c));

        r2DBCCoffeeRepository.findByName(name).subscribe(c -> log.info("Find {}", c));
        return "success";
    }

}
