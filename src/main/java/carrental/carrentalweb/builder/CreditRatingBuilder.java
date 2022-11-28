package carrental.carrentalweb.builder;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;

public class CreditRatingBuilder {

    private CreditRating creditRating;

    public CreditRatingBuilder () {
        this.creditRating = new CreditRating();
    }

    public CreditRatingBuilder userId(long userId) {
        creditRating.setUserId(userId);
        return this;
    }

    public CreditRatingBuilder state(CreditRatingState state) {
        creditRating.setState(state);
        return this;
    }

    public CreditRatingBuilder createdAt(LocalDateTime createdAt) {
        creditRating.setCreatedAt(createdAt);
        return this;
    }

    public CreditRatingBuilder updatedAt(LocalDateTime updatedAt) {
        creditRating.setUpdatedAt(updatedAt);
        return this;
    }

    public CreditRating build() {
        return creditRating;
    }
}
