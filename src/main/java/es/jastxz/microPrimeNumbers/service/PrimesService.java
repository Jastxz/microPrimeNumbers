package es.jastxz.microPrimeNumbers.service;

import es.jastxz.microPrimeNumbers.model.DiffResponse;
import es.jastxz.microPrimeNumbers.model.InverseConstantResponse;
import es.jastxz.microPrimeNumbers.model.PolarResponse;

import es.jastxz.microPrimeNumbers.model.ChartResponse;

public interface PrimesService {
    public DiffResponse primeDiff(int number);

    public PolarResponse polarCoordenates(int number);

    public ChartResponse chart(int number);

    public InverseConstantResponse analyzeInverseConstant(int number);
}
