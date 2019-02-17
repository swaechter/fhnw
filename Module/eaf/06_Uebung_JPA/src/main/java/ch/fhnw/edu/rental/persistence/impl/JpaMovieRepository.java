package ch.fhnw.edu.rental.persistence.impl;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaMovieRepository extends JpaBaseRepository<Movie> implements MovieRepository {

    @Autowired
    public JpaMovieRepository(EntityManager entityManager) {
        super(entityManager, Movie.class);
    }

    @Override
    public List<Movie> findByTitle(String title) {
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
        Root<Movie> root = query.from(Movie.class);
        query.select(root).where(builder.equal(root.get("title"), title));
        return getEm().createQuery(query).getResultList();
    }
}
