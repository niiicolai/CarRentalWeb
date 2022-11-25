package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

/*
 * Written by Nicolai Berg Andersen.
 */


public class Invoice {
    private long bookingId;
    private LocalDateTime due;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Invoice() {
    }

    public Invoice(long bookingId, LocalDateTime due, LocalDateTime paidAt) {
        this.bookingId = bookingId;
        this.due = due;
        this.paidAt = paidAt;
    }

    public Invoice(long bookingId, LocalDateTime due, LocalDateTime paidAt, 
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookingId = bookingId;
        this.due = due;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public void setDue(LocalDateTime due) {
        this.due = due;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
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

    public LocalDateTime getDue() {
        return due;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
