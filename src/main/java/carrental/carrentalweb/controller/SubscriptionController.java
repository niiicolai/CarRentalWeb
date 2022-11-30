package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Mads
@Controller
public class SubscriptionController {

    private final SubscriptionRepository subRepo;

    public SubscriptionController(SubscriptionRepository subRepo) {
        this.subRepo = subRepo;
    }

    @GetMapping("/subscriptions")
    public String index(Model model) {
        model.addAttribute("subscriptions", subRepo.getAll());
        return "subscriptions/index";
    }

    @GetMapping("/subscriptions/new")
    public String instantiate(Model model) {
        model.addAttribute("subscription", new Subscription());
        return "subscriptions/new";
    }

    @PostMapping("/subscriptions/new")
    public String create(@ModelAttribute("subscription") Subscription subscription) {
        subRepo.create(subscription);
        return "redirect:/subscriptions";
    }

    @GetMapping("/subscriptions/edit/{name}")
	public String update(Model model, @PathVariable("name") String name) {
		model.addAttribute("subscription", subRepo.get("name", name));
		return "subscriptions/edit";
	}

	@PatchMapping("/subscriptions/edit")
	public String edit(Subscription subscription) {
		subRepo.update(subscription);
		return "redirect:/subscriptions";
	}
}
