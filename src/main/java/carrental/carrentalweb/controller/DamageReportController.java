package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.repository.DamageReportRepository;
import carrental.carrentalweb.repository.DamageSpecificationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Mads
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

    @GetMapping("/damage-report/new")
    public String instantiate(Model model) {
        model.addAttribute("damageReport", new DamageReport());
        model.addAttribute("damageSpecs", dmgSpecRepo.getAll());
        return "damage-report/new";
    }

    @PostMapping("/damage-report/new")
    public String create(@ModelAttribute("damageReport") DamageReport dmgReport,
    @ModelAttribute("damageSpecs") List<DamageSpecification> dmgSpecs) {
        dmgRepo.create(dmgReport, dmgSpecs);
        return "redirect:/damage-report/show/" + dmgReport.getBookingId();
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
