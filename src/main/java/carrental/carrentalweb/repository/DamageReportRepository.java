package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.services.DatabaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

// Mads
@Repository
public class DamageReportRepository {

    @Autowired
    DatabaseService databaseService;

    public DamageReport getByBookingId(Long id) {
        return new DamageReport();
    }

    public void update(DamageReport damageReport) {

    }
}
