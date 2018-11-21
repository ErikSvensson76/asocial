package se.erik.socialboard.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.erik.socialboard.data.CategoryRepository;
import se.erik.socialboard.data.PostRepository;
import se.erik.socialboard.data.TopicRepository;
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;

@Service
@Transactional
public class ForumServiceImpl implements ForumService {
	
	private TopicRepository topicRepo;
	private PostRepository postRepo;
	private CategoryRepository categoryRepo;
	
	@Autowired	
	public ForumServiceImpl(TopicRepository topicRepo, PostRepository postRepo, CategoryRepository categoryRepo) {
		this.topicRepo = topicRepo;
		this.postRepo = postRepo;
		this.categoryRepo = categoryRepo;
	}

	@Override
	public Topic saveTopic(Topic topic) {
		return topicRepo.save(topic);
	}
	
	@Override
	public Post savePost(Post post) {
		return postRepo.save(post);
	}
	
	@Override
	public Category save(Category category) {
		return categoryRepo.save(category);
	}
	
	@Override
	public Optional<Post> findPostById(long postId){
		return postRepo.findById(postId);
	}
	
	@Override
	public Topic asyncFindTopicById(long topicId){
		try {
			return topicRepo.findTopicById(topicId).get();
		}catch(InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;		
	}
	
	@Override
	public Optional<Topic> findTopicById(long topicId) {
		return topicRepo.findById(topicId);
	}	

	@Override
	public List<Topic> findAllTopics(){
		try {
			return topicRepo.findAllTopics().get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Post> findPostsByTopicId(long topicId){
		try {
			return postRepo.findPostsByTopicId(topicId).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}		
		return null;				
	}
	
	@Override
	public List<Topic> findTopicsByCategoryId(long categoryId){
		try {
			return topicRepo.findTopicsByCategoryId(categoryId).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Topic> findTopicsByCreatorAlias(String alias){
		try {
			return topicRepo.findByCreatorAlias(alias).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Topic> findTopicsByTopicName(String topicName){
		return topicRepo.findByTopicName(topicName);
	}
	
	@Override
	public List<Post> findPostsByAuthorAlias(String authorAlias){
		try {
			return postRepo.findByAuthorAlias(authorAlias).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Optional<Category> findById(long id) {
		return categoryRepo.findById(id);
	}
		
	@Override
	public Category asyncFindById(long id) {
		try {
			return categoryRepo.eagerfindCategoryById(id).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Category> findByCategoryNameLike(String categoryName){
		return categoryRepo.findByCategoryNameLike(categoryName);
	}
		
	@Override
	public List<Category> findAllCategories(){
		return categoryRepo.findAllCategories();					
	}
	
	@Override
	public List<Category> asyncFindAllCategories() {
		try {
			return categoryRepo.asyncFindAllCategories().get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}	

	@Override
	public List<Topic> asyncFindTopicsByTopicName(String topicName) {
		try {
			return topicRepo.asyncFindByTopicName(topicName).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Category> asyncFindByCategoryNameLike(String categoryName) {
		try {
			return categoryRepo.asyncFindByCategoryNameLike(categoryName).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

		
}
