package ch.fhnw.edu.rental.persistence.jdbc;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcRentalRepository implements RentalRepository {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private JdbcUserRepository userRepository;

    @Autowired
    private JdbcMovieRepository movieRepository;

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Rental> findByUser(User user) {
        String sql = "SELECT * FROM RENTALS WHERE USER_ID = ?";
        return template.query(sql, (rs, row) -> createRentals(rs, user), user.getId());
    }

    @Override
    public Optional<Rental> findById(Long aLong) {
        String sql = "SELECT * FROM RENTALS WHERE RENTAL_ID = ? LIMIT 1";
        return Optional.of(template.queryForObject(sql, new Object[]{aLong}, (rs, row) -> createRentals(rs)));
    }

    @Override
    public List<Rental> findAll() {
        String sql = "SELECT * FROM RENTALS";
        return template.query(sql, (rs, row) -> createRentals(rs));
    }

    @Override
    public Rental save(Rental rental) {
        if (rental.getId() == null) {
            String sql = "INSERT INTO RENTALS(RENTAL_RENTALDATE, RENTAL_RENTALDAYS, USER_ID, MOVIE_ID) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                final PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, dateTimeFormatter.format(rental.getRentalDate()));
                statement.setInt(2, rental.getRentalDays());
                statement.setLong(3, rental.getUser().getId());
                statement.setLong(4, rental.getMovie().getId());
                return statement;
            }, keyHolder);
            rental.setId(keyHolder.getKey().longValue());
        } else {
            String sql = "UPDATE RENTALS SET RENTAL_RENTALDATE = ?, RENTAL_RENTALDAYS = ?, USER_ID = ?, MOVIE_ID = ?";
            template.update(sql, new Object[]{dateTimeFormatter.format(rental.getRentalDate()), rental.getRentalDays(), rental.getUser().getId(), rental.getMovie().getId()});
        }
        return rental;
    }

    @Override
    public void deleteById(Long aLong) {
        String sql = "DELETE FROM RENTALS WHERE RENTAL_ID = ?";
        template.update(sql, new Object[]{aLong});
    }

    @Override
    public void delete(Rental entity) {
        deleteById(entity.getId());
        entity.setId(null);
    }

    @Override
    public boolean existsById(Long aLong) {
        // It's a bad idea to use count(*) because it can result in a full table scan. Instead use the queryForObject exception handling
        try {
            findById(aLong);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM RENTALS";
        return template.queryForObject(sql, Long.class);
    }

    private Rental createRentals(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("USER_ID");
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new IllegalStateException("Unable to find the user in the database - the reference integrity was violated!");
        }
        //return createRentals(resultSet, user.get());
        for (Rental rental : user.get().getRentals()) {
            if (rental.getId().equals(id)) {
                return rental;
            }
        }
        throw new RuntimeException("Inconsistent user");
    }

    private Rental createRentals(ResultSet resultSet, User user) throws SQLException {
        Long id = resultSet.getLong("RENTAL_ID");
        Rental rental = new Rental();
        rental.setId(id);

        Optional<Movie> movie = movieRepository.findById(resultSet.getLong("MOVIE_ID"));
        if (!movie.isPresent()) {
            throw new IllegalStateException("Unable to find the movie in the database - the reference integrity was violated!");
        }

        rental.setRentalDays(resultSet.getInt("RENTAL_RENTALDAYS"));
        rental.setUser(user);
        rental.setMovie(movie.get());
        return rental;
    }
}
