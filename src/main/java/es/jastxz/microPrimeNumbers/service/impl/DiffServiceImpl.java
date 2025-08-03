package es.jastxz.microPrimeNumbers.service.impl;

import java.util.List;

import es.jastxz.microPrimeNumbers.model.DiffResponse;
import es.jastxz.microPrimeNumbers.service.DiffService;
import es.jastxz.microPrimeNumbers.util.Util;

public class DiffServiceImpl implements DiffService {

    @Override
    public DiffResponse primeDiff(int number) {
        List<Integer> primos = Util.cribaEratostenes(number);
        List<Integer> diferencias = Util.pairwiseDifferences(primos);
        String mensaje = Util.formatMap(Util.frequencyCount(diferencias), ";\n");
        DiffResponse res = new DiffResponse(primos, diferencias, mensaje);
        return res;
    }

}
