package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

import groovyjarjarantlr4.v4.parse.ANTLRParser.prequelConstruct_return;

/*
 * Written by Thomas S. Andersen
 */
public class Address {

  private Long id;
  private String street;
  private String city;
  private String zipCode;
  private String country;
  private Double latitude;
  private Double longitude;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Address(
    Long id,
    String street,
    String city,
    String zipCode,
    String country,
	Double latitude,
	Double longitude,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
  ) {
    this.id = id;
    this.street = street;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
	this.latitude = latitude;
	this.longitude = longitude;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Address(
    String street,
    String city,
    String zipCode,
    String country,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
  ) {
    this.street = street;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Address(String street, String city, String zipCode, String country, 
  		Double latitude, Double longitude) {
    this.street = street;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
	this.latitude = latitude;
	this.longitude = longitude;
  }

  public Address() {}

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
  
  public String toString() {
	return String.format("%s, %s, %s, %s", street, city, zipCode, country);
  }
}
