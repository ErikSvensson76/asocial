package se.erik.socialboard.data;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import se.erik.socialboard.entity.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long>{
	
	@Async
	@Query("SELECT top FROM Topic top WHERE top.category.id = :categoryId")
	Future<List<Topic>> findTopicsByCategoryId(@Param("categoryId") long categoryId);
	
	@Async
	@Query("SELECT top FROM Topic top WHERE top.topicCreator.id = :userId")
	Future<List<Topic>> findTopicsByUserId(@Param("userId") long userId);
	
	@Async
	@Query("SELECT top FROM Topic top")
	Future<List<Topic>> findAllTopics();
	
	@Async
	Future<List<Topic>> findByCreatorAlias(String creatorAlias);
	
	List<Topic> findByTopicName(String topicName);
	
	@Async
	@Query("SELECT top FROM Topic top WHERE top.topicName = :topicName")
	Future<List<Topic>> asyncFindByTopicName(@Param("topicName")String topicName);
	
	@Async	
	@Query("SELECT top FROM Topic top WHERE top.id = :topicId")
	Future<Topic> findTopicById(@Param("topicId") long id);

}
