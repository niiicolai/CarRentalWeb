package carrental.carrentalweb.builder;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.Invoice;

public class InvoiceBuilder {
    
    private Invoice invoice;

    public InvoiceBuilder () {
        this.invoice = new Invoice();
    }

    public InvoiceBuilder id(long id) {
        invoice.setId(id);
        return this;
    }

    public InvoiceBuilder bookingId(long bookingId) {
        invoice.setBookingId(bookingId);
        return this;
    }

    public InvoiceBuilder due(LocalDateTime due) {
        invoice.setDueDate(due);
        return this;
    }

    public InvoiceBuilder paidAt(LocalDateTime paidAt) {
        invoice.setPaidAt(paidAt);
        return this;
    }

    public InvoiceBuilder createdAt(LocalDateTime createdAt) {
        invoice.setCreatedAt(createdAt);
        return this;
    }

    public InvoiceBuilder updatedAt(LocalDateTime updatedAt) {
        invoice.setUpdatedAt(updatedAt);
        return this;
    }

    public Invoice build() {
        return invoice;
    }
}
