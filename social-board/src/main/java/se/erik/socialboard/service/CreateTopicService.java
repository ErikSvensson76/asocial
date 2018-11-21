package se.erik.socialboard.service;

import se.erik.socialboard.entity.Topic;

public interface CreateTopicService {

	Topic createNewTopic(String topicName, long categoryId, long topicCreatorId);

}