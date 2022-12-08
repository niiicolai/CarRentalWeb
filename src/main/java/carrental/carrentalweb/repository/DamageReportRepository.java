package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


/*
 * Written by Mads Kristian Pedersen
 */
@Repository
public class DamageReportRepository {
    private final DatabaseService databaseService;
    public DamageReportRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public DamageReport get(String column, Object value) {
        String sql = String.format("SELECT * FROM damage_reports WHERE %s=? ", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        return parseResponseFirst(databaseResponse);
    }
    public DatabaseResponse create(List<String> descriptions, Long bookingId) {
        String reportQuery = "INSERT INTO damage_reports (booking_id) VALUES (?)";
        DatabaseRequestBody reportBody = new DatabaseRequestBody(bookingId);
        DatabaseResponse reportDatabaseResponse = databaseService.executeUpdate(reportQuery, reportBody);
        for (String description : descriptions) {
            String specQuery = "INSERT INTO damage_report_specifications (report_id, spec_description) VALUES (?, ?)";
            DatabaseRequestBody specBody = new DatabaseRequestBody(bookingId, description);
            databaseService.executeUpdate(specQuery, specBody);
        }
        return reportDatabaseResponse;
    }
    public boolean update(DamageReport damageReport, List<DamageSpecification> dmgSpecs) {
        String reportQuery = "UPDATE damage_reports SET booking_id=?";
        DatabaseRequestBody reportBody = new DatabaseRequestBody(damageReport.getBookingId());
        DatabaseResponse reportDatabaseResponse = databaseService.executeUpdate(reportQuery, reportBody);
        for (DamageSpecification dmgSpec : dmgSpecs) {
            String specQuery = "UPDATE damage_report_specifications SET report_id=?, spec_description=?";
            DatabaseRequestBody specBody = new DatabaseRequestBody(damageReport.getBookingId(), dmgSpec.getDescription());
            databaseService.executeUpdate(specQuery, specBody);
        }
        return reportDatabaseResponse.isSuccessful();
    }
    public DamageReport last() {
        String query = "SELECT * FROM damage_reports ORDER BY created_at DESC LIMIT 1";
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
        return parseResponseFirst(databaseResponse);
    }
    public DamageReport parseResponseFirst(DatabaseResponse databaseResponse) {
        List<DamageReport> damageReports = parseResponse(databaseResponse);
        if (damageReports.size() == 0) return null;
        else return damageReports.get(0);
    }
    public List<DamageReport> parseResponse(DatabaseResponse databaseResponse) {
        List<DamageReport> dmgReports = new LinkedList<>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();
            dmgReports.add(
                    new DamageReport(
                            (long) record.map().get("booking_id"),
                            (LocalDateTime) record.map().get("created_at"),
                            (LocalDateTime) record.map().get("updated_at")
                    )
            );
        }
        return dmgReports;
    }
}
