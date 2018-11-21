package se.erik.socialboard.repository_tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import se.erik.socialboard.data.PostRepository;
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {
	
	@Autowired
	private TestEntityManager em;
	
	@Autowired
	private PostRepository postRepo;
	
	private Topic testTopic;
	private User testUser;
	private Category testCategory;	
	private List<Post> testPosts;
	
	@Before
	public void setup() {
		testCategory = new Category("Category", "Test category");
		testCategory = em.persistAndFlush(testCategory);
		
		testUser = new User("test@test.com", "Test", "Testsson", "testy", LocalDate.parse("2018-01-01"));
		testUser = em.persistAndFlush(testUser);	
		
		testTopic = new Topic(LocalDateTime.now(), "TestTopic");
		testTopic.setCategory(testCategory);
		testTopic.setTopicCreator(testUser);
		testTopic = em.persistAndFlush(testTopic);
		
		Post p1 = new Post(testUser.getAlias());
		p1.setAuthor(testUser);
		p1.setTextContent("Content 1");
		p1.setTopic(testTopic);
		Post p2 = new Post(testUser.getAlias());
		p2.setAuthor(testUser);
		p2.setTextContent("Content 2");
		p2.setTopic(testTopic);
		em.persist(p1);
		em.flush();
		em.persist(p2);
		em.flush();
		testPosts = new ArrayList<>();
		testPosts.add(p1);
		testPosts.add(p2);		
	}
	
	@After
	public void tearDown() {
		postRepo.deleteAll();
		em.flush();
		em.remove(testTopic);
		em.flush();
		em.remove(testUser);
		em.flush();
		em.remove(testCategory);
		em.flush();
	}
	
	@Test
	public void testFindPostsByTopicId() throws InterruptedException, ExecutionException {
		long topicId = testTopic.getId();
		Future<List<Post>> result = postRepo.findPostsByTopicId(topicId);
		assertTrue(result.get().containsAll(testPosts));
	}
	
	@Test
	public void testFindPostsByUserId() throws InterruptedException, ExecutionException {
		long userId = testUser.getId();
		Future<List<Post>> result = postRepo.findPostsByUserId(userId);
		assertTrue(result.get().containsAll(testPosts));
	}
	
	@Test
	public void testFindByAuthorAlias() throws InterruptedException, ExecutionException {
		String alias = testUser.getAlias();
		Future<List<Post>> result = postRepo.findByAuthorAlias(alias);
		assertTrue(result.get().stream().allMatch(post -> post.getAuthorAlias().equalsIgnoreCase(alias)));
	}	
}
