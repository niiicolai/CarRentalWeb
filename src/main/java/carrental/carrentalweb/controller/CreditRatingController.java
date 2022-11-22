package carrental.carrentalweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carrental.carrentalweb.repository.CreditRatingRepository;

/*
 * Written by Nicolai Berg Andersen.
 */

@Controller
public class CreditRatingController {

    private CreditRatingRepository creditRatingRepository;

    public CreditRatingController(CreditRatingRepository creditRatingRepository) {
        this.creditRatingRepository = creditRatingRepository;
    }
    
    @GetMapping("/credit/rating/{id}")
    public String show(Model model, @RequestParam String id) {
        model.addAttribute("creditRating", creditRatingRepository.find("id", id));
		return "credit-rating/show";
    }
}
