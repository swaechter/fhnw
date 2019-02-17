package ch.fhnw.edu.rental.persistence.impl;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaMovieRepository extends JpaBaseRepository<Movie> implements MovieRepository {

    @Autowired
    public JpaMovieRepository(EntityManager entityManager) {
        super(entityManager, Movie.class);
    }

    @Override
    public List<Movie> findByTitle(String title) {
        TypedQuery<Movie> query = getEm().createQuery("SELECT movie FROM Movie movie WHERE movie.title = :title", Movie.class);
        query.setParameter("title", title);
        return query.getResultList();
    }
}
