package ua.pimenova.model.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Freight;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class CalculatorTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void getTotalCostTest(String citiFrom, String cityTo, Freight.FreightType freightType,
                          ExtraOptions.DeliveryType deliveryType, double weight, int expectedTotalCost) {
        assertEquals(expectedTotalCost, Calculator.getTotalCost(citiFrom, cityTo, freightType, deliveryType, weight));
    }

    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("Kyiv", "Kyiv", Freight.FreightType.GOODS, ExtraOptions.DeliveryType.TO_THE_BRANCH,
                        5.0, 50),
                Arguments.of("Kyiv", "Kharkiv", Freight.FreightType.COMPACT, ExtraOptions.DeliveryType.COURIER,
                        0.1, 91),
                Arguments.of("Kharkiv", "Lviv", Freight.FreightType.GLASS, ExtraOptions.DeliveryType.TO_THE_BRANCH,
                        12.5, 137),
                Arguments.of("Lviv", "Kyiv", Freight.FreightType.GOODS, ExtraOptions.DeliveryType.COURIER,
                        50.0, 260)
        );
    }
}