package ch.fhnw.edu.rental.persistence.impl;

import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaUserRepository extends JpaBaseRepository<User> implements UserRepository {

    @Autowired
    public JpaUserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        TypedQuery<User> query = getEm().createQuery("SELECT user FROM User user WHERE user.lastName = :lastName", User.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        TypedQuery<User> query = getEm().createQuery("SELECT user FROM User user WHERE user.firstName = :firstName", User.class);
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }

    @Override
    public List<User> findByEmail(String email) {
        TypedQuery<User> query = getEm().createQuery("SELECT user FROM User user WHERE user.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
}
