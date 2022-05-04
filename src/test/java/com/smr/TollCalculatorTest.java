package com.smr;

import com.smr.exception.NotFoundException;
import com.smr.rest.TollFeeRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringBootTest
class TollCalculatorTest {

    @Autowired
    private TollCalculator tollCalculator;

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - executes before each test method in this class");
    }

    @Test
    void calculatefees_city_name_required() {
        TollFeeRequest request = new TollFeeRequest();
        //double results =  tollCalculator.calculateFees(request);
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            tollCalculator.calculateFees(request);
        });
        assertEquals(exception.getMessage(), "City name required");
    }

    @Test
    void calculatefees_city_name_wrong() {
        TollFeeRequest request = new TollFeeRequest();
        request.setCityName("NOCITY");
        //double results =  tollCalculator.calculateFees(request);
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            tollCalculator.calculateFees(request);
        });
        assertEquals(exception.getMessage(), "Tollfee is not setup for the City");
    }

    @Test
    @Disabled
    void calculatefees_city_name_correct_no_year() {
        TollFeeRequest request = new TollFeeRequest();
        request.setCityName("STOCKHOLM");
        //double results =  tollCalculator.calculateFees(request);
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            tollCalculator.calculateFees(request);
        });
        assertEquals(exception.getMessage(), "Tollfee is not setup for the year");// Vehicle name given is not implemented inn the system
    }

    @Test
    void calculatefees_vehicle_is_not_implemented_in_the_system() {
        TollFeeRequest request = new TollFeeRequest();
        request.setCityName("STOCKHOLM");
        //double results =  tollCalculator.calculateFees(request);
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            tollCalculator.calculateFees(request);
        });
        assertEquals(exception.getMessage(), "Vehicle name given is not implemented inn the system");
    }

    void calculatefees_year_wrong() {
        TollFeeRequest request = new TollFeeRequest();
        request.setCityName("STOCKHOLM");
        request.setTollFeeYear("2000");
        //double results =  tollCalculator.calculateFees(request);
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            tollCalculator.calculateFees(request);
        });
        assertEquals(exception.getMessage(), "Tollfee is not setup for the year");
    }

    @Test
    void calculatefees_year_correct_no_date_provieded() {
        TollFeeRequest request = new TollFeeRequest();
        request.setCityName("STOCKHOLM");
        request.setTollFeeYear("2013");
        //double results =  tollCalculator.calculateFees(request);
        AtomicReference<Double> results = new AtomicReference<>((double) 0);
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            results.set(tollCalculator.calculateFees(request));
        });
        Double d = results.get();
        assertEquals(d.doubleValue(), 0.0,0);
    }

    @Test
    void calculatefees_year_correct_with_date_provieded() {
        TollFeeRequest request = new TollFeeRequest();
        request.setCityName("STOCKHOLM");
        request.setTollFeeYear("2013");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,2013);
        cal.set(Calendar.MONTH,01);
        cal.set(Calendar.DAY_OF_MONTH,23);
        cal.set(Calendar.HOUR_OF_DAY,06);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        Date start = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY,12);
        Date end = cal.getTime();

        Set<Date> dates = new HashSet<Date>();
        dates.add(start);
        dates.add(end);
        request.setDates(dates);
        //double results =  tollCalculator.calculateFees(request);
        AtomicReference<Double> results = new AtomicReference<>((double) 0);
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            results.set(tollCalculator.calculateFees(request));
        });
        Double d = results.get();
        assertEquals(d, 0.0,0);
    }


    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }

    @Test
    @Disabled
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        assertAll("numbers",
                () -> assertEquals(numbers[0], 1),
                () -> assertEquals(numbers[3], 3),
                () -> assertEquals(numbers[4], 1)
        );
    }

    @AfterEach
    void tearDown() {
        log.info("@AfterEach - executed after each test method.");
    }

    @AfterAll
    static void done() {
        log.info("@AfterAll - executed after all test methods.");
    }


}