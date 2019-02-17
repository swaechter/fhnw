package ch.fhnw.edu.eaf.rentalmgmt.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.fhnw.edu.eaf.rentalmgmt.domain.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
