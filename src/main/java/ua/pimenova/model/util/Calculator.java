package ua.pimenova.model.util;

import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Freight;

import java.util.HashMap;
import java.util.Map;

public class Calculator {

    private static final Map<Double, Integer> rates = new HashMap<>();

    static {
        rates.put(0.5, 35);
        rates.put(1.0, 37);
        rates.put(2.0, 40);
        rates.put(5.0, 50);
        rates.put(10.0, 60);
        rates.put(20.0, 70);
        rates.put(30.0, 80);
        rates.put(40.0, 100);
    }

    public Calculator() {

    }

    public static int getTotalCost(String citiFrom, String cityTo, Freight.FreightType freightType,
                                   ExtraOptions.DeliveryType deliveryType, double weight) {
        ExtraOptions.Direction direction;
        if(citiFrom.equalsIgnoreCase(cityTo)) {
            direction = ExtraOptions.Direction.CITY;
        } else {
            direction = ExtraOptions.Direction.COUNTRY;
        }
        int fare = getRate(weight);
        double cost = fare * direction.getCoefficientOfDirection() * freightType.getCoefficientOfFreightType()
                * deliveryType.getCoefficientOfDeliveryType();
        return (int) Math.ceil(cost);
    }

    private static int getRate(double weight) {
        if(weight <= 0.5) {
            return rates.get(0.5);
        } else if(weight > 0.5 && weight <= 1.0) {
            return rates.get(1.0);
        } else if(weight > 1.0 && weight <= 2.0) {
            return rates.get(2.0);
        } else if(weight > 2.0 && weight <= 5.0) {
            return rates.get(5.0);
        } else if(weight > 5.0 && weight <= 10.0) {
            return rates.get(10.0);
        } else if(weight > 10.0 && weight <= 20.0) {
            return rates.get(20.0);
        } else if(weight > 20.0 && weight <= 30.0) {
            return rates.get(30.0);
        }
        return rates.get(40.0);
    }
}
