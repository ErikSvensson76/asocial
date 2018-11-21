package se.erik.socialboard.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import se.erik.socialboard.data.CategoryRepository;
import se.erik.socialboard.data.PostRepository;
import se.erik.socialboard.data.TopicRepository;
import se.erik.socialboard.data.UserRepository;
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	@TestConfiguration
	static class UserServiceTestConfig{
		@Bean
		public UserService userService(UserRepository userRepo, TopicRepository topicRepo, CategoryRepository categoryRepo,
				PostRepository postRepo) {
			return new UserServiceImpl(userRepo, topicRepo, categoryRepo, postRepo);
			
		}
	}
	
	@Autowired private UserService userService;
	
	@MockBean private UserRepository userRepo;
	@MockBean private TopicRepository topicRepo;
	@MockBean private CategoryRepository categoryRepo;
	@MockBean private PostRepository postRepo;
	
	private User testUser;
	private List<User> testUsers;
	private List<Topic> testTopics;
	private List<Category> testCategories;
	private List<Post> testPosts;
	
	@Before
	public void setup() {
		this.testUser = new User("test@test.com", "Test", "Testsson", "testy", LocalDate.parse("2018-01-01"));
		testUsers = new ArrayList<>();
		testUsers.add(testUser);
		testUsers.add(new User("test2@test.com", "Test2", "Testsson2", "testy2", LocalDate.now()));
				
		Category c1 = new Category("Test", "Test description");
		c1.setCreator(testUser);
		testCategories = new ArrayList<>();
		testCategories.add(c1);
		
		Topic t1 = new Topic(LocalDateTime.now(), "Test topic");
		t1.setTopicCreator(testUser);
		t1.setCategory(c1);
		testTopics = new ArrayList<>();
		testTopics.add(t1);
		
		Post p1 = new Post(testUser.getAlias());
		p1.setAuthor(testUser);
		p1.setTopic(t1);
		p1.setTextContent("Test content");
		testPosts = new ArrayList<>();
		testPosts.add(p1);				
	}
	
	@Test
	public void testFindById_returns_testUser() {
		when(userRepo.findById(anyLong())).thenReturn(Optional.of(testUser));		
		assertEquals(testUser, userService.findById(anyLong()).get());
	}
	
	@Test
	public void testAsyncFindById_returns_testUser() {
		when(userRepo.asyncFindById(anyLong())).thenReturn(CompletableFuture.completedFuture(testUser));
		assertEquals(testUser, userService.asyncFindById(anyLong()));
	}
	
	@Test
	public void testFindTopicsByUserId() {
		when(topicRepo.findTopicsByUserId(anyLong())).thenReturn(CompletableFuture.completedFuture(testTopics));
		assertTrue(userService.findTopicsByUserId(anyLong()).stream().allMatch(topic -> topic.getTopicCreator().equals(testUser)));
	}
	
	@Test
	public void testFindCategoriesByUserId() {
		when(categoryRepo.findCategoriesByUserId(anyLong())).thenReturn(CompletableFuture.completedFuture(testCategories));
		assertTrue(userService.findCategoriesByUserId(anyLong()).stream().allMatch(cat -> cat.getCreator().equals(testUser)));
	}
	
	@Test
	public void testFindPostsByUserId() {
		when(postRepo.findPostsByUserId(anyLong())).thenReturn(CompletableFuture.completedFuture(testPosts));
		assertTrue(userService.findPostsByUserId(anyLong()).stream().allMatch(post -> post.getAuthor().equals(testUser)));
	}
	
	@Test
	public void testFindByEmail_returns_testUser() {
		when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));
		assertEquals("test@test.com", userService.findByEmail("test@test.com").get().getEmail());
	}
	
	@Test
	public void testAsyncFindByEmail_returns_testUser() {
		when(userRepo.asyncFindByEmail("test@test.com")).thenReturn(CompletableFuture.completedFuture(testUser));
		assertEquals("test@test.com", userService.asyncFindByEmail("test@test.com").getEmail());
	}
	
	@Test
	public void testFindByAliasLike_returns_testUsers() {
		String query = "test%";
		when(userRepo.findByAliasLike(query)).thenReturn(CompletableFuture.completedFuture(testUsers));
		assertTrue(userService.findByAliasLike(query).stream().allMatch(user -> user.getEmail().contains("test")));
	}
	
	@Test
	public void testFindByLastNameAndFirstName() {
		String firstName = "Test", lastName = "Testsson";
		when(userRepo.findByLastNameLikeAndFirstNameLike(lastName, firstName)).thenReturn(CompletableFuture.completedFuture(Arrays.asList(testUser)));
		List<User> actual = userService.findByLastNameAndFirstName(lastName, firstName);
		assertTrue(actual.stream().map(User::getFullName).allMatch(name -> name.equalsIgnoreCase(firstName + " " + lastName)));
	}
	
	@Test
	public void findByActiveStatus_true() {
		when(userRepo.findByActiveTrue()).thenReturn(CompletableFuture.completedFuture(testUsers));
		assertTrue(userService.findByActiveStatus(true).stream().allMatch(User::isActive));
	}
	
	@Test
	public void findByActiveStatus_false() {
		User inactive = new User("Test3@test.com", "Test", "Testsson", "testy", LocalDate.now());
		inactive.setActive(false);
		when(userRepo.findByActiveFalse()).thenReturn(CompletableFuture.completedFuture(Arrays.asList(inactive)));
		assertFalse(userService.findByActiveStatus(false).stream().allMatch(User::isActive));
	}

}
