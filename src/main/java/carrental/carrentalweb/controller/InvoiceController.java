package carrental.carrentalweb.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.InvoiceRepository;
import carrental.carrentalweb.repository.InvoiceSpecificationRepository;
import carrental.carrentalweb.services.InvoicePDFService;

/*
 * Written by Nicolai Berg Andersen
 */
@Controller
public class InvoiceController {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceSpecificationRepository invoiceSpecificationRepository;

    @Autowired
    InvoicePDFService invoicePDFService;
    
    @GetMapping("/invoice/{id}")
    public ResponseEntity<byte[]> show(@PathVariable Long id, @AuthenticationPrincipal User user) throws IOException {
        Invoice invoice = invoiceRepository.find("id", id);
        List<InvoiceSpecification> specifications = invoiceSpecificationRepository.findCollection("invoice_id", id);
        System.out.println(specifications.size());
        // Convert to array
        InvoiceSpecification[] specificationsArr = new InvoiceSpecification[specifications.size()];
        for (int i = 0; i < specifications.size(); i++)
            specificationsArr[i] = specifications.get(i);
        
        File file = invoicePDFService.execute(invoice, specificationsArr);

        // https://allaboutspringframework.com/java-spring-download-file-from-controller/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // (3) Content-Type: application/octet-stream
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(file.getName()).build().toString()); // (4) Content-Disposition: attachment; filename="demo-file.txt"
        return ResponseEntity.ok().headers(httpHeaders).body(Files.readAllBytes(file.toPath()));
    }
}
