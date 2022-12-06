package carrental.carrentalweb.builder;

import carrental.carrentalweb.entities.Address;


import java.time.LocalDateTime;

/*
 * Written by Thomas S. Andersen.
 */

public class AddressBuilder {


  private Address address;

  public AddressBuilder () {
    this.address = new Address();
  }

  /*public AddressBuilder id(long id) {
    address.setId(id);
    return this;
  }*/


  public AddressBuilder street(String street) {
    address.setStreet(street);
    return this;
  }

  public AddressBuilder city(String city) {
    address.setCity(city);
    return this;
  }

  public AddressBuilder zipCode(String zipCode) {
    address.setZipCode(zipCode);
    return this;
  }

  public AddressBuilder country(String country) {
    address.setCountry(country);
    return this;
  }

  public AddressBuilder createdAt(LocalDateTime createdAt) {
    address.setCreatedAt(createdAt);
    return this;
  }

  public AddressBuilder updatedAt(LocalDateTime updatedAt) {
    address.setUpdatedAt(updatedAt);
    return this;
  }

  public Address build() {
    return address;
  }
}
