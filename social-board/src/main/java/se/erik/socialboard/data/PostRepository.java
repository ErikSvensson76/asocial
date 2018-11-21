package se.erik.socialboard.data;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import se.erik.socialboard.entity.Post;

public interface PostRepository extends CrudRepository<Post, Long>{
	
	@Async
	@Query("SELECT p FROM Post p WHERE p.topic.id= :topicId")
	Future<List<Post>> findPostsByTopicId(@Param("topicId") long topicId);
	
	@Async
	@Query("SELECT p FROM Post p WHERE p.author.id = :userId")
	Future<List<Post>> findPostsByUserId(@Param("userId") long userId);
	
	@Async
	Future<List<Post>> findByAuthorAlias(String authorAlias);

}
