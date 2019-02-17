package ch.fhnw.edu.rental.persistence.jdbc;

import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.PriceCategoryChildren;
import ch.fhnw.edu.rental.model.PriceCategoryNewRelease;
import ch.fhnw.edu.rental.model.PriceCategoryRegular;
import ch.fhnw.edu.rental.persistence.PriceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPriceCategoryRepository implements PriceCategoryRepository {

    @Autowired
    private JdbcTemplate template;

    @Override
    public Optional<PriceCategory> findById(Long aLong) {
        String sql = "SELECT * FROM PRICECATEGORIES WHERE PRICECATEGORY_ID = ? LIMIT 1";
        return Optional.of(template.queryForObject(sql, new Object[]{aLong}, (rs, row) -> createPriceCategory(rs)));
    }

    @Override
    public List<PriceCategory> findAll() {
        String sql = "SELECT * FROM PRICECATEGORIES";
        return template.query(sql, (rs, row) -> createPriceCategory(rs));
    }

    @Override
    public PriceCategory save(PriceCategory priceCategory) {
        throw new UnsupportedOperationException("You are not allowed to save or create static database entries!");
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException("You are not allowed to delete static database entries!");
    }

    @Override
    public void delete(PriceCategory entity) {
        throw new UnsupportedOperationException("You are not allowed to delete static database entries!");

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
        String sql = "SELECT COUNT(*) FROM PRICECATEGORIES";
        return template.queryForObject(sql, Long.class);
    }

    private PriceCategory createPriceCategory(ResultSet resultSet) throws SQLException {
        PriceCategory priceCategory;

        switch (resultSet.getString("PRICECATEGORY_TYPE")) {
            case "Regular":
                priceCategory = new PriceCategoryRegular();
                break;
            case "Children":
                priceCategory = new PriceCategoryChildren();
                break;
            case "NewRelease":
                priceCategory = new PriceCategoryNewRelease();
                break;
            default:
                throw new InvalidParameterException("Invalid price category type!");
        }

        priceCategory.setId(resultSet.getLong("PRICECATEGORY_ID"));
        return priceCategory;
    }
}
