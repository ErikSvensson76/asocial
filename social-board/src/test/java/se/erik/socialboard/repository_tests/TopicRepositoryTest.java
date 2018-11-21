package se.erik.socialboard.repository_tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import se.erik.socialboard.data.CategoryRepository;
import se.erik.socialboard.data.TopicRepository;
import se.erik.socialboard.data.UserRepository;
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest()
public class TopicRepositoryTest {
	
	@Autowired
	private TestEntityManager em;
	@Autowired
	private TopicRepository repo;
	@Autowired
	private CategoryRepository catRepo;
	@Autowired
	private UserRepository userRepo;
	
	private Topic testTopic;
	private long userId;
	private long categoryId;
	
	private List<Topic> testTopics = new ArrayList<>();
	
	@Before
	public void setup() {
		testTopic = new Topic(LocalDateTime.parse("2018-01-01T10:30"), "Topic 1" );
		User user = new User("test@test.com","test", "testsson", "testy", LocalDate.parse("2017-10-11"));
		Category category = new Category("Test category", "Test description");
		testTopic.setTopicCreator(user);
		testTopic.setCategory(category);
		
		Topic testTopic2 = new Topic(LocalDateTime.parse("2018-02-02T10:30"), "Topic 2" );
		testTopic2.setTopicCreator(user);
		testTopic2.setCategory(new Category("Test category2", "Test description2"));
		testTopics.add(testTopic);
		testTopics.add(testTopic2);
		
		testTopics.forEach(em::persist);
		em.flush();
		userId = user.getId();
		categoryId = category.getId();
		em.flush();
	}
	
	@After
	public void tearDown() {
		repo.deleteAll(testTopics);
		em.flush();
		catRepo.deleteAll();
		em.flush();
		userRepo.deleteAll();
		em.flush();		
	}
	
	@Test
	public void testFindTopicsByCategoryId() throws InterruptedException, ExecutionException {
		List<Topic> expected = Arrays.asList(testTopic);
		assertEquals(expected, repo.findTopicsByCategoryId(categoryId).get());
	}
	
	@Test
	public void testFindTopicsByUserId() throws InterruptedException, ExecutionException {
		List<Topic> expected = testTopics;
		assertEquals(expected, repo.findTopicsByUserId(userId).get());
	}
	
	@Test
	public void testFindAllTopics() throws InterruptedException, ExecutionException {
		long expectedNumberOfTopics = 2;
		Future<List<Topic>> result = repo.findAllTopics();
		assertEquals(expectedNumberOfTopics, result.get().size());
	}
	
	@Test
	public void testFindByCreatorAlias() throws InterruptedException, ExecutionException {
		String alias = "testy";
		Future<List<Topic>> result = repo.findByCreatorAlias(alias);
		assertTrue(result.get().stream().allMatch(topic->topic.getCreatorAlias().equalsIgnoreCase(alias)));
	}
	
	@Test
	public void testFindByTopicName() {
		String topicName = "Topic 1";
		List<Topic> actual =  repo.findByTopicName(topicName);
		assertTrue(actual.stream().allMatch(topic->topic.getTopicName().equalsIgnoreCase(topicName)));
	}
	
	@Test
	public void testAsyncFindByTopicName() throws InterruptedException, ExecutionException {
		String topicName = "Topic 2";
		Future<List<Topic>> future = repo.asyncFindByTopicName(topicName);
		assertTrue(future.get().stream().allMatch(topic -> topic.getTopicName().equalsIgnoreCase(topicName)));
	}
	
	@Test
	public void testFindTopicById() throws InterruptedException, ExecutionException {
		long topicId = testTopic.getId();
		Topic expected = testTopic;
		Future<Topic> future = repo.findTopicById(topicId);
		
		assertEquals(expected, future.get());
	}
	
	

}
