package pl.edu.pwr.solarmonitoring.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class EnergyServiceUtilsTest {

    @Test
    void getProducedSummaryResponseTest() {
        List<Double> list1 = Arrays.asList(2.0, 3.0, 4.0, 2.0, 3.0, 4.0, 2.0, 3.0, 4.0, 2.0, 3.0, 4.0);
        List<Double> list2 = Arrays.asList(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0);
        List<Double> list3 = Arrays.asList(12.0, 5.0, 2.0, 12.0, 5.0, 2.0, 12.0, 5.0, 2.0, 12.0, 5.0, 2.0);
        List<Double> expected = Arrays.asList(15.0, 10.0, 9.0, 15.0, 10.0, 9.0, 15.0, 10.0, 9.0, 15.0, 10.0, 9.0);
        List<List<Double>> list = List.of(list1, list2, list3);
        List<Double> result = EnergyServiceUtils.getProducedSummaryResponse(list).getData();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getWornOutEnergyTest() {
        List<Double> produced = Arrays.asList(2.0, 3.0, 4.0, 2.0, 3.0, 4.0, 2.0, 3.0, 4.0, 2.0, 3.0, 4.0);
        List<Double> remitted = Arrays.asList(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0);
        List<Double> result = EnergyServiceUtils.getWornOutEnergy(remitted, produced).getData();
        List<Double> expected = Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void roundListTest() {
        List<Double> input = Arrays.asList(1.123, 2.123, 3.123, 1.123, 2.123, 3.123, 1.123, 2.123, 3.123, 1.123, 2.123, 3.123);
        EnergyServiceUtils.roundList(input);
        List<Double> expected = Arrays.asList(1.12, 2.12, 3.12, 1.12, 2.12, 3.12, 1.12, 2.12, 3.12, 1.12, 2.12, 3.12);
        Assertions.assertEquals(expected, input);
    }
}