package carrental.carrentalweb.controller;

import carrental.carrentalweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/css_framework")
    public String css_framework() {
        return "home/css_framework";
    }
}
