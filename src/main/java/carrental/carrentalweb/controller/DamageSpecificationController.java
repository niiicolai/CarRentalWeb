package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.repository.DamageSpecificationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Mads
@Controller
public class DamageSpecificationController {

    private DamageSpecificationRepository dmgSpecRepo;

    public DamageSpecificationController(DamageSpecificationRepository dmgSpecRepo) {
        this.dmgSpecRepo = dmgSpecRepo;
    }

    @GetMapping("/damage-specifications")
    public String index(Model model) {
        model.addAttribute("damage-specifications", dmgSpecRepo.getAll());
        return "damage-specification/index";
    }

    @GetMapping("/damage-specification/new")
    public String instantiate() {
        return "damage-specification/new";
    }

    @PostMapping("/damage-specification/new")
    public String create(@ModelAttribute("damage-specification") DamageSpecification dmgSpec) {
        dmgSpecRepo.create(dmgSpec);
        return "redirect:/damage-specifications";
    }

    @GetMapping("/damage-specification/edit/{desc}")
    public String edit(@PathVariable("desc") String description, Model model) {
        model.addAttribute("desc", description);
        return "damage-specification/edit";
    }

    @PostMapping("/damage-specification/edit/{desc}")
    public String update(@PathVariable("desc") String description) {
        dmgSpecRepo.update(dmgSpecRepo.getByDescription(description));
        return "redirect:/damage-specifications";
    }
}
