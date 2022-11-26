package carrental.carrentalweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.CreditRatingRepository;
import carrental.carrentalweb.services.CreditRatingService;

/*
 * Written by Nicolai Berg Andersen.
 */

@Controller
public class CreditRatingController {

    CreditRatingRepository creditRatingRepository;
    CreditRatingService creditRatingService;

    public CreditRatingController (CreditRatingRepository repository, CreditRatingService service) {
        creditRatingRepository = repository;
        creditRatingService = service;
    }
    
    @GetMapping("/credit/rating")
    public String show(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("creditRating", creditRatingRepository.find("user_id", user.getId()));
		return "credit_rating/show";
    }

    @PostMapping("/credit/rating")
    public String create(@AuthenticationPrincipal User user) {
        creditRatingService.check(user);
        return "redirect:/user";
    }
}
