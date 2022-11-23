package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// Mads
@Controller
public class SubscriptionController {

    @Autowired
    SubscriptionRepository subRepo;

    @GetMapping("/subscriptions")
    public String index(Model model) {
        model.addAttribute("subscriptions", subRepo.getAll());
        return "subscriptions/index";
    }

    @GetMapping("/subscriptions/new")
    public String instantiate() {
        return "subscriptions/new";
    }

    @PostMapping("/subscriptions/new")
    public String create(@ModelAttribute("subscription") Subscription subscription) {
        subRepo.create(subscription);
        return "redirect:/subscriptions";
    }
}
