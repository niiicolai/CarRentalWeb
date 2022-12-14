package carrental.carrentalweb.entities;

import java.time.Duration;
import java.time.LocalDateTime;

/*
 * Written by Mikkel Aabo Simonsen
 */
public class Car {
    private long vehicleNumber;
    private String frameNumber;
    private String brand;
    private String model;
    private String color;
    private int equipmentLevel;
    private double steelPrice;
    private double registrationFee;
    private double co2Discharge;
    private double sellPrice;
    private boolean inspected;
    private boolean damaged;
    private boolean sold;

    private LocalDateTime firstRentedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //New car constructor (Does not need booking. createdAt and updatedAt are generated)
    public Car(String frameNumber, String brand, String model, String color, int equipmentLevel, double steelPrice, double registrationFee, double co2Discharge, boolean inspected, boolean damaged, boolean sold, double sellPrice, LocalDateTime firstRentedAt) {
        this.frameNumber = frameNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.equipmentLevel = equipmentLevel;
        this.steelPrice = steelPrice;
        this.registrationFee = registrationFee;
        this.co2Discharge = co2Discharge;
        this.inspected = inspected;
        this.damaged = damaged;
        this.sold = sold;
        this.sellPrice = sellPrice;
        this.firstRentedAt = firstRentedAt;
    }

    public Car(){}

    public Car(long vehicleNumber, String frameNumber, String brand, String model, String color, int equipmentLevel, double steelPrice, double registrationFee, double co2Discharge, boolean inspected, boolean damaged, boolean sold, double sellPrice, LocalDateTime firstRentedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.vehicleNumber = vehicleNumber;
        this.frameNumber = frameNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.equipmentLevel = equipmentLevel;
        this.steelPrice = steelPrice;
        this.registrationFee = registrationFee;
        this.co2Discharge = co2Discharge;
        this.inspected = inspected;
        this.damaged = damaged;
        this.sold = sold;
        this.sellPrice = sellPrice;
        this.firstRentedAt = firstRentedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getFirstRentedAt() {
        return firstRentedAt;
    }

    public void setFirstRentedAt(LocalDateTime firstRentedAt) {
        this.firstRentedAt = firstRentedAt;
    }

    public boolean getDamaged(){
        return this.damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean getInspected(){
        return this.inspected;
    }

    public boolean getSold() {
        return this.sold;
    }

    public double getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setSold(boolean sold){
        this.sold = sold;
    }

    public void setInspected(boolean inspected){
        this.inspected = inspected;
    }

    public long getVehicleNumber() {
        return vehicleNumber;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getEquipmentLevel() {
        return equipmentLevel;
    }

    public double getSteelPrice() {
        return steelPrice;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public double getCo2Discharge() {
        return co2Discharge;
    }

    public double getSecondsBeforeRent() {
        if (firstRentedAt == null)
            return -1;

        Duration duration = Duration.between(createdAt, firstRentedAt);
        return duration.toSeconds();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setVehicleNumber(long vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEquipmentLevel(int equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public void setSteelPrice(double steelPrice) {
        this.steelPrice = steelPrice;
    }

    public void setRegistrationFee(double registrationFee) {
        this.registrationFee = registrationFee;
    }

    public void setCo2Discharge(double co2Discharge) {
        this.co2Discharge = co2Discharge;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isInspected() {
        return inspected;
    }

    public String toString(){

        String inspectedYN = inspected ? "Yes" : "No";

        return "#" + vehicleNumber +
                " : " + frameNumber +
                " - " + brand +
                ", " + model +
                ": " + color + "\n" +
                ", Equipment-level: " + equipmentLevel +
                ", Steel-price: " + steelPrice +
                ", Registration-fee: " + registrationFee +
                ", Co2-fischarge: " + co2Discharge + "\n" +
                ", Inspected: " + inspectedYN +
                ", Created at: " + createdAt +
                ", Updated at: " + updatedAt;
    }

}