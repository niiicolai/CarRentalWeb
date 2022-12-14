package carrental.carrentalweb.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.records.InvoiceRecord;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.InvoiceRepository;
import carrental.carrentalweb.repository.InvoiceSpecificationRepository;
import carrental.carrentalweb.repository.SubscriptionRepository;
import carrental.carrentalweb.repository.UserRepository;

/*
 * Written by Nicolai Berg Andersen
 */
@Service
public class BookingInvoiceService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceSpecificationRepository invoiceSpecificationRepository;

    @Autowired
    private InvoiceMailerService invoiceMailerService;

    private InvoiceRecord generateInvoiceRecord(Booking booking, Subscription subscription) {
        long bookingId = booking.getId();
        long days = (long) subscription.getDays();
        LocalDateTime due = LocalDateTime.now().plusDays(days);

        /* Build invoice */
        Invoice invoice = new Invoice();
        invoice.setBookingId(bookingId);
        invoice.setDueDate(due);
        invoiceRepository.insert(invoice);

        Invoice last = invoiceRepository.last();

        /* Add invoice specification */
        InvoiceSpecification[] specifications = new InvoiceSpecification[1];
        specifications[0] = new InvoiceSpecification();
        specifications[0].setInvoiceId(last.getId());
        specifications[0].setDescription(subscription.getName());
        specifications[0].setPrice(subscription.getPrice());

        invoiceSpecificationRepository.insert(specifications[0]);
        
        return new InvoiceRecord(invoice, specifications);
    }

    private User getBookingOwner(long userId) {
        return userRepository.find("id", userId);
    }

    private void emailOwner(User user, InvoiceRecord invoiceRecord) {
        // Catch exception to counter SMTP lockout.
        try {
            invoiceMailerService.send(
                user, 
                invoiceRecord.invoice(), 
                invoiceRecord.specifications());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(long bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        Subscription subscription = subscriptionRepository.get("name", booking.getSubscriptionName());

        InvoiceRecord invoiceRecord = generateInvoiceRecord(booking, subscription);
        User user = getBookingOwner(booking.getUserId());

        emailOwner(user, invoiceRecord);
    }
}
