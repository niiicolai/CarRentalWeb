package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

import carrental.carrentalweb.enums.CreditRatingState;

/*
 * Written by Nicolai Berg Andersen.
 */

public class CreditRating {
    
    private long bookingId;
    private CreditRatingState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public CreditRating(CreditRatingState state, long bookingId) {
        this.state = state;
        this.bookingId = bookingId;
    }

    public CreditRating(CreditRatingState state, long bookingId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.state = state;
        this.bookingId = bookingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setState(CreditRatingState state) {
        this.state = state;
    }

    public CreditRatingState getState() {
        return state;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public long getBookingId() {
        return bookingId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
