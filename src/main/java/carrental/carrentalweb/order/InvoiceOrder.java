package carrental.carrentalweb.order;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.repository.InvoiceRepository;
import carrental.carrentalweb.repository.InvoiceSpecificationRepository;
import carrental.carrentalweb.repository.SubscriptionRepository;

/*
 * Written by Nicolai Berg Andersen.
 */

public class InvoiceOrder {
    private Booking booking;
    private int noMonths;

    public InvoiceOrder(Booking booking, int noMonths) {
        this.booking = booking;
        this.noMonths = noMonths;
    }

    public Invoice execute() {
        long bookingId = booking.getId();
        String subscriptionName = booking.getSubscriptionName();
        LocalDateTime due = LocalDateTime.now().plusMonths(noMonths);

        /* Build invoice */
        Invoice invoice = new Invoice();
        invoice.setBookingId(bookingId);
        invoice.setDue(due);

        /* Save invoice */
        InvoiceRepository repository = new InvoiceRepository();        
        repository.insert(invoice);

        /* Get subscription */
        SubscriptionRepository subscriptionRepository = new SubscriptionRepository();
        Subscription subscription = subscriptionRepository.find(subscriptionName);

        /* Add invoice specification */
        InvoiceSpecificationRepository invoiceSpecificationRepository = new InvoiceSpecificationRepository();
        InvoiceSpecification specification = new InvoiceSpecification();
        specification.setBookingId(bookingId);
        specification.setDescription(subscription.getName());
        specification.setPrice(subscription.getPrice());
        invoiceSpecificationRepository.insert(specification);

        /* TODO: SEND EMAIL */
        
        return invoice;
    }
}
