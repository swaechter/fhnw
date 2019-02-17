package ch.fhnw.edu.rental.persistence.impl;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaRentalRepository extends JpaBaseRepository<Rental> implements RentalRepository {

    @Autowired
    public JpaRentalRepository(EntityManager entityManager) {
        super(entityManager, Rental.class);
    }

    @Override
    public List<Rental> findByUser(User user) {
        return user.getRentals();
    }
}
