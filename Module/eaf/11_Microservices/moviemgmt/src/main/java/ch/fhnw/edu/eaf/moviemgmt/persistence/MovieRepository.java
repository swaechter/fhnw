package ch.fhnw.edu.eaf.moviemgmt.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.fhnw.edu.eaf.moviemgmt.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
