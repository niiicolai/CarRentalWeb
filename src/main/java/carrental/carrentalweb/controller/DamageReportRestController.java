package carrental.carrentalweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.repository.DamageReportRepository;
import carrental.carrentalweb.repository.DamageSpecificationRepository;
import carrental.carrentalweb.utilities.DatabaseResponse;
import groovyjarjarantlr4.v4.parse.ANTLRParser.elementEntry_return;

@RestController
public class DamageReportRestController {

    private final DamageReportRepository dmgRepo;
    private final DamageSpecificationRepository dmgSpecRepo;

    public DamageReportRestController(DamageReportRepository dmgRepo, DamageSpecificationRepository dmgSpecRepo) {
        this.dmgRepo = dmgRepo;
        this.dmgSpecRepo = dmgSpecRepo;
    }
    
    @PostMapping("/damage-report/new/{booking_id}")
    public Map<String, String> create(@RequestBody List<String> descriptions, @PathVariable("booking_id") Long bookingId) {
        DatabaseResponse response = dmgRepo.create(descriptions, bookingId);

        HashMap<String, String> results = new HashMap<>();
        if (response.isSuccessful()) {
            results.put("response", "Skaderapport oprettet!");
            results.put("state", "success");
        } else {
            results.put("response", response.getDatabaseError().getHumanMessage());
            results.put("state", response.getState().toString());
        }

        return results;
    }
}
