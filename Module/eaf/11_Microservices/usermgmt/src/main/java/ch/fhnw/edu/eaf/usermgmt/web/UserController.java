package ch.fhnw.edu.eaf.usermgmt.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.edu.eaf.usermgmt.domain.User;
import ch.fhnw.edu.eaf.usermgmt.persistence.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		Sort sort = new Sort(Direction.ASC, "id");
		List<User> users = userRepository.findAll(sort);
		log.debug("Found " + users.size() + " users");
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			log.debug("Found user with id=" + user.getId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user) {
		user = userRepository.save(user);
		log.debug("Created user with id=" + user.getId());
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@RequestBody User newUser, @PathVariable Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setEmail(newUser.getEmail());
			userRepository.save(user);
			log.debug("Updated user with id=" + user.getId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			userRepository.delete(user);
			log.debug("Deleted user with id=" + id);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

}
