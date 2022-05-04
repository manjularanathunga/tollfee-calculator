package com.afry.service.vehicle.impl;

import com.afry.service.vehicle.Vehicle;


public class Car implements Vehicle {
    @Override
    public String getType() {
        return "Car";
    }
}
