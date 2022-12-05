package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.entity_factories.*;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.services.DatabaseService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Written by Thomas S. Andersen.
 */

@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressRepositoryTest {

  private static AddressRepository addressRepository;

  private static Address lastInsertedAddress;

  private static Address testAddress;
  private static DatabaseService database;

  @BeforeAll
  public static void init(DatabaseService databaseService) {
    database = databaseService;

    // Arrange
    // Create test repository
    addressRepository = new AddressRepository(database);


    // Create test address
    /*
    addressRepository.createAddress(address);
    lastInsertedAddress = addressRepository.last();*/
  }

  @AfterAll
  public static void after() {
    addressRepository.deleteAddress(lastInsertedAddress.getId());
  }


  @Test
  @Order(1)
  void testCreateAndLast_SaveToDatabase_AndReturnDataBaseObject() {
    testAddress = TestAddressFactory.create();

    //act
    addressRepository.createAddress(testAddress);
    lastInsertedAddress = addressRepository.last();

    //assert
    assertNotEquals(0L, lastInsertedAddress.getId(), "Id must not be 0");
    assertEquals(testAddress.getStreet(), lastInsertedAddress.getStreet(), "Street must be the same");
    assertEquals(testAddress.getCity(), lastInsertedAddress.getCity(), "City must be the same");
    assertEquals(testAddress.getZipCode(), lastInsertedAddress.getZipCode(), "ZipCode must be the same");
    assertEquals(testAddress.getCountry(), lastInsertedAddress.getCountry(), "Country must be the same");

  }

  @Test
  @Order(2)
  void getAddressList() {
    assertNotEquals(0, addressRepository.getAddressList().size(), "Size must nok be 0");
    assertNotEquals(0L, lastInsertedAddress.getId(), "Id must not be 0");
  }

  @Test
  @Order(3)
  void testFindAddressById() {
    //act
    Address findAddress = addressRepository.findAddressById(lastInsertedAddress.getId());

    //assert
    assertEquals(lastInsertedAddress.getId(), findAddress.getId(), "Id must match");
    assertEquals(lastInsertedAddress.getStreet(), findAddress.getStreet(), "Street must be the same");
    assertEquals(lastInsertedAddress.getCity(),findAddress.getCity(), "City must be the same");
    assertEquals(lastInsertedAddress.getZipCode(), findAddress.getZipCode(), "ZipCode must be the same");
    assertEquals(lastInsertedAddress.getCountry(), findAddress.getCountry(), "Country must be the same");
  }


  @Test
  @Order(4)
  void deleteAddress(){
    //act
    addressRepository.deleteAddress(lastInsertedAddress.getId());
    Address address = addressRepository.findAddressById(lastInsertedAddress.getId());

    assertEquals(null, address, "Must be null");
  }
}