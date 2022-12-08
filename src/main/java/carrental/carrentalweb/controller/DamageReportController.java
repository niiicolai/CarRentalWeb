package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.repository.DamageReportRepository;
import carrental.carrentalweb.repository.DamageSpecificationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/*
 * Written by Mads Kristian Pedersen
 */
@Controller
public class DamageReportController {

    private final DamageReportRepository dmgRepo;
    private final DamageSpecificationRepository dmgSpecRepo;

    public DamageReportController(DamageReportRepository dmgRepo, DamageSpecificationRepository dmgSpecRepo) {
        this.dmgRepo = dmgRepo;
        this.dmgSpecRepo = dmgSpecRepo;
    }

    @GetMapping("/damage-report/show/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("damageReport", dmgRepo.get("booking_id", id));
        model.addAttribute("damageSpecs", dmgSpecRepo.getAllById(id));
        model.addAttribute("bookingId", id);
        return "damage-report/show";
    }

    @GetMapping("/damage-report/new/{id}")
    public String instantiate(Model model, @PathVariable("id") long id) {
        model.addAttribute("damageReport", new DamageReport());
        model.addAttribute("damageSpecs", dmgSpecRepo.getAll());
        model.addAttribute("bookingId", id);
        return "damage-report/new";
    }

    

    @GetMapping("/damage-report/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("bookingId", id);
        model.addAttribute("damageSpecs", dmgSpecRepo.getAll());
        return "damage-report/edit";
    }

    @PatchMapping("/damage-report/edit/{id}")
    public String update(@ModelAttribute("damageReport") DamageReport dmgReport,
                         @ModelAttribute("damageSpecs") List<DamageSpecification> dmgSpecs,
                         @PathVariable("id") long id) {
        dmgRepo.update(dmgReport, dmgSpecs);
        return "redirect:/damage-report/show/" + id;
    }
}
