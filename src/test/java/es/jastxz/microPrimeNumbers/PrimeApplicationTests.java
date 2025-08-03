package es.jastxz.microPrimeNumbers;

import es.jastxz.microPrimeNumbers.util.Util;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@SpringBootTest
@SpringJUnitConfig
public class PrimeApplicationTests {

    private static final int[] TEST_NUMBERS = {
        10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000
    };
    
    private static final int WARMUP_ITERATIONS = 5;
    private static final int MEASUREMENT_ITERATIONS = 10;
    private static final int CONCURRENT_USERS = 10;

    public static void main(String[] args) {
        PrimeApplicationTests test = new PrimeApplicationTests();
        test.runTests();
    }

    public void runTests() {
        warmup();
        testSequentialPerformance();
        warmup();
        testConcurrentLoad();
        warmup();
        testStressLimit();
        warmup();
        testMemoryUsage();
    }

    @BeforeEach
    void warmup() {
        // Calentamiento de JVM
        System.out.println("Calentando JVM...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            Util.cribaEratostenes(1000);
        }
        System.gc(); // Sugerir limpieza de memoria
    }

    @Test
    @DisplayName("Test de rendimiento secuencial - Criba de Eratóstenes")
    void testSequentialPerformance() {
        System.out.println("\n=== TEST SECUENCIAL ===");
        System.out.printf("%-10s %-15s %-15s %-15s %-10s%n", 
            "Número", "Tiempo (ms)", "Tiempo Prom", "Primos", "MB/s");
        System.out.println("-".repeat(75));

        for (int number : TEST_NUMBERS) {
            PerformanceResult result = measureCribaPerformance(number);
            printResult(number, result);
            
            // Parar si supera límite razonable (ej: 1 segundo)
            if (result.averageTimeMs > 1000) {
                System.out.println("⚠️ Límite de tiempo excedido en: " + number);
                break;
            }
        }
    }

    @Test
    @DisplayName("Test de carga concurrente")
    void testConcurrentLoad() {
        System.out.println("\n=== TEST CONCURRENTE ===");
        
        int testNumber = 500000; // Número fijo para test concurrente
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENT_USERS);
        
        long startTime = System.nanoTime();
        
        // Lanzar múltiples requests concurrentes
        CompletableFuture<Void>[] futures = IntStream.range(0, CONCURRENT_USERS)
            .mapToObj(i -> CompletableFuture.runAsync(() -> {
                long threadStart = System.nanoTime();
                Util.cribaEratostenes(testNumber);
                long threadEnd = System.nanoTime();
                double threadTimeMs = (threadEnd - threadStart) / 1_000_000.0;
                System.out.printf("Hilo %d: %.2f ms%n", i, threadTimeMs);
            }, executor))
            .toArray(CompletableFuture[]::new);
        
        CompletableFuture.allOf(futures).join();
        
        long endTime = System.nanoTime();
        double totalTimeMs = (endTime - startTime) / 1_000_000.0;
        double throughput = CONCURRENT_USERS / (totalTimeMs / 1000.0);
        
        System.out.printf("Tiempo total: %.2f ms%n", totalTimeMs);
        System.out.printf("Throughput: %.2f requests/segundo%n", throughput);
        
        executor.shutdown();
    }

    @Test
    @DisplayName("Test de estrés - Encontrar límite máximo")
    void testStressLimit() {
        System.out.println("\n=== TEST DE ESTRÉS ===");
        
        int maxAcceptableTimeMs = 500; // 500ms máximo aceptable
        int currentNumber = 100;
        
        while (true) {
            PerformanceResult result = measureCribaPerformance(currentNumber);
            
            System.out.printf("Número: %d, Tiempo: %.2f ms%n", 
                currentNumber, result.averageTimeMs);
            
            if (result.averageTimeMs > maxAcceptableTimeMs) {
                System.out.printf("🚨 LÍMITE ENCONTRADO: %d (%.2f ms)%n", 
                    currentNumber, result.averageTimeMs);
                System.out.printf("💡 LÍMITE RECOMENDADO: <%d%n", currentNumber);
                break;
            }
            
            currentNumber += currentNumber;
            
            // Límite de seguridad
            if (currentNumber > 10000000) {
                System.out.println("🛑 Límite de seguridad alcanzado");
                break;
            }
        }
    }

    @Test
    @DisplayName("Test de memoria")
    void testMemoryUsage() {
        System.out.println("\n=== TEST DE MEMORIA ===");
        
        Runtime runtime = Runtime.getRuntime();
        
        for (int number : TEST_NUMBERS) {
            // Limpiar memoria antes del test
            System.gc();
            Thread.yield();
            
            long memBefore = runtime.totalMemory() - runtime.freeMemory();
            
            List<Integer> primos = Util.cribaEratostenes(number);
            
            long memAfter = runtime.totalMemory() - runtime.freeMemory();
            long memUsed = memAfter - memBefore;
            
            System.out.printf("N=%d: Primos=%d, Memoria=%.2f MB%n", 
                number, primos.size(), memUsed / (1024.0 * 1024.0));
        }
    }

    // === MÉTODOS AUXILIARES ===

    private PerformanceResult measureCribaPerformance(int number) {
        long totalTime = 0;
        int primosCount = 0;
        
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            List<Integer> primos = Util.cribaEratostenes(number);
            long end = System.nanoTime();
            
            totalTime += (end - start);
            primosCount = primos.size();
        }
        
        double averageTimeMs = (totalTime / MEASUREMENT_ITERATIONS) / 1_000_000.0;
        double throughputMBps = (number * 4.0 / 1024.0 / 1024.0) / (averageTimeMs / 1000.0);
        
        return new PerformanceResult(averageTimeMs, primosCount, throughputMBps);
    }

    private void printResult(int number, PerformanceResult result) {
        String status = result.averageTimeMs < 100 ? "✅" : 
                       result.averageTimeMs < 500 ? "⚠️" : "🚨";
        
        System.out.printf("%-10d %-15.2f %-15.2f %-15d %-10.2f %s%n",
            number, result.averageTimeMs, result.averageTimeMs, 
            result.primosCount, result.throughputMBps, status);
    }

    // Clase auxiliar para resultados
    private static class PerformanceResult {
        final double averageTimeMs;
        final int primosCount;
        final double throughputMBps;
        
        PerformanceResult(double averageTimeMs, int primosCount, double throughputMBps) {
            this.averageTimeMs = averageTimeMs;
            this.primosCount = primosCount;
            this.throughputMBps = throughputMBps;
        }
    }
}