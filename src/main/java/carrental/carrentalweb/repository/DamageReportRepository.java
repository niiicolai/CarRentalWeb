package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.services.DatabaseService;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
        String sql = String.format("SELECT * FROM damage_reports WHERE %s=? " +
                "LEFT OUTER JOIN damage_report_specifications " +
                "ON damage_reports.id = damage_report_specifications.report_id " +
                "LEFT OUTER JOIN damage_specifications " +
                "ON damage_report_specifications.spec_description = damage_specifications.description",
                column);

        LinkedList<Object> values = new LinkedList<>();
        values.add(value);

        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, values);
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    public void update(DamageReport damageReport) {

    }
    private DamageReport parseFromMap(HashMap<String, Object> map) {
        if (map == null) return null;
        return new DamageReport(
                (List<DamageSpecification>) map.get("damage_specifications"),
                (Long) map.get("booking_id"),
                (LocalDateTime) map.get("created_at"),
                (LocalDateTime) map.get("updated_at")
        );
    }
}
