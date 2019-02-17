package ch.fhnw.edu.rental.persistence.jdbc;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.persistence.MovieRepository;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcMovieRepository implements MovieRepository {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private JdbcPriceCategoryRepository priceCategoryRepository;

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Movie> findByTitle(String title) {
        String sql = "SELECT * FROM MOVIES WHERE MOVIE_TITLE = ?";
        return template.query(sql, (rs, row) -> createMovie(rs), title);
    }

    @Override
    public Optional<Movie> findById(Long aLong) {
        String sql = "SELECT * FROM MOVIES WHERE MOVIE_ID = ? LIMIT 1";
        return Optional.of(template.queryForObject(sql, new Object[]{aLong}, (rs, row) -> createMovie(rs)));
    }

    @Override
    public List<Movie> findAll() {
        String sql = "SELECT * FROM MOVIES";
        return template.query(sql, (rs, row) -> createMovie(rs));
    }

    @Override
    public Movie save(Movie movie) {
        if (movie.getId() == null) {
            String sql = "INSERT INTO MOVIES(MOVIE_RELEASEDATE, MOVIE_TITLE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                final PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, dateTimeFormatter.format(movie.getReleaseDate()));
                statement.setString(2, movie.getTitle());
                statement.setBoolean(3, movie.isRented());
                statement.setLong(4, movie.getPriceCategory().getId());
                return statement;
            }, keyHolder);
            movie.setId(keyHolder.getKey().longValue());
        } else {
            String sql = "UPDATE MOVIES SET MOVIE_RELEASEDATE = ?, MOVIE_TITLE = ?, MOVIE_RENTED = ?, PRICECATEGORY_FK = ?";
            template.update(sql, new Object[]{dateTimeFormatter.format(movie.getReleaseDate()), movie.getTitle(), movie.isRented(), movie.getPriceCategory().getId()});
        }
        return movie;
    }

    @Override
    public void deleteById(Long aLong) {
        String sql = "DELETE FROM MOVIES WHERE MOVIE_ID = ?";
        template.update(sql, new Object[]{aLong});
    }

    @Override
    public void delete(Movie entity) {
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
        String sql = "SELECT COUNT(*) FROM MOVIES";
        return template.queryForObject(sql, Long.class);
    }

    private Movie createMovie(ResultSet resultSet) throws SQLException {
        Optional<PriceCategory> priceCategory = priceCategoryRepository.findById(resultSet.getLong("PRICECATEGORY_FK"));
        if (!priceCategory.isPresent()) {
            throw new IllegalStateException("Unable to find the price category in the database - the reference integrity was violated!");
        }

        Movie movie = new Movie(resultSet.getString("MOVIE_TITLE"), LocalDate.parse(resultSet.getString("MOVIE_RELEASEDATE"), dateTimeFormatter), priceCategory.get());
        movie.setId(resultSet.getLong("MOVIE_ID"));
        //movie.setRented(resultSet.getBoolean("MOVIE_RENTED")); Don't set the rented status because the Rental constructor does it

        return movie;
    }
}
