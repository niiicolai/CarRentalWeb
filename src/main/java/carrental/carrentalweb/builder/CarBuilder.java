package carrental.carrentalweb.builder;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.Car;

/*
 * Written by Nicolai Berg Andersen
 */
public class CarBuilder {
    
    private Car car;

    public CarBuilder () {
        this.car = new Car();
    }

    public CarBuilder vehicleNumber(long vehicleNumber) {
        car.setVehicleNumber(vehicleNumber);
        return this;
    }

    public CarBuilder frameNumber(String frameNumber) {
        car.setFrameNumber(frameNumber);
        return this;
    }

    public CarBuilder brand(String brand) {
        car.setBrand(brand);
        return this;
    }

    public CarBuilder model(String model) {
        car.setModel(model);
        return this;
    }

    public CarBuilder color(String color) {
        car.setColor(color);
        return this;
    }

    public CarBuilder equipmentLevel(int equipmentLevel) {
        car.setEquipmentLevel(equipmentLevel);
        return this;
    }

    public CarBuilder steelPrice(double steelPrice) {
        car.setSteelPrice(steelPrice);
        return this;
    }

    public CarBuilder registrationFee(double registrationFee) {
        car.setRegistrationFee(registrationFee);
        return this;
    }

    public CarBuilder co2Discharge(double co2Discharge) {
        car.setCo2Discharge(co2Discharge);
        return this;
    }

    public CarBuilder inspected(boolean inspected) {
        car.setInspected(inspected);
        return this;
    }

    public CarBuilder damaged(boolean damaged) {
        car.setDamaged(damaged);
        return this;
    }

    public CarBuilder createdAt(LocalDateTime createdAt) {
        car.setCreatedAt(createdAt);
        return this;
    }

    public CarBuilder updatedAt(LocalDateTime updatedAt) {
        car.setUpdatedAt(updatedAt);
        return this;
    }

    public Car build() {
        return car;
    }
}
