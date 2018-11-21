package se.erik.socialboard.repository_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import se.erik.socialboard.data.UserRepository;
import se.erik.socialboard.entity.User;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	private List<User> testUsers;
	
	private User testUser;
	
	@Before
	public void setup() {
		List<User> testUsers = new ArrayList<>();
		testUser = new User("test@test.com", "Test", "Testsson", "Testa", LocalDate.parse("2018-01-01"));		
		testUsers.add(new User("test2@test.com", "Test2", "Testsson2", "Testb", LocalDate.parse("2018-02-02")));
		testUsers.add(new User("test3@test.com", "Test3", "Testsson3", "Testc", LocalDate.parse("2018-03-03")));
		testUsers.add(new User("test4@test.com", "Test4", "Testsson4", "Testd", LocalDate.parse("2018-04-04")));
		testUsers.add(testUser);
		this.testUsers = testUsers.stream()
				.peek(entityManager::persist)
				.collect(Collectors.toList());
		entityManager.flush();
	}
	
	@After
	public void tearDown() throws InterruptedException {
		userRepository.deleteAll();
		entityManager.flush();
			
	}
	
	@Test
	public void testFindByAliasLike() throws InterruptedException, ExecutionException {
		String query = "%est%";
		List<User> expected = testUsers;
		CompletableFuture<List<User>> result = userRepository.findByAliasLike(query);
		assertTrue(expected.containsAll(result.get()));		
	}
	
	@Test
	public void testFindByActiveTrue() throws InterruptedException, ExecutionException {
		CompletableFuture<List<User>> result = userRepository.findByActiveTrue();
		assertTrue(result.get().stream().allMatch(User::isActive));
	}
	
	@Test
	public void testFindByActiveFalse() throws InterruptedException, ExecutionException {
		testUser.setActive(false);
		entityManager.persist(testUser);
		entityManager.flush();
				
		CompletableFuture<List<User>> result = userRepository.findByActiveFalse();
		assertEquals(testUser, result.get().stream().findFirst().get());		
	}
	
	@Test
	public void testFindByLastNameLikeAndFirstNameLike() throws InterruptedException, ExecutionException {
		String query1 = "T%";
		String query2 = "%sso%";
		List<User> expected = testUsers;
		CompletableFuture<List<User>> result = userRepository.findByLastNameLikeAndFirstNameLike(query2, query1);
		assertTrue(expected.containsAll(result.get()));
	}
	
	@Test
	public void testAsyncFindById() throws InterruptedException, ExecutionException {
		User expected = testUser;
		long userId = expected.getId();
		CompletableFuture<User> result = userRepository.asyncFindById(userId);
		
		assertEquals(expected, result.get());		
	}
	
	@Test
	public void testFindByEmail() {
		User expected = testUser;
		String email = "test@test.com";
		Optional<User> result = userRepository.findByEmail(email);
		
		assertEquals(expected.getEmail(), result.get().getEmail());
	}

}
