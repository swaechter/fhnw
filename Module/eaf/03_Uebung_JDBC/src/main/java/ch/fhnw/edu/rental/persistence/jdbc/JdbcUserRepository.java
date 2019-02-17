package ch.fhnw.edu.rental.persistence.jdbc;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.UserRepository;
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
import java.util.List;
import java.util.Optional;

@Component
public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcRentalRepository rentalRepository;

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<User> findByLastName(String lastName) {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
        return template.query(sql, (rs, row) -> createUser(rs), lastName);
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        String sql = "SELECT * FROM USERS WHERE USER_FIRSTNAME = ?";
        return template.query(sql, (rs, row) -> createUser(rs), firstName);
    }

    @Override
    public List<User> findByEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE USER_EMAIL = ?";
        return template.query(sql, (rs, row) -> createUser(rs), email);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ? LIMIT 1";
        return Optional.of(template.queryForObject(sql, new Object[]{aLong}, (rs, row) -> createUser(rs)));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USERS";
        return template.query(sql, (rs, row) -> createUser(rs));
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            String sql = "INSERT INTO USERS(USER_EMAIL, USER_FIRSTNAME, USER_NAME) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                final PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getFirstName());
                statement.setString(3, user.getLastName());
                return statement;
            }, keyHolder);
            user.setId(keyHolder.getKey().longValue());
        } else {
            String sql = "UPDATE USERS SET USER_EMAIL = ?, USER_FIRSTNAME = ?, USER_NAME = ?";
            template.update(sql, new Object[]{user.getEmail(), user.getFirstName(), user.getLastName()});
        }
        return user;
    }

    @Override
    public void deleteById(Long aLong) {
        String sql = "DELETE FROM USERS WHERE USER_ID = ?";
        template.update(sql, new Object[]{aLong});
    }

    @Override
    public void delete(User entity) {
        for (Rental r : entity.getRentals()) {
            rentalRepository.delete(r);
        }
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
        String sql = "SELECT COUNT(*) FROM USERS";
        return template.queryForObject(sql, Long.class);
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("USER_ID");
        User user = new User();
        user.setId(id);

        user.setLastName(resultSet.getString("USER_NAME"));
        user.setFirstName(resultSet.getString("USER_FIRSTNAME"));
        user.setEmail(resultSet.getString("USER_EMAIL"));
        user.setRentals(rentalRepository.findByUser(user));
        return user;
    }
}
