package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

import carrental.carrentalweb.enums.CreditRatingState;

/*
 * Written by Nicolai Berg Andersen.
 */

public class CreditRating {
    
    private long userId;
    private CreditRatingState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CreditRating() {
    }
    
    public CreditRating(CreditRatingState state, long userId) {
        this.state = state;
        this.userId = userId;
    }

    public CreditRating(CreditRatingState state, long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.state = state;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setState(CreditRatingState state) {
        this.state = state;
    }

    public CreditRatingState getState() {
        return state;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
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

    public boolean isApproved() {
        return this.state == CreditRatingState.Approve;
    }
}
