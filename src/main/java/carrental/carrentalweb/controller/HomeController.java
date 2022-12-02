package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/")
    public String index(@AuthenticationPrincipal User authUser) {
        return "home/index";
    }
}
