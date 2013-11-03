package junit.org.rapidpm.demo.jaxenter.blog0006;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Created by Sven Ruppert on 29.10.13.
 */
public class BubbleChartParallelDemo extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("MT Bubble Chart Sample");
        final NumberAxis xAxis = new NumberAxis(2.0, 15.0, 1);
        final NumberAxis yAxis = new NumberAxis(2.0, 15.0, 1);
        final BubbleChart<Number,Number> blc = new BubbleChart<>(xAxis,yAxis);
        xAxis.setLabel("Week");
        yAxis.setLabel("Product Budget");
        blc.setTitle("Budget Monitoring");

        XYChart.Series<Number,Number> series1 = new XYChart.Series<>();
        series1.setName("Product 1");

        XYChart.Series<Number,Number> series2 = new XYChart.Series<>();
        series2.setName("Product 2");

        getValuesForSeries()
                .parallelStream()
                .map(v -> new XYChart.Data<Number, Number>(v.getAvg(), v.getMax()))
                .collect(Collectors.toList()).forEach(v-> series1.getData().add((XYChart.Data<Number, Number>) v));

        getValuesForSeries()
                .parallelStream()
                .map(v -> new XYChart.Data<Number, Number>(v.getAvg(), v.getMax()))
                .collect(Collectors.toList())
                .forEach(v-> series2.getData().add((XYChart.Data<Number, Number>)v));

        Scene scene  = new Scene(blc);
        blc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();
    }

    private List<ChartValue> getValuesForSeries() {
        final List<List<Integer>> demoValueMatrix = generateDemoValueMatrix();
        final List<ChartValue> collect = demoValueMatrix
                .parallelStream()
                .map(v -> {
                    final double avg = v.stream().mapToInt(i -> i).average().getAsDouble();
                    final double max = v.stream().mapToInt(i -> i).average().getAsDouble();

                    return new ChartValue(avg,max);
                })

                .collect(Collectors.toList());

        System.out.println("collect = " + collect);
        return collect;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static class ChartValue{
        public double avg;
        public double max;

        public double getAvg() {
            return avg;
        }

        public void setAvg(double avg) {
            this.avg = avg;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public ChartValue(double avg, double max) {
            this.avg = avg;
            this.max = max;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ChartValue{");
            sb.append("avg=").append(avg);
            sb.append(", max=").append(max);
            sb.append('}');
            return sb.toString();
        }
    }



    public List<List<Integer>> generateDemoValueMatrix(){
        return Stream
                .generate(this::generateDemoValues)
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<Integer> generateDemoValues(){
        final Random random = new Random();
        return Stream
                .generate(() -> random.nextInt(11))
                .limit(1000)
                .collect(Collectors.toList());
    }



}