package com.smr.service.city;

import org.springframework.stereotype.Service;

@Service
public class CityBasedTollFeeService {
    public CityBasedTollFee getCity(String cityName) throws IllegalArgumentException {
        Cities v = Cities.valueOf(cityName);
        switch (v) {
            case STOCKHOLM:
                return new SthlmFee();
            case SOLNA:
                return new SolnaFee();
            default:
                throw new IllegalArgumentException("The city " + cityName + " is not contain toll fee");
        }
    }
}
