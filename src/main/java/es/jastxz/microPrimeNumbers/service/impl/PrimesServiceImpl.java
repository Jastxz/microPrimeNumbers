package es.jastxz.microPrimeNumbers.service.impl;

import java.util.ArrayList;
import java.util.List;

import es.jastxz.microPrimeNumbers.model.DiffResponse;
import es.jastxz.microPrimeNumbers.model.Point;
import es.jastxz.microPrimeNumbers.model.PointDouble;
import es.jastxz.microPrimeNumbers.model.PolarResponse;
import es.jastxz.microPrimeNumbers.service.PrimesService;
import es.jastxz.microPrimeNumbers.model.ChartResponse;
import es.jastxz.microPrimeNumbers.util.Util;

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
        List<Point> puntosPrimos = Util.lista2Puntos(Util.cribaEratostenes(number));
        List<PointDouble> gaussRiemann = new ArrayList<>();
        List<PointDouble> aproxJavier = new ArrayList<>();

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
}
