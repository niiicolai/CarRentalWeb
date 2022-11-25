package carrental.carrentalweb.controller;

import carrental.carrentalweb.repository.UserRepository;
import carrental.carrentalweb.services.InvoiceMailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    InvoiceMailerService invoiceMailerService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "home/index";
    }
}
