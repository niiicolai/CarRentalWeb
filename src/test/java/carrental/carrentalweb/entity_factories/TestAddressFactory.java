package carrental.carrentalweb.entity_factories;

import carrental.carrentalweb.builder.AddressBuilder;
import carrental.carrentalweb.builder.BookingBuilder;
import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.records.DatabaseRecord;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class TestAddressFactory {

  public static Address create () {
    LocalDateTime now = LocalDateTime.now();
    return new AddressBuilder()
        .street(now.toString())
        .city(now.toString())
        .zipCode(now.toString())
        .country(now.toString())
        .latitude(Math.random() * 100)
        .longitude(Math.random() * 100)
        .createdAt(now)
        .updatedAt(now)
        .build();
  }

  public static DatabaseRecord createDatabaseRecord() {
    LocalDateTime now = LocalDateTime.now();
    HashMap<String, Object> map = new HashMap<>();
    map.put("id", 0);
    map.put("street", now.toString());
    map.put("city", now.toString());
    map.put("zipCode", now.toString());
    map.put("country", now.toString());
    map.put("latitude", Math.random() * 100);
    map.put("longitude", Math.random() * 100);
    map.put("created_at", now.toString());
    map.put("updated_at", now.toString());
    return new DatabaseRecord(map);
  }
}
