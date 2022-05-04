package com.smr.rest;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TollFeeResponce {
    private double calculatedFee;
    private String errorMsg;
}
