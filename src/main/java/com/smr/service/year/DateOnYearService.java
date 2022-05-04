package com.smr.service.year;


import com.smr.service.year.impl.Y2013;
import com.smr.service.year.impl.Y2014;
import org.springframework.stereotype.Service;

@Service
public class DateOnYearService {
    public DateOnYear getYears(String vString) throws IllegalArgumentException {
        switch (vString) {
            case "2013":
                return new Y2013();
            case "2014":
                return new Y2014();
            default:
                throw new IllegalArgumentException("Toll fee not found for given year " + vString);
        }
    }
}
