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

        return parseResponse(databaseResponse).get(0);
    }

    public List<DamageSpecification> getAll() {
        String query = "SELECT * FROM damage_specifications";

        DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());

        return parseResponse(databaseResponse);
    }

    public boolean create(DamageSpecification dmgSpec) {
        String query = "INSERT INTO damage_specifications (" +
                "description," +
                "damaged," +
                "price," +
                "created_at," +
                "updated_at)" +
                "VALUES (?, ?, ?, ?, ?)";

        DatabaseRequestBody body = new DatabaseRequestBody(dmgSpec.getDescription(), dmgSpec.isDamaged(),
                dmgSpec.getPrice());

        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);
        return databaseResponse.isSuccessful();
    }

    public boolean update(DamageSpecification dmgSpec) {
        String query = "UPDATE damage_specifications " +
                "SET damaged=?," +
                "price=?," +
                "updated_at=?";

        DatabaseRequestBody body = new DatabaseRequestBody(dmgSpec.getDescription(), dmgSpec.isDamaged(),
                dmgSpec.getPrice());

        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);

        return databaseResponse.isSuccessful();
    }

    private List<DamageSpecification> parseResponse(DatabaseResponse databaseResponse) {
        List<DamageSpecification> dmgSpecs = new LinkedList<DamageSpecification>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();

            dmgSpecs.add(
                    new DamageSpecification(
                            (String) record.map().get("description"),
                            (boolean) record.map().get("damaged"),
                            (double) record.map().get("price"),
                            (LocalDateTime) record.map().get("created_at"),
                            (LocalDateTime) record.map().get("updated_at")
                    )
            );
        }
        return dmgSpecs;
    }
}
