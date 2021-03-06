package com.smr.service.city;

import org.springframework.stereotype.Service;

@Service
public class SthlmFee implements CityBasedTollFee {
    /**
     * Method will calculate tollfee for each city according to the time of parking.
     *
     * @param hour
     * @param minute
     * @return
     */
    @Override
    public int calculateTollFee(int hour, int minute) {
        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }
}
