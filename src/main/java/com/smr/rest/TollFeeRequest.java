package com.smr.rest;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@ToString
@Builder
@NoArgsConstructor
public class TollFeeRequest {
    private String cityName;
    private String vehicle;
    private String tollFeeYear;
    private Set<Date> dates;
}
