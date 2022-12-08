package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.DamageSpecification;
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
public class DamageSpecificationRepository {
    private final DatabaseService databaseService;
    public DamageSpecificationRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    public DamageSpecification get(String column, Object value) {
        String query = String.format("SELECT * FROM damage_specifications WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, body);
        return parseResponseFirst(databaseResponse);
    }
    public List<DamageSpecification> getAll() {
        String query = "SELECT * FROM damage_specifications";
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
        return parseResponse(databaseResponse);
    }
    public List<DamageSpecification> getAllById(Object value) {
        String query = "SELECT * FROM damage_report_specifications " +
                "JOIN damage_specifications " +
                "ON damage_report_specifications.spec_description=damage_specifications.description " +
                "WHERE damage_report_specifications.report_id=?";
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, body);
        return parseResponse(databaseResponse);
    }
    public boolean create(DamageSpecification dmgSpec) {
        String query = "INSERT INTO damage_specifications (description, price) VALUES (?, ?, ?)";
        DatabaseRequestBody body = new DatabaseRequestBody(dmgSpec.getDescription(), dmgSpec.getPrice());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);
        return databaseResponse.isSuccessful();
    }
    public boolean update(DamageSpecification dmgSpec) {
        String query = "UPDATE damage_specifications SET price=? WHERE description=?";
        DatabaseRequestBody body = new DatabaseRequestBody(dmgSpec.getPrice(), dmgSpec.getDescription());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);
        return databaseResponse.isSuccessful();
    }

    public boolean delete(DamageSpecification dmgSpec) {
        String query = "DELETE FROM damage_specifications WHERE description=?";
        DatabaseRequestBody requestBody = new DatabaseRequestBody(dmgSpec.getDescription());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
        return databaseResponse.isSuccessful();
    }
    public DamageSpecification last() {
        String query = "SELECT * FROM damage_specifications ORDER BY created_at DESC LIMIT 1";
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
        return parseResponseFirst(databaseResponse);
    }
    public DamageSpecification parseResponseFirst(DatabaseResponse databaseResponse) {
        List<DamageSpecification> damageSpecifications = parseResponse(databaseResponse);
        if (damageSpecifications.size() == 0) return null;
        else return damageSpecifications.get(0);
    }
    public List<DamageSpecification> parseResponse(DatabaseResponse databaseResponse) {
        List<DamageSpecification> dmgSpecs = new LinkedList<>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();
            dmgSpecs.add(
                    new DamageSpecification(
                            (String) record.map().get("description"),
                            (double) record.map().get("price"),
                            (LocalDateTime) record.map().get("created_at"),
                            (LocalDateTime) record.map().get("updated_at")
                    )
            );
        }
        return dmgSpecs;
    }
}
