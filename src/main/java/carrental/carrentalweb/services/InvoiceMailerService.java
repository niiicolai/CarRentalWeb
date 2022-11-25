package carrental.carrentalweb.services;

import java.io.File;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.User;

@Service
public class InvoiceMailerService {

    @Autowired
    MailerService mailerService;

    @Autowired
    InvoicePDFService invoicePDFService;

    private static final String subject = "Din faktura (#%d)";
    private static final String description = "Tak for din køb.";

    public void send(User user, Invoice invoice, InvoiceSpecification[] specifications) {
        File file = invoicePDFService.execute(invoice, specifications);
        String mailTo = user.getEmail();

        try {
            mailerService.sendMessageWithAttachment(mailTo, String.format(subject, invoice.getBookingId()), description, file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}