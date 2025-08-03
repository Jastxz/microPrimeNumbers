package es.jastxz.microPrimeNumbers.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import es.jastxz.microPrimeNumbers.model.Point;

public class Util {

    public final static int MAX_NUMBER_LIMIT = 500000;

    // CribaEratostenes genera una lista de números primos hasta el límite dado n
    public static List<Integer> cribaEratostenes(int n) {
        if(n < 2) {
            return new ArrayList<>();
        }

        // Crear un slice de booleanos para marcar números como primos (true) o no (false)
        HashMap<Integer, Boolean> isPrime = new HashMap<>();
        for(int i = 2; i <= n; i++) {
            isPrime.put(i, true);
        }

        // Aplicar la criba
        for(int p = 2; p*p <= n; p++) {
            if(isPrime.get(p)) {
                for(int multiple = p * p; multiple <= n; multiple += p) {
                    isPrime.replace(multiple, false);
                }
            }
        }

        // Recopilar los números primos
        List<Integer> primes = new ArrayList<>();
        for(int i = 2; i <= n; i++) {
            if(isPrime.get(i)) {
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
        for(int i = 0; i < nums.size() - 1; i++) {
            differences.add(nums.get(i+1)-nums.get(i));
        }
        return differences;
    }

    public static HashMap<Integer, Integer> frequencyCount(List<Integer> nums) {
        HashMap<Integer, Integer> frequency = new HashMap<>();
        nums.stream().forEach(n -> frequency.put(n, 0));
        for (Integer i : nums) {
            int val = frequency.get(i)+1;
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
}
