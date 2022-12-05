package carrental.carrentalweb.builder;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.Booking;

public class BookingBuilder {
    
    private Booking booking;

    public BookingBuilder () {
        this.booking = new Booking();
    }

    public BookingBuilder id(long id) {
        booking.setId(id);
        return this;
    }

    public BookingBuilder deliveredAt(LocalDateTime deliveredAt) {
        booking.setDeliveredAt(deliveredAt);
        return this;
    }

    public BookingBuilder returnedAt(LocalDateTime returnedAt) {
        booking.setReturnedAt(returnedAt);
        return this;
    }

    public BookingBuilder kilometerDriven(double kilometerDriven) {
        booking.setKilometerDriven(kilometerDriven);
        return this;
    }

    public BookingBuilder pickupPointId(long pickupPointId) {
        booking.setPickupPointId(pickupPointId);
        return this;
    }

    public BookingBuilder subscriptionName(String subscriptionName) {
        booking.setSubscriptionName(subscriptionName);
        return this;
    }

    public BookingBuilder userId(long userId) {
        booking.setUserId(userId);
        return this;
    }

    public BookingBuilder vehicleNumber(long vehicleNumber) {
        booking.setVehicleNumber(vehicleNumber);
        return this;
    }

    public BookingBuilder createdAt(LocalDateTime createdAt) {
        booking.setCreatedAt(createdAt);
        return this;
    }

    public BookingBuilder updatedAt(LocalDateTime updatedAt) {
        booking.setUpdatedAt(updatedAt);
        return this;
    }

    public Booking build() {
        return booking;
    }
}
