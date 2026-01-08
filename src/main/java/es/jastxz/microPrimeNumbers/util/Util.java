package es.jastxz.microPrimeNumbers.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import es.jastxz.microPrimeNumbers.model.Point;

public class Util {

    public final static int MAX_NUMBER_LIMIT = 500000;

    // CribaEratostenes genera una lista de números primos hasta el límite dado n
    public static List<Integer> cribaEratostenes(int n) {
        if (n < 2) {
            return new ArrayList<>();
        }

        // Crear un slice de booleanos para marcar números como primos (true) o no
        // (false)
        HashMap<Integer, Boolean> isPrime = new HashMap<>();
        for (int i = 2; i <= n; i++) {
            isPrime.put(i, true);
        }

        // Aplicar la criba
        for (int p = 2; p * p <= n; p++) {
            if (isPrime.get(p)) {
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    isPrime.replace(multiple, false);
                }
            }
        }

        // Recopilar los números primos
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime.get(i)) {
                primes.add(i);
            }
        }

        return primes;
    }

    public static List<Point> lista2Puntos(List<Integer> lista) {
        return lista.stream().map(p -> new Point(p, p)).toList();
    }

    public static List<Integer> pairwiseDifferences(List<Integer> nums) {
        List<Integer> differences = new ArrayList<>();
        for (int i = 0; i < nums.size() - 1; i++) {
            differences.add(nums.get(i + 1) - nums.get(i));
        }
        return differences;
    }

    public static HashMap<Integer, Integer> frequencyCount(List<Integer> nums) {
        HashMap<Integer, Integer> frequency = new HashMap<>();
        nums.stream().forEach(n -> frequency.put(n, 0));
        for (Integer i : nums) {
            int val = frequency.get(i) + 1;
            frequency.replace(i, val);
        }
        return frequency;
    }

    public static String formatMap(HashMap<Integer, Integer> mapa, String separator) {
        // Obtener y ordenar las claves
        Set<Integer> keys = mapa.keySet();
        SortedSet<Integer> sortedKeys = new TreeSet<>();
        keys.stream().forEach(k -> sortedKeys.add(k));

        // Crear la representación ordenada como string
        String result = "";
        for (Integer sk : sortedKeys) {
            result += sk + ":" + mapa.get(sk) + separator;
        }
        return result;
    }

    public static double gaussRiemann(int n) {
        return n * Math.log(n);
    }

    public static double aproxJavier(int n) {
        return gaussRiemann(n) + n * Math.log(2) + Math.log(n) * Math.log(2);
    }

    public static String chart(int maxN) {
        try {
            // Crear el dataset con múltiples puntos
            XYSeries series = new XYSeries("gauss");
            XYSeries series2 = new XYSeries("primes");
            XYSeries series3 = new XYSeries("aprox");

            // Generar puntos desde 2 hasta maxN
            for (int n = 1; n <= maxN; n++) {
                double indicePrimo = (double) n;
                double primo = gaussRiemann(n);
                series.add(indicePrimo, primo);
                series3.add(n, aproxJavier(n));
            }

            List<Integer> nums = cribaEratostenes(maxN);
            for (int n = 1; n < nums.size(); n++) {
                double primo = nums.get(n);
                series2.add(n, primo);
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);
            dataset.addSeries(series2);
            dataset.addSeries(series3);

            // Crear el gráfico
            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Aproximación de Gauss-Riemann para Números Primos",
                    "Índice del Primo (n)",
                    "n * ln(n)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false);

            // Personalizar el gráfico
            chart.setBackgroundPaint(java.awt.Color.WHITE);
            XYPlot plot = chart.getXYPlot();
            plot.setBackgroundPaint(java.awt.Color.LIGHT_GRAY);
            plot.setDomainGridlinePaint(java.awt.Color.WHITE);
            plot.setRangeGridlinePaint(java.awt.Color.WHITE);

            // Guardar el gráfico como imagen PNG
            String outputPath = "src/main/resources/static/gauss_riemann_chart.png";
            File chartFile = new File(outputPath);
            ChartUtils.saveChartAsPNG(chartFile, chart, 800, 600);

            System.out.println("Gráfica generada exitosamente en: " + chartFile.getAbsolutePath());
            return chartFile.getAbsolutePath();

        } catch (Exception e) {
            System.err.println("Error al generar la gráfica: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
