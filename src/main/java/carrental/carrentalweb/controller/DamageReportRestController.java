package carrental.carrentalweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import carrental.carrentalweb.repository.DamageReportRepository;
import carrental.carrentalweb.services.DamageReportInvoiceService;
import carrental.carrentalweb.utilities.DatabaseResponse;

/*
 * Written by Nicolai Berg Andersen
 */

@RestController
public class DamageReportRestController {

    @Autowired
    DamageReportRepository damageReportRepository;

    @Autowired
    DamageReportInvoiceService damageReportInvoiceService;
    
    @PostMapping("/damage-report/new/{booking_id}")
    public Map<String, String> create(@RequestBody List<String> descriptions, @PathVariable("booking_id") Long bookingId) {
        DatabaseResponse response = damageReportRepository.create(descriptions, bookingId);

        HashMap<String, String> results = new HashMap<>();
        if (response.isSuccessful()) {
            damageReportInvoiceService.execute(bookingId);
            results.put("response", "Skaderapport oprettet!");
            results.put("state", "success");
        } else {
            results.put("response", response.getDatabaseError().getHumanMessage());
            results.put("state", response.getState().toString());
        }

        return results;
    }
}
