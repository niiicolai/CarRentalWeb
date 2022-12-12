package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/*
 * Written by Thomas S. Andersen.
 */

@Repository
public class AddressRepository {

  private final DatabaseService databaseService;

  public AddressRepository(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }


  public boolean createAddress(Address newAddress) {
    String query = "INSERT INTO address(street, city, zipCode, country, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";

    DatabaseRequestBody requestBody = new DatabaseRequestBody(
        newAddress.getStreet(),
        newAddress.getCity(),
        newAddress.getZipCode(),
        newAddress.getCountry(),
        newAddress.getLatitude(),
        newAddress.getLongitude());

    DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
    return databaseResponse.isSuccessful();
  }

  public List<Address> getAddressList() {
    String query = "SELECT * FROM address";
    DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
    return parseResponse(databaseResponse);
  }

  public Address findAddressById(Long id) {
    String sql = "SELECT * FROM address WHERE id= ?";
    DatabaseRequestBody body = new DatabaseRequestBody(id);
    DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
    return parseResponseFirst(databaseResponse);
  }

  public Address last() {
    String sql = "SELECT * FROM address ORDER BY created_at DESC LIMIT 1";
    DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
    return parseResponseFirst(databaseResponse);
  }

  public boolean deleteAddress(Long id) {
    String sql = "DELETE FROM address WHERE id = ?";
    DatabaseRequestBody requestBody = new DatabaseRequestBody(id);
    DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, requestBody);

    return databaseResponse.isSuccessful();
  }


  public Address parseResponseFirst(DatabaseResponse databaseResponse) {
    List<Address> addresses = parseResponse(databaseResponse);
    if (addresses.size() == 0) return null;
    else return addresses.get(0);
  }


  private List<Address> parseResponse(DatabaseResponse databaseResponse) {
    List<Address> addresses = new LinkedList<Address>();
    while (databaseResponse.hasNext()) {
      DatabaseRecord record = databaseResponse.next();

      addresses.add(
          new Address(
              (Long) record.map().get("id"),
              (String) record.map().get("street"),
              (String) record.map().get("city"),
              (String) record.map().get("zipCode"),
              (String) record.map().get("country"),
              (Double) record.map().get("latitude"),
              (Double) record.map().get("longitude"),
              (LocalDateTime) record.map().get("created_at"),
              (LocalDateTime) record.map().get("updated_at")
          ));
    }

    return addresses;
  }
}
