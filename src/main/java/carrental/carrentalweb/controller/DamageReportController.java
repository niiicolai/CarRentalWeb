package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.repository.DamageReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Mads
@Controller
public class DamageReportController {

    private final DamageReportRepository dmgRepo;

    public DamageReportController(DamageReportRepository dmgRepo) {
        this.dmgRepo = dmgRepo;
    }

    @GetMapping("/damage-report/show/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("damage-report", dmgRepo.get("booking_id", id));
        return "damage-report/show";
    }

    @GetMapping("/damage-report/new")
    public String instantiate() {
        return "damage-report/new";
    }

    @PostMapping("/damage-report/new")
    public String create(@ModelAttribute("damage-report") DamageReport dmgReport) {
        dmgRepo.create(dmgReport);
        return "redirect:/damage-report/show/" + dmgReport.getBookingId();
    }
}
