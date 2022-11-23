package carrental.carrentalweb.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.UserRepository;

/*
 * Written by Nicolai Berg Andersen.
 */

@Controller
public class UserController {
    
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String show(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
		return "user/show";
    }

    @GetMapping("/user/edit")
    public String edit(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
		return "user/edit";
    }

    @GetMapping("/user/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
		return "user/signup";
    }

    @PostMapping("/user")
    public String create(User user) {
        if (userRepository.insert(user))
            return "redirect:user";
        else
            return "redirect:/user/signup";
    }

    @PatchMapping("/user")
    public String update(User user, @AuthenticationPrincipal User authUser) {
        /* Only allow the authenticated user to be modified. */
        user.setId(authUser.getId());
        if (userRepository.update(user))
            return "redirect:user";
        else
            return "redirect:/user/edit";
    }

    @PatchMapping("/user/password")
    public String updatePassword(User user, @AuthenticationPrincipal User authUser) {
        /* Only allow the authenticated user to be modified. */
        user.setId(authUser.getId());
        if (userRepository.updatePassword(user))
            return "redirect:user";
        else
            return "redirect:/user/edit";
    }

    @DeleteMapping("/user")
    public String delete(User user, @AuthenticationPrincipal User authUser) {
        /* Only allow the authenticated user to be modified. */
        user.setId(authUser.getId());
        if (userRepository.delete(user))
            return "redirect:/";
        else
            return "redirect:/user/edit";
    }
}
