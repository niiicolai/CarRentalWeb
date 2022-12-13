package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.repository.DamageSpecificationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/*
 * Written by Mads Kristian Pedersen
 */
@Controller
public class DamageSpecificationController {

    private final DamageSpecificationRepository dmgSpecRepo;

    public DamageSpecificationController(DamageSpecificationRepository dmgSpecRepo) {
        this.dmgSpecRepo = dmgSpecRepo;
    }

    @GetMapping("/damage-specifications")
    public String index(Model model) {
        model.addAttribute("damageSpecs", dmgSpecRepo.getAll());
        return "damage-specification/index";
    }

    @GetMapping("/damage-specification/new")
    public String instantiate(Model model) {
        model.addAttribute("damageSpec", new DamageSpecification());
        return "damage-specification/new";
    }

    @PostMapping("/damage-specification/new")
    public String create(@ModelAttribute("damageSpec") DamageSpecification dmgSpec) {
        dmgSpecRepo.create(dmgSpec);
        return "redirect:/damage-specifications";
    }

    @GetMapping("/damage-specification/edit/{desc}")
    public String edit(@PathVariable("desc") String description, Model model) {
        model.addAttribute("damageSpec", dmgSpecRepo.get("description", description));
        return "damage-specification/edit";
    }

    @PatchMapping("/damage-specification/edit")
    public String update(DamageSpecification damageSpecification) {
        dmgSpecRepo.update(damageSpecification);
        return "redirect:/damage-specifications";
    }
}
