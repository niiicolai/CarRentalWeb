package carrental.carrentalweb.controller;

import carrental.carrentalweb.repository.DamageReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Mads
@Controller
public class DamageReportController {

    private DamageReportRepository dmgRepo;

    public DamageReportController(DamageReportRepository dmgRepo) {
        this.dmgRepo = dmgRepo;
    }

    @GetMapping("/damage-report/show/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("damage-report", dmgRepo.get("booking_id", id));
        return "damage-report/show";
    }

    @GetMapping("/damage-report/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("damage-report", dmgRepo.get("booking_id", id));
        return "damage-report/edit";
    }

    @PostMapping("/damage-report/edit/{id}")
    public String update(@PathVariable("id") Long id) {
        dmgRepo.update(dmgRepo.get("booking_id", id));
        return "redirect:/damage-report/show/" + id;
    }
}
