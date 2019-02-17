package ch.fhnw.edu.rental.persistence.memory;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.MovieRepository;
import ch.fhnw.edu.rental.persistence.RentalRepository;
import ch.fhnw.edu.rental.persistence.UserRepository;

//@Component
public class RentalRepositoryImpl implements RentalRepository {
	private Map<Long, Rental> data = new HashMap<Long, Rental>();
	private long nextId = 1;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private MovieRepository movieRepo;

	@SuppressWarnings("unused")
	private void initData () {
		data.clear();
		nextId = 1;
		save(new Rental(userRepo.findById(1L).get(), movieRepo.findById(1L).get(), 14, LocalDate.of(2018, Month.SEPTEMBER, 23)));
		save(new Rental(userRepo.findById(1L).get(), movieRepo.findById(2L).get(), 14, LocalDate.of(2018, Month.SEPTEMBER, 25)));
		save(new Rental(userRepo.findById(3L).get(), movieRepo.findById(3L).get(), 14, LocalDate.of(2018, Month.SEPTEMBER, 27)));
	}

	@Override
	public Optional<Rental> findById(Long id) {
		if(id == null) throw new IllegalArgumentException();
		return Optional.ofNullable(data.get(id));
	}

	@Override
	public List<Rental> findAll() {
		return new ArrayList<Rental>(data.values());
	}

	@Override
	public List<Rental> findByUser(User user) {
		List<Rental> res = new ArrayList<Rental>();
		for(Rental r : data.values()){
			if(r.getUser().equals(user)) res.add(r);
		}
		return res;
	}

	@Override
	public Rental save(Rental rental) {
		if (rental.getId() == null)
			rental.setId(nextId++);
		data.put(rental.getId(), rental);
		return rental;
	}

	@Override
	public void delete(Rental rental) {
		if(rental == null) throw new IllegalArgumentException();
		data.remove(rental.getId());
		rental.setId(null);
	}

	@Override
	public void deleteById(Long id) {
		if(id == null) throw new IllegalArgumentException();
		findById(id).ifPresent(e -> delete(e));
	}

	@Override
	public boolean existsById(Long id) {
		if(id == null) throw new IllegalArgumentException();
		return data.get(id) != null;
	}

	@Override
	public long count() {
		return data.size();
	}

}
