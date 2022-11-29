package carrental.carrentalweb.entity_factories;

import carrental.carrentalweb.builder.AddressBuilder;
import carrental.carrentalweb.builder.BookingBuilder;
import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.records.DatabaseRecord;

import java.time.LocalDateTime;
import java.util.HashMap;

public class TestAddressFactory {

  public static Address create () {
    LocalDateTime now = LocalDateTime.now();
    return new AddressBuilder()
        .street(now.toString())
        .city(now.toString())
        .zipCode(now.toString())
        .country(now.toString())
        .createdAt(now)
        .updatedAt(now)
        .build();
  }

  public static DatabaseRecord createDatabaseRecord(String street, String city, String zipCode, String country) {
    LocalDateTime now = LocalDateTime.now();
    HashMap<String, Object> map = new HashMap<>();
    map.put("street", street);
    map.put("city", city);
    map.put("zipCode", zipCode);
    map.put("country", country);
    map.put("created_at", now);
    map.put("updated_at", now);
    return new DatabaseRecord(map);
  }
}
