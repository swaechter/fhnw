package ch.fhnw.edu.rental.persistence.impl;

import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.persistence.PriceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class JpaPriceCategoryRepository extends JpaBaseRepository<PriceCategory> implements PriceCategoryRepository {

    @Autowired
    public JpaPriceCategoryRepository(EntityManager entityManager) {
        super(entityManager, PriceCategory.class);
    }
}
