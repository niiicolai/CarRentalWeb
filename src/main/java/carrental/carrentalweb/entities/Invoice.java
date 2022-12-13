package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

/*
 * Written by Nicolai Berg Andersen.
 */

public class Invoice {
    private long id;
    private long bookingId;
    private LocalDateTime dueDate;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Invoice() {
    }

    public Invoice(long id, long bookingId, LocalDateTime dueDate, LocalDateTime paidAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.dueDate = dueDate;
        this.paidAt = paidAt;
    }

    public Invoice(long id, long bookingId, LocalDateTime dueDate, LocalDateTime paidAt, 
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.dueDate = dueDate;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
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

    public long getId() {
        return id;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
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
