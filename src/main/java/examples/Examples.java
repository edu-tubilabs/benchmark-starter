package examples;

import examples.stats.Scenario;
import examples.stats.Stats;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Examples {

    public void startBenchmarkTest() {

        for (Scenario scenario : Scenario.values()) {
            int tune = 10;
            int samplingSpace = 10000;
            System.out.println("Starting Scenario: " + scenario + " [Sampling Space: n>=0 ; 10^n ; n <= " + samplingSpace + " Tune: " + tune + "]");
            testScenario(scenario, samplingSpace, tune);
        }
    }

    public void testScenario(Scenario scenario, Integer samplingSpace, Integer tune) {

        TreeMap<Integer, Stats> testCases = new TreeMap<Integer, Stats>() {
        };
        int param = 1;
        for (; param <= samplingSpace; param = param * 10) {

            Stats stats = new Stats(param);
            for (int i = 0; i < tune; i++) {
                switch (scenario) {
                    case CREATE_STRING_ARRAY:
                        stats.addResult(createStringArray(param));
                        break;
                    case CREATE_INTEGER_ARRAY:
                        stats.addResult(createIntegerArray(param));
                        break;
                    case O_N_ARITHMETIC_OPERATION:
                        stats.addResult(simulate_O_N_Operation(param));
                        break;
                    case O_N2_ARITHMETIC_OPERATION:
                        stats.addResult(simulate_O_N2_Operation(param));
                        break;
                    default:
                        break;
                }
            }
            testCases.put(param, stats.build());
        }
        printResult(testCases, scenario);
    }

    private Double simulate_O_N_Operation(int param) {

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < param; i++) {
            data.add(i);
        }

        long startTime = System.nanoTime();
        long total = 0;
        for (int i = 0; i < data.size(); i++) {
            total += i;
        }
        Double dif = Double.valueOf((System.nanoTime() - startTime)) / 1000000;
        return dif;
    }

    private Double simulate_O_N2_Operation(int param) {

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < param; i++) {
            data.add(i);
        }

        long startTime = System.nanoTime();
        long total = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                total += i + j;
            }
        }
        Double dif = Double.valueOf((System.nanoTime() - startTime)) / 1000000;
        return dif;
    }


    public Double createStringArray(int param) {

        long startTime = System.nanoTime();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < param; i++) {
            data.add("Number : " + i);
        }
        Double dif = Double.valueOf((System.nanoTime() - startTime)) / 1000000;
        //System.out.println("createData : " + (dif) / 1000000 + " ms");
        return dif;
    }

    public Double createIntegerArray(int param) {

        long startTime = System.nanoTime();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < param; i++) {
            data.add(i);
        }
        Double dif = Double.valueOf((System.nanoTime() - startTime)) / 1000000;
        //System.out.println("createData : " + (dif) / 1000000 + " ms");
        return dif;
    }

    public Double doArithmetic(int param) {

        long startTime = System.nanoTime();
        long total = 0;
        for (int i = 0; i < param; i++) {
            total += i;
        }
        Double dif = Double.valueOf((System.nanoTime() - startTime)) / 1000000;
        //System.out.println("createData : " + (dif) / 1000000 + " ms");
        return dif;
    }


    private void printResult(Map<Integer, Stats> testCases, Scenario scenario) {

        System.out.println("Results of Scenario:" + scenario);
        testCases.forEach((k, v) -> {
            System.out.println("Sampling: " + k
                    + " Min: " + round(v.getMin()) + " ms. "
                    + " Max: " + round(v.getMax()) + " ms. "
                    + " Avg: " + round(v.getAvg()) + " ms. "
                    + " Total: " + round(v.getTotal()) + " ms. "
                    + " StdDev: " + round(v.getStdDev()) + " ms. "
            );
        });
        System.out.println("-------------------------------------------------");
    }

    private Double round(Double val) {
        return new BigDecimal(val.toString()).setScale(5, RoundingMode.HALF_UP).doubleValue();
    }
}
