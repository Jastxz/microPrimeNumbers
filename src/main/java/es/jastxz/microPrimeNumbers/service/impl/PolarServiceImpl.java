package es.jastxz.microPrimeNumbers.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import es.jastxz.microPrimeNumbers.model.Point;
import es.jastxz.microPrimeNumbers.model.PolarResponse;
import es.jastxz.microPrimeNumbers.service.PolarService;
import es.jastxz.microPrimeNumbers.util.Util;

@Service
public class PolarServiceImpl implements PolarService {

    public PolarResponse polarCoordenates(int number) {
        List<Point> puntos = Util.lista2Puntos(Util.cribaEratostenes(number));
        PolarResponse res = new PolarResponse(puntos);
        return res;
    }
}
