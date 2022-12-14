package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.*;
import carrental.carrentalweb.entity_factories.*;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.services.DatabaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;


import static org.junit.jupiter.api.Assertions.*;

/*
 * Written by Thomas S. Andersen.
 */


@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PickupPointRepositoryTest {


  private static PickupPoint testPickupPoint;
  private static Address testAddress;
  private static PickupPoint lastInsertedPickupPoint;

  private static Address lastInsertedAddress;
  private static PickupPointRepository pickupPointRepository;
  private static AddressRepository addressRepository;
  private static DatabaseService database;

  @BeforeAll
  public static void init(DatabaseService databaseService) {
    database = databaseService;

    // Arrange
    // Create test repositories
    pickupPointRepository = new PickupPointRepository(database);
    addressRepository = new AddressRepository(database);

    //create test address
    addressRepository.createAddress(TestAddressFactory.create());
    lastInsertedAddress = addressRepository.last();


  }

  @AfterAll
  public static void after() {

    //pickupPointRepository.delete(lastInsertedPickupPoint);

  }


    @Test
    @Order(1)
    void testCreateAndLast_SaveToDatabase_AndReturnDataBaseObject() {
         // Arrange
        testPickupPoint = TestPickupPointFactory.create(lastInsertedAddress.getId());

      // Act
      pickupPointRepository.createPickupPoint(testPickupPoint);
      lastInsertedPickupPoint = pickupPointRepository.last();

      // Assert
      assertNotEquals(0L, lastInsertedPickupPoint.getId(), "Id must not be 0");
      //assertEquals(testPickupPoint.getName(), lastInsertedPickupPoint.getName(), "Name must be the same");
      //assertEquals(testPickupPoint.getAddressId(), lastInsertedPickupPoint.getAddressId(), "Address_id must be the same");


    }

    @Test
    @Order(2)
    void testGetPickupPointList(){


    assertNotEquals(0, pickupPointRepository.getPickupPointsList().size(), "Size must nok be 0");
    assertNotEquals(0L, lastInsertedPickupPoint.getId(), "Id must not be 0");
    }

    @Test
    @Order(3)
    void testFindPickupPointById() {
      //act
      PickupPoint findPickupPoint = pickupPointRepository.findPickupPointById(lastInsertedPickupPoint.getId());


      //assert
      assertEquals(lastInsertedPickupPoint.getId(), findPickupPoint.getId(), "Id must be the same");
      assertEquals(lastInsertedPickupPoint.getName(), findPickupPoint.getName(), "Name must be the same");
      assertEquals(lastInsertedPickupPoint.getAddressId(), findPickupPoint.getAddressId(), "Address_id must be the same");

    }

    @Test
    @Order(4)
    void updatePickupPoint() {
      // Arrange
      addressRepository.createAddress(TestAddressFactory.create());
      Address updatedAddress = addressRepository.last();
      PickupPoint updatedPickup = TestPickupPointFactory.create(updatedAddress.getId());
      updatedPickup.setId(lastInsertedPickupPoint.getId());

      // Act
      pickupPointRepository.updatePickupPoint(updatedPickup);
      //lastInsertedPickupPoint = pickupPointRepository.last();

      // Assert
      assertEquals(lastInsertedPickupPoint.getId(), updatedPickup.getId(), "Id must be the same");
      assertNotEquals(lastInsertedPickupPoint.getName(), updatedPickup.getName(), "Name must not be the same");
      assertNotEquals(lastInsertedPickupPoint.getAddressId(), updatedPickup.getAddressId(), "Address_id must not be the same");

    }



}

