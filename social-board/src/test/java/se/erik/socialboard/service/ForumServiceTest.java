package se.erik.socialboard.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ForumServiceTest {

	@TestConfiguration
	public static class ForumServiceTestConfig {
		@Bean
		public ForumService forumService(TopicRepository topicRepo, PostRepository postRepo, CategoryRepository categoryRepo) {
			return new ForumServiceImpl(topicRepo,postRepo,categoryRepo);
		}
	}
	
	@Autowired
	private ForumService forumService;
	@MockBean
	private TopicRepository topicRepo;
	@MockBean
	private PostRepository postRepo;
	@MockBean
	private CategoryRepository categoryRepo;
	
	private Category testCategory;
	private Topic testTopic;
	private Post testPost;
	
	@Before
	public void setup() {
		testCategory = new Category("Test category", "Test category description");
		testTopic = new Topic(LocalDateTime.parse("2018-01-01T10:30"), "Test topic");
		User user = new User("test@test.com", "Test", "Testsson","Testy", LocalDate.now());
		testPost = new Post(user.getAlias());
		testCategory.setCreator(user);
		testTopic.setTopicCreator(user);
		testTopic.setCategory(testCategory);
		testPost.setAuthor(user);
		testPost.setTextContent("Test Post Content");
		testPost.setTopic(testTopic);
	}
	
	@Test
	public void testSaveTopic() {
		when(topicRepo.save(testTopic)).thenReturn(testTopic);
		assertEquals(testTopic, forumService.saveTopic(testTopic));
	}
	
	@Test
	public void testSavePost() {
		when(postRepo.save(testPost)).thenReturn(testPost);
		assertEquals(testPost, forumService.savePost(testPost));
	}
	
	@Test
	public void test_save_testCategory() {
		when(categoryRepo.save(testCategory)).thenReturn(testCategory);
		assertEquals(testCategory, forumService.save(testCategory));
	}
	
	@Test
	public void testFindPostById_return_testPost() {
		when(postRepo.findById(anyLong())).thenReturn(Optional.of(testPost));
		assertEquals(Optional.of(testPost), forumService.findPostById(anyLong()));
	}
	
	@Test
	public void testAsyncFindTopicById_return_testTopic() {
		when(topicRepo.findTopicById(anyLong())).thenReturn(CompletableFuture.completedFuture(testTopic));
		assertEquals(testTopic, forumService.asyncFindTopicById(anyLong()));
	}
	
	@Test
	public void testFindTopicById_return_testTopic() {
		when(topicRepo.findById(anyLong())).thenReturn(Optional.of(testTopic));
		assertEquals(testTopic, forumService.findTopicById(anyLong()).get());
	}
	
	@Test
	public void testFindAllTopics() {
		when(topicRepo.findAllTopics()).thenReturn(CompletableFuture.completedFuture(Arrays.asList(testTopic)));
		assertEquals(Arrays.asList(testTopic), forumService.findAllTopics());
	}
	
	@Test
	public void testFindPostsByTopicId_return_list_of_testPost() {
		when(postRepo.findPostsByTopicId(anyLong())).thenReturn(CompletableFuture.completedFuture(Arrays.asList(testPost)));
		assertEquals(Arrays.asList(testPost), forumService.findPostsByTopicId(anyLong()));
	}
	
	@Test
	public void testFindTopicsByCategoryId_return_list_of_testTopic() {
		when(topicRepo.findTopicsByCategoryId(anyLong())).thenReturn(CompletableFuture.completedFuture(Arrays.asList(testTopic)));
		assertEquals(Arrays.asList(testTopic), forumService.findTopicsByCategoryId(anyLong()));
	}
	
	@Test
	public void testFindTopicsByCreatorAlias_return_list_of_testTopic() {
		when(topicRepo.findByCreatorAlias("Testy")).thenReturn(CompletableFuture.completedFuture(Arrays.asList(testTopic)));
		assertEquals(Arrays.asList(testTopic), forumService.findTopicsByCreatorAlias("Testy"));
	}
	
	@Test
	public void testFindTopicsByTopicName_return_list_of_testTopic() {
		when(topicRepo.findByTopicName("Test topic")).thenReturn(Arrays.asList(testTopic));
		assertEquals(Arrays.asList(testTopic), forumService.findTopicsByTopicName("Test topic"));
	}
	
	
	
	
	
	
	
	
}
