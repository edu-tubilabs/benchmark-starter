package examples.stats;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Data
public class Stats {

    public int parameter;
    public List<Double> resultList;
    public Double max;
    public Double min;
    public Double avg;
    public Double total;
    public Double stdDev;

    public Stats(int parameter) {
        max = 0.0D;
        min = 0.0D;
        avg = 0.0D;
        total = 0.0D;
        stdDev= 0.0D;
        this.parameter = parameter;
        resultList = new ArrayList<>();
    }

    public void addResult(Double result) {
        resultList.add(result);
    }

    public Stats build(){

        setStats();
        return this;
    }

    private void setStats() {

        for (Double val : resultList) {
            if (val > max) {
                max = val;
            }else if( min == 0.0D){
                min = val;
            }else if (val < min) {
                min = val;
            }
            total += val;
        }
        avg = (total / resultList.size());
        stdDev = calculateSD();

    }

    private Double calculateSD()
    {
        Double sum = 0.0, standardDeviation = 0.0;

        for(Double result: resultList) {
            standardDeviation += Math.pow(result - avg, 2);
        }

        return Math.sqrt(standardDeviation/resultList.size());
    }

}
