package com.afry.service.vehicle;

public enum Vehicles {
    CAR("Car"),
    MOTERBIKE("Moterbike");
    private final String vehicle;

    Vehicles(String type) {
        this.vehicle = type;
    }

    public String getVehicle() {
        return vehicle;
    }
}