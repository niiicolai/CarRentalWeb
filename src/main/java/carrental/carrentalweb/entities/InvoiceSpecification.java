package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

/*
 * Written by Nicolai Berg Andersen.
 */

public class InvoiceSpecification {
    private long id;
    private long invoiceId;
    private String description;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public InvoiceSpecification() {
    }

    public InvoiceSpecification(long id, long invoiceId, String description, double price) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.description = description;
        this.price = price;
    }

    public InvoiceSpecification(long id, long invoiceId, String description, 
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public double getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
