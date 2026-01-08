package es.jastxz.microPrimeNumbers.model;

import java.util.List;

import lombok.Data;

@Data
public class ChartResponse {
    private List<Point> primesPoints;
    private List<PointDouble> gaussPoints;
    private List<PointDouble> aproxPoints;
}
