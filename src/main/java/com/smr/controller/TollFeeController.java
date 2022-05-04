package com.smr.controller;

import com.smr.TollCalculator;
import com.smr.rest.TollFeeRequest;
import com.smr.rest.TollFeeResponce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class is created for enable REST web service for tollfee calculating system
 */
@Slf4j
@RestController
@RequestMapping("/tollfee")
public class TollFeeController {

    @Autowired
    private TollCalculator tollCalculator;

    @GetMapping
    public ResponseEntity calculate() {
        return new ResponseEntity<String>("Welcome to CityBased TollFee calculation System", HttpStatus.ACCEPTED);
    }

    @PostMapping("/calculate")
    public ResponseEntity calculateFee(@RequestBody TollFeeRequest request) {
        log.info("Came inside the calculate fee method" + request.toString());
        TollFeeResponce v = new TollFeeResponce();
        try {
            v.setCalculatedFee(tollCalculator.calculateFees(request));
        } catch (Exception e) {
            //e.printStackTrace();
            v.setErrorMsg(e.getMessage());
            return new ResponseEntity<TollFeeResponce>(v, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<TollFeeResponce>(v, HttpStatus.ACCEPTED);
    }
}
