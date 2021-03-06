package com.smr.service.vehicle;


import com.smr.service.vehicle.impl.Car;
import com.smr.service.vehicle.impl.Motorbike;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    public Vehicle getVehicle(String vString) throws IllegalArgumentException {
        Vehicles v = Vehicles.valueOf(vString);
        switch (v) {
            case CAR:
                return new Car();
            case MOTERBIKE:
                return new Motorbike();
            default:
                throw new IllegalArgumentException("Invalid Vehicle: " + vString);
        }
    }
}


