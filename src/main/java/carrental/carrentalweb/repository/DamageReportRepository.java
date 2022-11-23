package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.utilities.MySQLConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

// Mads
@Repository
public class DamageReportRepository {
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    public DamageReport getByBookingId(Long id) {
        return new DamageReport();
    }

    public void update(DamageReport damageReport) {

    }
}