package carrental.carrentalweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.CreditRatingRepository;
import carrental.carrentalweb.repository.UserRepository;
import carrental.carrentalweb.services.TimeOfDayService;
import carrental.carrentalweb.utilities.DatabaseResponse;

/*
 * Written by Nicolai Berg Andersen.
 */

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CreditRatingRepository creditRatingRepository;

    @Autowired
    TimeOfDayService timeOfDayService;

    @GetMapping("/user")
    public String show(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", userRepository.find("id", user.getId()));
        model.addAttribute("creditRating", creditRatingRepository.find("user_id", user.getId()));
        model.addAttribute("timeOfDayImage", timeOfDayService.getImage());
        model.addAttribute("timeOfDayGreeting", timeOfDayService.getGreeting());
        return "user/show";
    }

    @GetMapping("/user/edit")
    public String edit(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", userRepository.find("id", user.getId()));
		return "user/edit";
    }

    @GetMapping("/login")
    public String login() {
		return "user/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
		return "user/signup";
    }

    @PostMapping("/signup")
    public String create(User user, RedirectAttributes redirectAttributes) {
        DatabaseResponse response = userRepository.insert(user);
        if (response.isSuccessful()) {
            redirectAttributes.addAttribute("response", "Bruger oprettet!");
            redirectAttributes.addAttribute("state", response.getState());
            return "redirect:/login";        
        } else {
            redirectAttributes.addAttribute("response", response.getDatabaseError().getHumanMessage());
            redirectAttributes.addAttribute("state", response.getState());
            return "redirect:/signup";
        }            
    }

    @PatchMapping("/user")
    public String update(User user, @AuthenticationPrincipal User authUser, RedirectAttributes redirectAttributes) {
        /* Only allow the authenticated user to be modified. */
        user.setId(authUser.getId());
        DatabaseResponse response = userRepository.update(user);
        if (response.isSuccessful()) {
            redirectAttributes.addAttribute("response", "Bruger opdateret!");
            redirectAttributes.addAttribute("state", response.getState());
            return "redirect:/user";
        } else {
            redirectAttributes.addAttribute("response", response.getDatabaseError().getHumanMessage());
            redirectAttributes.addAttribute("state", response.getState());
            return "redirect:/user/edit";
        }          
    }

    @PatchMapping("/user/password")
    public String updatePassword(User user, @AuthenticationPrincipal User authUser, RedirectAttributes redirectAttributes) {
        /* Only allow the authenticated user to be modified. */
        user.setId(authUser.getId());
        DatabaseResponse response = userRepository.updatePassword(user);
        if (response.isSuccessful()) {
            redirectAttributes.addAttribute("response", "Adgangskode opdateret!");
            redirectAttributes.addAttribute("state", response.getState());
            return "redirect:/user";
        } else {
            redirectAttributes.addAttribute("response", response.getDatabaseError().getHumanMessage());
            redirectAttributes.addAttribute("state", response.getState());
            return "redirect:/user/edit";
        }
    }

    @DeleteMapping("/user")
    public String disable(User user, @AuthenticationPrincipal User authUser, RedirectAttributes redirectAttributes) {
        /* Only allow the authenticated user to be modified. */
        user.setId(authUser.getId());
        DatabaseResponse response = userRepository.disable(user);
        if (response.isSuccessful()) {
            User.logout();
            redirectAttributes.addAttribute("response", "Bruger deaktiveret! Kontakt support hvis dette var en fejl.");
            redirectAttributes.addAttribute("state", "warning");
            return "redirect:/";
        } else {
            redirectAttributes.addAttribute("response", response.getDatabaseError().getHumanMessage());
            redirectAttributes.addAttribute("state", response.getState());
            return "redirect:/user/edit";
        }
            
    }
}
