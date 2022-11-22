package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

public class PickupPoint {

  private String name;
  private Address address;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public PickupPoint(String name, Address address, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.name = name;
    this.address = address;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public PickupPoint(String name, Address address, LocalDateTime createdAt) {
    this.name = name;
    this.address = address;
    this.createdAt = createdAt;
  }

  public PickupPoint() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
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
