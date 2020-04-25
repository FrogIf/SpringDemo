package frog.learn.spring.session;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionTestController {

    @RequestMapping("/")
    public String printSession(HttpSession session,String name){
        String storeName = (String) session.getAttribute("name");
        if(storeName == null){
            session.setAttribute("name", name);
            storeName = name;
        }
        return "hello" + storeName;
    }

}
