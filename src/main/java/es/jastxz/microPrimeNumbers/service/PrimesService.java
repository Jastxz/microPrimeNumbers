package es.jastxz.microPrimeNumbers.service;

import es.jastxz.microPrimeNumbers.model.DiffResponse;
import es.jastxz.microPrimeNumbers.model.PolarResponse;

import org.springframework.stereotype.Service;

import es.jastxz.microPrimeNumbers.model.ChartResponse;

@Service
public interface PrimesService {
    public DiffResponse primeDiff(int number);

    public PolarResponse polarCoordenates(int number);

    public ChartResponse chart(int number);
}
