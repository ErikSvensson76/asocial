package se.erik.socialboard.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

@Service
@Transactional
public class CreateTopicServiceImpl implements CreateTopicService {
	
	private ForumService forumService;
	private UserService userService;
	
	@Autowired
	public CreateTopicServiceImpl(ForumService forumService, UserService userService) {
		this.forumService = forumService;
		this.userService = userService;
	}
	
	@Override
	public Topic createNewTopic(String topicName, long categoryId, long topicCreatorId) {
		User topicCreator = userService.findById(topicCreatorId).orElse(null);
		Category category = forumService.findById(categoryId).orElse(null);
		
		if(topicCreator == null || category == null)
			return null;
		
		Topic newTopic = new Topic(LocalDateTime.now(), topicName);
		newTopic.setCategory(category);
		newTopic.setTopicCreator(topicCreator);
		
		return forumService.saveTopic(newTopic);		
	}	
}
