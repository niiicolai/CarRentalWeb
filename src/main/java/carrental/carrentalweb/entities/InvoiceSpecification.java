package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

/*
 * Written by Nicolai Berg Andersen.
 */

public class InvoiceSpecification {
    private long bookingId;
    private String description;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public InvoiceSpecification() {
    }

    public InvoiceSpecification(long bookingId, String description, double price) {
        this.bookingId = bookingId;
        this.description = description;
        this.price = price;
    }

    public InvoiceSpecification(long bookingId, String description) {
        this.bookingId = bookingId;
        this.description = description;
    }

    public InvoiceSpecification(long bookingId, String description, 
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookingId = bookingId;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public long getBookingId() {
        return bookingId;
    }

    public double getPrice() {
        return price;
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
