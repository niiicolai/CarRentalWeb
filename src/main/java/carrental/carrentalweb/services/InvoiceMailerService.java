package carrental.carrentalweb.services;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.InvoiceSpecificationRepository;

@Service
public class InvoiceMailerService {

    @Autowired
    MailerService mailerService;

    @Autowired
    InvoicePDFService invoicePDFService;

    @Autowired
    InvoiceSpecificationRepository invoiceSpecificationRepository;

    private static final String subject = "Din faktura (#%d)";
    private static final String description = "Tak for din køb.";

    public void send(User user, Invoice invoice, InvoiceSpecification[] specifications) {
        
        //InvoiceSpecification[] specifications = invoiceSpecificationRepository.findCollection("booking_id", invoice.getBookingId());
        File file = invoicePDFService.execute(invoice, specifications);
        String mailTo = user.getEmail();

        try {
            mailerService.sendMessageWithAttachment(mailTo, String.format(subject, invoice.getBookingId()), description, file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
