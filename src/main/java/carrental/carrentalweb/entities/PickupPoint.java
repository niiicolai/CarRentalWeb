package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

public class PickupPoint {

  private long id;
  private String name;
  private long addressId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  /*
   * Jeg har tilføjet getter til address her,
   * som bliver sat når et pickup point bliver læst i rep.
   * ~ Nicolai
   */
  private Address address;

  public PickupPoint(String name, long addressId) {
    this.name = name;
    this.addressId = addressId;
  }

  public PickupPoint(
    long id,
    String name,
    long addressId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Address address
  ) {
    this.id = id;
    this.name = name;
    this.addressId = addressId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.address = address;
  }

  public PickupPoint() {}

  /*
   * Jeg har tilføjet getter til address her,
   * som bliver sat når et pickup point bliver læst i rep.
   * ~ Nicolai
   */
  public Address getAddress() {
    return address;
  }

  /*
   * Jeg har tilføjet setter til address her,
   * som bliver sat når et pickup point bliver læst i rep.
   * ~ Nicolai
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public long getAddressId() {
    return addressId;
  }

  public void setAddressId(long addressId) {
    this.addressId = addressId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
