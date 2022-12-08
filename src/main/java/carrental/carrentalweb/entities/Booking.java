package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

/*
 * Written by Thomas S. Andersen
 */
public class Booking {
    private long id;
    private long userId;
    private long vehicleNumber;
    private long pickupPointId;

    private String subscriptionName;
    private double kilometerDriven;
    
    private LocalDateTime returnedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Booking(long id, long userId, long vehicleNumber, String subscriptionName, long pickupPointId, LocalDateTime deliveredAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime returnedAt, double kilometerDriven) {
        this.id = id;
        this.userId = userId;
        this.vehicleNumber = vehicleNumber;
        this.subscriptionName = subscriptionName;
        this.pickupPointId = pickupPointId;
        this.deliveredAt = deliveredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.returnedAt = returnedAt;
        this.kilometerDriven = kilometerDriven;
    }

    public Booking(long userId, long vehicleNumber, long pickupPointId, String subscriptionName, LocalDateTime deliveredAt) {
        this.userId = userId;
        this.vehicleNumber = vehicleNumber;
        this.pickupPointId = pickupPointId;
        this.subscriptionName = subscriptionName;
        this.deliveredAt = deliveredAt;
    }

    public Booking() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getVehicleNumber() {
        return vehicleNumber;
    }

    public long getPickupPointId() {
        return pickupPointId;
    }

    public double getKilometerDriven() {
        return kilometerDriven;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVehicleNumber(long vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public void setPickupPointId(long pickupPointId) {
        this.pickupPointId = pickupPointId;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public void setKilometerDriven(double kilometerDriven) {
        this.kilometerDriven = kilometerDriven;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }
}
