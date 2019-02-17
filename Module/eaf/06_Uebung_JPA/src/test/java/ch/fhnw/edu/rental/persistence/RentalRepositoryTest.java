package ch.fhnw.edu.rental.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Rental;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={"gui=false"})
@Transactional
public class RentalRepositoryTest {
	
	@Autowired
	private RentalRepository repo;
	
	@Test
	public void testCount() {
		assertEquals("expected 3 rentals in the rental repo", 3, repo.count());
	}
	
	@Test
	public void testExists() {
		assertTrue("rental with id 3 should exist in repo", repo.existsById(3L));
		assertFalse("rental with id 33 should not exist in repo", repo.existsById(33L));
	}
	
	@Test
	public void testDeleteId1() {
		repo.deleteById(3L);
		assertEquals("expected 2 rentals after deleting rental with id 3", 2, repo.count());
	}
	
	@Test
	public void testDeleteId2() {
		try {
			repo.deleteById(11L);
			fail("expected an exception when deleting a non-existing entity");
		} catch(Exception e) {
			// OK
		}
	}
	
	@Test
	public void findAll() {
		List<Rental> list = repo.findAll();
		assertEquals("expected to load 3 rentals", 3, list.size());
		assertTrue(list.contains(repo.findById(1L).get()));
		assertTrue(list.contains(repo.findById(2L).get()));
		assertTrue(list.contains(repo.findById(3L).get()));
	}
	
}
