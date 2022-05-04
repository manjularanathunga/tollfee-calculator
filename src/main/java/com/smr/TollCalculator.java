package com.afry;

import com.afry.service.vehicle.TollFreeVehicles;
import com.afry.exception.NotFoundException;
import com.afry.rest.TollFeeRequest;
import com.afry.service.city.CityBasedTollFee;
import com.afry.service.city.CityBasedTollFeeService;
import com.afry.service.year.DateOnYear;
import com.afry.service.year.DateOnYearService;
import com.afry.service.vehicle.Vehicle;
import com.afry.service.vehicle.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TollCalculator {

    @Autowired
    private CityBasedTollFeeService calculateTollFee;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DateOnYearService yearService;

    @Value("${tollfee.default.year}")
    private String currentYear;

    /**
     * Method will receive external request to process tollfee
     *
     * @param request
     * @return
     * @throws NotFoundException
     */
    public double calculateFees(TollFeeRequest request) throws NotFoundException {
        CityBasedTollFee cityBasedTollFee = null;
        try {
            String selectedCityName = request.getCityName();
            if (!StringUtils.hasLength(selectedCityName)) {
                log.info("City name required ...");
                throw new NotFoundException("City name required");
            }
            cityBasedTollFee = calculateTollFee.getCity(selectedCityName);
        } catch (IllegalArgumentException e) {
            log.info("Tollfee City required ..." + e.getMessage());
            throw new NotFoundException("Tollfee is not setup for the City");
        }

        DateOnYear tollFeeYear = null;
        try {
            if (StringUtils.hasLength(request.getTollFeeYear())) {
                currentYear = request.getTollFeeYear();
            }
            tollFeeYear = yearService.getYears(currentYear);
        } catch (IllegalArgumentException e) {
            log.info("Tollfee year required ..." + e.getMessage());
            throw new NotFoundException("Tollfee is not setup for the year");
        }

        Vehicle v = null;
        try {
            v = vehicleService.getVehicle(request.getVehicle());
        } catch (Exception e) {
            log.info("Vehicle name given is not implemented inn the system ..." + e.getMessage());
            throw new NotFoundException("Vehicle name given is not implemented inn the system");
        }

        try {
            return getTollFee(v, request.getDates(), tollFeeYear, cityBasedTollFee);
        } catch (Exception e) {
            log.info("getTollFee" + e.getMessage());
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Calculate the total toll fee for one day
     *
     * @param vehicle     - the vehicle
     * @param dates       - date and time of all passes on one day
     * @param tollFeeYear
     * @return - the total toll fee for that day
     */
    public double getTollFee(Vehicle vehicle, Set<Date> dates, DateOnYear tollFeeYear, CityBasedTollFee cityBasedTollFee) throws Exception {
        Date intervalStart = dates.stream().findFirst().get();
        double totalFee = 0;
        //tollfee free vehicle
        if (isTollFreeVehicle(vehicle)) {
            log.info("tollfee free vehicle !");
            throw new NotFoundException("tollfee free vehicle ");
        }

        for (Date date : dates) {
            double nextFee = getTollFee(date, tollFeeYear, cityBasedTollFee);
            double tempFee = getTollFee(intervalStart, tollFeeYear, cityBasedTollFee);

            TimeUnit timeUnit = TimeUnit.MINUTES;
            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
            }
        }
        if (totalFee > 60) totalFee = 60;
        log.info("totalFee {}", totalFee);
        return totalFee;
    }

    /**
     * Method will filter the tollfee required vehicles
     *
     * @param vehicle
     * @return
     */
    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getType();
        return (Arrays.stream(TollFreeVehicles.values()).filter(v -> v.getType() == vehicleType).collect(Collectors.toList())).size() > 0;
    }

    private Boolean isTollFreeDate(Date date, DateOnYear tollFeeYear) throws IllegalArgumentException {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) return true;
        return tollFeeYear.isTollFreeDate(year, month, day, dayOfWeek);
    }

    /**
     * Method will calculate the tollfee according to the parameters
     *
     * @param date
     * @param tollFeeYear
     * @return
     */
    public double getTollFee(final Date date, DateOnYear tollFeeYear, CityBasedTollFee cityBasedTollFee) throws IllegalArgumentException {
        if (isTollFreeDate(date, tollFeeYear)) return 0;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return cityBasedTollFee.calculateTollFee(hour, minute);
    }
}

