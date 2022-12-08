package carrental.carrentalweb.builder;

import carrental.carrentalweb.entities.DamageReport;

import java.time.LocalDateTime;

/*
 * Written by Mads Kristian Pedersen
 */
public class DamageReportBuilder {
    private DamageReport damageReport;

    public DamageReportBuilder() {
        this.damageReport = new DamageReport();
    }

    public DamageReportBuilder bookingId(Long bookingId) {
        damageReport.setBookingId(bookingId);
        return this;
    }
    public DamageReportBuilder createdAt(LocalDateTime createdAt) {
        damageReport.setCreatedAt(createdAt);
        return this;
    }
    public DamageReportBuilder updatedAt(LocalDateTime updatedAt) {
        damageReport.setUpdatedAt(updatedAt);
        return this;
    }
    public DamageReport build() {
        return damageReport;
    }
}
