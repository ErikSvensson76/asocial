package se.erik.socialboard.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

@Service
@Transactional
public class CreatePostServiceImpl implements CreatePostService {
	
	private ForumService forumService;
	private UserService userService;
	
	@Autowired
	public CreatePostServiceImpl(ForumService forumService, UserService userService) {
		this.forumService = forumService;
		this.userService = userService;
	}
	
	@Override
	public Post createNewPost(String textContent, long authorId, long topicId) throws NotFoundException{
		User author = userService.findById(authorId).orElse(null);
		Topic topic = forumService.findTopicById(topicId).orElse(null);
		
		if(author == null || topic == null)
			throw new NotFoundException("Either User or Topic could not be found in the database");
		
		Post newPost = new Post(author.getAlias());
		newPost.setAuthor(author);
		newPost.setTopic(topic);
		newPost.setTextContent(textContent);
		topic.setLatestActivity(newPost.getTimeStamp());
		
		return forumService.savePost(newPost);
	}	
}
