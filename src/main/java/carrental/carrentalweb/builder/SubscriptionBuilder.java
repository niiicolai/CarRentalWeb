package carrental.carrentalweb.builder;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.Subscription;

public class SubscriptionBuilder {
    
    private Subscription subscription;

    public SubscriptionBuilder () {
        this.subscription = new Subscription();
    }

    public SubscriptionBuilder name(String name) {
        subscription.setName(name);
        return this;
    }

    public SubscriptionBuilder price(double price) {
        subscription.setPrice(price);
        return this;
    }

    public SubscriptionBuilder days(double days) {
        subscription.setDays(days);
        return this;
    }

    public SubscriptionBuilder available(boolean available) {
        subscription.setAvailable(available);
        return this;
    }

    public SubscriptionBuilder createdAt(LocalDateTime createdAt) {
        subscription.setCreatedAt(createdAt);
        return this;
    }

    public SubscriptionBuilder updatedAt(LocalDateTime updatedAt) {
        subscription.setUpdatedAt(updatedAt);
        return this;
    }

    public Subscription build() {
        return subscription;
    }
}
