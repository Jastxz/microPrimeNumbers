package es.jastxz.microPrimeNumbers.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import es.jastxz.microPrimeNumbers.model.UserRequest;

public interface UserRequestRepo extends JpaRepository<UserRequest, String>{
}
