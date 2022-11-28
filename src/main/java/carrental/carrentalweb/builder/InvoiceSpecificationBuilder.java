package carrental.carrentalweb.builder;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.InvoiceSpecification;

public class InvoiceSpecificationBuilder {
    
    private InvoiceSpecification invoiceSpecification;

    public InvoiceSpecificationBuilder () {
        this.invoiceSpecification = new InvoiceSpecification();
    }

    public InvoiceSpecificationBuilder id(long id) {
        invoiceSpecification.setId(id);
        return this;
    }

    public InvoiceSpecificationBuilder invoiceId(long invoiceId) {
        invoiceSpecification.setInvoiceId(invoiceId);
        return this;
    }

    public InvoiceSpecificationBuilder description(String description) {
        invoiceSpecification.setDescription(description);
        return this;
    }

    public InvoiceSpecificationBuilder price(Double price) {
        invoiceSpecification.setPrice(price);
        return this;
    }

    public InvoiceSpecificationBuilder createdAt(LocalDateTime createdAt) {
        invoiceSpecification.setCreatedAt(createdAt);
        return this;
    }

    public InvoiceSpecificationBuilder updatedAt(LocalDateTime updatedAt) {
        invoiceSpecification.setUpdatedAt(updatedAt);
        return this;
    }

    public InvoiceSpecification build() {
        return invoiceSpecification;
    }
}
