package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

public class Car {
    private long vehicleNumber;
    private String frameNumber;
    private String brandNumber;
    private String model;
    private String color;
    private int equipmentLevel;
    private double steelPrice;
    private double registrationFee;
    private double co2Discharge;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Car(long vehicleNumber, String frameNumber, String brandNumber, String model, String color, int equipmentLevel, double steelPrice, double registrationFee, double co2Discharge, String state) {
        this.vehicleNumber = vehicleNumber;
        this.frameNumber = frameNumber;
        this.brandNumber = brandNumber;
        this.model = model;
        this.color = color;
        this.equipmentLevel = equipmentLevel;
        this.steelPrice = steelPrice;
        this.registrationFee = registrationFee;
        this.co2Discharge = co2Discharge;
        this.state = state;
    }

    public long getVehicleNumber() {
        return vehicleNumber;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public String getBrandNumber() {
        return brandNumber;
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

    public String getState() {
        return state;
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

    public void setBrandNumber(String brandNumber) {
        this.brandNumber = brandNumber;
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

    public void setState(String state) {
        this.state = state;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
