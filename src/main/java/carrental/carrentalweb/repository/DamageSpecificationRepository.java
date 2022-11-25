package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.services.DatabaseService;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

        LinkedList<Object> values = new LinkedList<>();
        values.add(value);

        List<HashMap<String, Object>> resultList = databaseService.executeQuery(query, values);
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    public List<DamageSpecification> getAll() {
        String query = "SELECT * FROM damage_specifications";

        List<HashMap<String, Object>> resultList = databaseService.executeQuery(query, new LinkedList<>());
        if (resultList == null) return null;

        return parseFromList(resultList);
    }

    public boolean create(DamageSpecification dmgSpec) {
        String query = "INSERT INTO damage_specifications (" +
                "description," +
                "damaged," +
                "price," +
                "created_at," +
                "updated_at)" +
                "VALUES (?, ?, ?, ?, ?)";

        LinkedList<Object> values = new LinkedList<>();
        values.add(dmgSpec.getDescription());
        values.add(dmgSpec.isDamaged());
        values.add(dmgSpec.getPrice());
        values.add(dmgSpec.getCreatedAt());
        values.add(dmgSpec.getUpdatedAt());

        databaseService.executeUpdate(query, values);
        return true;
    }

    public boolean update(DamageSpecification dmgSpec) {
        String query = "UPDATE damage_specifications " +
                "SET damaged=?," +
                "price=?," +
                "updated_at=?";

        LinkedList<Object> values = new LinkedList<>();
        values.add(dmgSpec.isDamaged());
        values.add(dmgSpec.getPrice());
        values.add(dmgSpec.getUpdatedAt());

        databaseService.executeUpdate(query, values);

        return true;
    }

    private DamageSpecification parseFromMap(HashMap<String, Object> map) {
        if (map == null) return null;
        return new DamageSpecification(
                (String) map.get("description"),
                (boolean) map.get("damaged"),
                (double) map.get("price"),
                (LocalDateTime) map.get("created_at"),
                (LocalDateTime) map.get("updated_at")
        );
    }
    private List<DamageSpecification> parseFromList(List<HashMap<String, Object>> list) {
        List<DamageSpecification> dmgSpec = new ArrayList<>();
        for (HashMap<String, Object> map : list) {
            dmgSpec.add(parseFromMap(map));
        }
        return dmgSpec;
    }
}
