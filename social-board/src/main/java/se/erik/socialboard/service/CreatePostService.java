package se.erik.socialboard.service;

import javassist.NotFoundException;
import se.erik.socialboard.entity.Post;

public interface CreatePostService {

	Post createNewPost(String textContent, long authorId, long topicId) throws NotFoundException;

}