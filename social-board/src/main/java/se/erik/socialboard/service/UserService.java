package se.erik.socialboard.service;

import java.util.List;
import java.util.Optional;

import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

public interface UserService {

	Optional<User> findById(long id);
	
	User asyncFindById(long id);

	List<Topic> findTopicsByUserId(long userId);

	List<Category> findCategoriesByUserId(long userId);

	List<Post> findPostsByUserId(long userId);

	Optional<User> findByEmail(String email);
	
	User asyncFindByEmail(String email);

	List<User> findByAliasLike(String alias);

	List<User> findByLastNameAndFirstName(String lastName, String firstName);

	List<User> findByActiveStatus(boolean active);

	User save(User user);

}