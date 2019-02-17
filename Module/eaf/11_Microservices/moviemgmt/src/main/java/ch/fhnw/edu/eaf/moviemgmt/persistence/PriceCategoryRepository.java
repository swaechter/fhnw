package ch.fhnw.edu.eaf.moviemgmt.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.fhnw.edu.eaf.moviemgmt.domain.PriceCategory;

public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long> {
}
