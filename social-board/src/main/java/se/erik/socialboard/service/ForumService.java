package se.erik.socialboard.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;

public interface ForumService {

	Topic saveTopic(Topic topic);

	Post savePost(Post post);

	Category save(Category category);

	Optional<Post> findPostById(long postId);

	Optional<Topic> findTopicById(long topicId);

	List<Topic> findAllTopics();

	List<Post> findPostsByTopicId(long topicId);

	List<Topic> findTopicsByCategoryId(long categoryId);

	List<Topic> findTopicsByCreatorAlias(String alias);

	List<Topic> findTopicsByTopicName(String topicName);
	
	List<Topic> asyncFindTopicsByTopicName(String topicName);

	List<Post> findPostsByAuthorAlias(String authorAlias);

	Optional<Category> findById(long id);
	
	Category asyncFindById(long id);

	List<Category> findByCategoryNameLike(String categoryName);
	
	List<Category> asyncFindByCategoryNameLike(String categoryName);

	List<Category> findAllCategories();
	
	List<Category> asyncFindAllCategories();

	Topic asyncFindTopicById(long topicId);

}