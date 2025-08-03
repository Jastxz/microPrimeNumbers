package es.jastxz.microPrimeNumbers.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import es.jastxz.microPrimeNumbers.model.GlobalRequest;

public interface GlobalRequestRepo extends JpaRepository<GlobalRequest, Long>{
}
