package carrental.carrentalweb.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.records.InvoiceRecord;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.DamageReportRepository;
import carrental.carrentalweb.repository.DamageSpecificationRepository;
import carrental.carrentalweb.repository.InvoiceRepository;
import carrental.carrentalweb.repository.InvoiceSpecificationRepository;
import carrental.carrentalweb.repository.UserRepository;

/*
 * Written by Nicolai Berg Andersen
 */
@Service
public class DamageReportInvoiceService {

    private static final int dueDays = 30;

    @Autowired
    private InvoiceMailerService invoiceMailerService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceSpecificationRepository invoiceSpecificationRepository;

    @Autowired
    private DamageReportRepository damageReportRepository;

    @Autowired
    private DamageSpecificationRepository damageSpecificationRepository;

    private InvoiceRecord generateInvoiceRecord(DamageReport report, List<DamageSpecification> damageSpecifications) {
        long bookingId = report.getBookingId();
        LocalDateTime due = LocalDateTime.now().plusDays(dueDays);

        /* Build invoice */
        Invoice invoice = new Invoice();
        invoice.setBookingId(bookingId);
        invoice.setDueDate(due);
        
        /* Add invoice specification */
        InvoiceSpecification[] specifications = new InvoiceSpecification[damageSpecifications.size()];
        for (int i = 0; i < damageSpecifications.size(); i++) {
            /* Add invoice specification */
            specifications[i] = new InvoiceSpecification();
            specifications[i].setInvoiceId(bookingId);
            specifications[i].setDescription(damageSpecifications.get(i).getDescription());
            specifications[i].setPrice(damageSpecifications.get(i).getPrice());
        }
        
        return new InvoiceRecord(invoice, specifications);
    }

    private void saveInvoiceInformation(InvoiceRecord invoiceRecord) {
        invoiceRepository.insert(invoiceRecord.invoice());

        for (InvoiceSpecification invoiceSpecification : invoiceRecord.specifications()) {
            invoiceSpecificationRepository.insert(invoiceSpecification);
        }
    }

    private User getBookingOwner(long bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        return userRepository.find("id", booking.getUserId());
    }

    private void emailOwner(User user, InvoiceRecord invoiceRecord) {
        invoiceMailerService.send(
            user, 
            invoiceRecord.invoice(), 
            invoiceRecord.specifications());
    }

    public void execute(long bookingId) {
        DamageReport report = damageReportRepository.get("booking_id", bookingId);
        List<DamageSpecification> specifications = damageSpecificationRepository.getAllById(bookingId);

        InvoiceRecord invoiceRecord = generateInvoiceRecord(report, specifications);
        saveInvoiceInformation(invoiceRecord);

        User user = getBookingOwner(bookingId);
        emailOwner(user, invoiceRecord);
    }
}
