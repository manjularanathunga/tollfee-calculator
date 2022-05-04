package com.smr.service.vehicle.impl;

import com.smr.service.vehicle.Vehicle;


public class Car implements Vehicle {
    @Override
    public String getType() {
        return "Car";
    }
}
