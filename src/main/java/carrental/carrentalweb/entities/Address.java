package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

public class Address {

  private Long id;
  private String street;
  private String city;
  private String zipCode;
  private String country;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Address(Long id, String street, String city, String zipCode, String country, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.street = street;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Address(String street, String city, String zipCode, String country, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.street = street;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Address(String street, String city, String zipCode, String country, LocalDateTime createdAt) {
    this.street = street;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
    this.createdAt = createdAt;
  }

  public Address() {
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
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
