package es.jastxz.microPrimeNumbers.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.jastxz.microPrimeNumbers.model.DiffResponse;
import es.jastxz.microPrimeNumbers.model.InverseConstantResponse;
import es.jastxz.microPrimeNumbers.model.Point;
import es.jastxz.microPrimeNumbers.model.PointDouble;
import es.jastxz.microPrimeNumbers.model.PolarResponse;
import es.jastxz.microPrimeNumbers.service.PrimesService;
import es.jastxz.microPrimeNumbers.model.ChartResponse;
import es.jastxz.microPrimeNumbers.util.AproximacionInverso;
import es.jastxz.microPrimeNumbers.util.PrecisionMath;
import es.jastxz.microPrimeNumbers.util.Util;

@Service
public class PrimesServiceImpl implements PrimesService {
    @Override
    public DiffResponse primeDiff(int number) {
        List<Integer> primos = Util.cribaEratostenes(number);
        List<Integer> diferencias = Util.pairwiseDifferences(primos);
        String mensaje = Util.formatMap(Util.frequencyCount(diferencias), ";");
        DiffResponse res = new DiffResponse(primos, diferencias, mensaje);
        return res;
    }

    @Override
    public PolarResponse polarCoordenates(int number) {
        List<Point> puntos = Util.lista2Puntos(Util.cribaEratostenes(number));
        PolarResponse res = new PolarResponse(puntos);
        return res;
    }

    @Override
    public ChartResponse chart(int number) {
        List<Integer> primos = Util.cribaEratostenes(number * 10);
        List<Point> puntosPrimos = new ArrayList<>();
        List<PointDouble> gaussRiemann = new ArrayList<>();
        List<PointDouble> aproxJavier = new ArrayList<>();

        for (int i = 0; i < primos.size(); i++) {
            puntosPrimos.add(new Point(i, primos.get(i)));
            if (i == number) {
                break;
            }
        }

        for (int i = 1; i <= number; i++) {
            PointDouble p = new PointDouble(i, Util.gaussRiemann(i));
            PointDouble q = new PointDouble(i, Util.aproxJavier(i));
            gaussRiemann.add(p);
            aproxJavier.add(q);
        }

        ChartResponse res = new ChartResponse();
        res.setAproxPoints(aproxJavier);
        res.setGaussPoints(gaussRiemann);
        res.setPrimesPoints(puntosPrimos);
        return res;
    }

    @Override
    public InverseConstantResponse analyzeInverseConstant(int number) {
        // Use the util class logic
        // Verify number size to prevent timeout
        int validatedN = (number > 2000) ? 2000 : number;

        var primes = AproximacionInverso.getPrimes(validatedN);
        var An = AproximacionInverso.calculateAn(primes);
        var Bn = AproximacionInverso.calculateBn(An);

        // Cn Approximation (Optimal Series for up to 10 terms)
        // 3*sqrt(2)
        // - sqrt(229)/10^5
        // - sqrt(71)/10^6
        // + sqrt(59)/10^8
        // - sqrt(103)/10^10
        Double CnVal = 3 * Math.sqrt(2)
                - Math.sqrt(229) / 100000
                - Math.sqrt(71) / 1000000
                + Math.sqrt(59) / 100000000
                - Math.sqrt(103) / 10000000000.0;

        double nLogN = validatedN * Math.log(validatedN);
        var ratio = Bn.divide(new java.math.BigDecimal(nLogN, PrecisionMath.MC), PrecisionMath.MC);

        return InverseConstantResponse.builder()
                .n(validatedN)
                .An(An.toString().substring(0, Math.min(An.toString().length(), 50)) + "...")
                .Bn(Bn.toString().substring(0, Math.min(Bn.toString().length(), 50)) + "...")
                .Cn(String.valueOf(CnVal))
                .difference(String.valueOf(Bn.doubleValue() - CnVal))
                .ratioConvergence(ratio.doubleValue())
                .build();
    }
}
