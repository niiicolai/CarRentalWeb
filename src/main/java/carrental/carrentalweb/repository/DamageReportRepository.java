package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

// Mads
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

        return parseResponse(databaseResponse).get(0);
    }

    public boolean create(DamageReport damageReport) {
        String query = "INSERT INTO damage_reports (booking_id) VALUES (?)";

        DatabaseRequestBody body = new DatabaseRequestBody(damageReport.getBookingId());

        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);

        return databaseResponse.isSuccessful();

    }

    private List<DamageReport> parseResponse(DatabaseResponse databaseResponse) {
        List<DamageReport> dmgReports = new LinkedList<DamageReport>();
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
