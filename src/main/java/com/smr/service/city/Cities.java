package com.smr.service.city;

public enum Cities {
    STOCKHOLM("Stockholm"),
    SOLNA("Solna");

    private final String cityName;

    Cities(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}