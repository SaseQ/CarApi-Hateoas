package it.marczuk.pracadomowa_tydzien3.repository;

import it.marczuk.pracadomowa_tydzien3.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
}
