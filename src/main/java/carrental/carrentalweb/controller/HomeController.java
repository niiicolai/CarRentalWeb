package carrental.carrentalweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.services.InvoicePDFService;

@Controller
public class HomeController {

    @Autowired
    InvoicePDFService invoicePDFService;
    
    @GetMapping("/")
    public String index() {
		return "home/index";
    }
}
