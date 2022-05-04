package com.afry.rest;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@ToString
public class TollFeeRequest {
    private String cityName;
    private String vehicle;
    private String tollFeeYear;
    private Set<Date> dates;
}
