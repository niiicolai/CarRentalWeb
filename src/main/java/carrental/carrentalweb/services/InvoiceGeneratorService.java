package carrental.carrentalweb.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.records.InvoiceRecord;

@Service
public class InvoiceGeneratorService {
    
    public InvoiceRecord generateFromSubscription(Booking booking, Subscription subscription) {
        long days = (long) subscription.getDays();
        LocalDateTime due = LocalDateTime.now().plusDays(days);
        long bookingId = booking.getId();

        /* Build invoice */
        Invoice invoice = new Invoice();
        invoice.setBookingId(bookingId);
        invoice.setDueDate(due);

        /* Add invoice specification */
        InvoiceSpecification specification = new InvoiceSpecification();
        specification.setInvoiceId(bookingId);
        specification.setDescription(subscription.getName());
        specification.setPrice(subscription.getPrice());
        
        return new InvoiceRecord(invoice, specification);
    }

    public InvoiceRecord generateFromDamageReport(DamageReport report, List<DamageSpecification> specifications) {
        return new InvoiceRecord(null, null);
    }
}
