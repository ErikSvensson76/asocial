package se.erik.socialboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.erik.socialboard.data.CategoryRepository;
import se.erik.socialboard.data.PostRepository;
import se.erik.socialboard.data.TopicRepository;
import se.erik.socialboard.data.UserRepository;
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepo;
	private TopicRepository topicRepo;
	private CategoryRepository categoryRepo;
	private PostRepository postRepo;

	@Autowired
	public UserServiceImpl(UserRepository userRepo, TopicRepository topicRepo, CategoryRepository categoryRepo,
			PostRepository postRepo) {
		this.userRepo = userRepo;
		this.topicRepo = topicRepo;
		this.categoryRepo = categoryRepo;
		this.postRepo = postRepo;
	}

	@Override
	public Optional<User> findById(long id) {
		return userRepo.findById(id);
	}
		
	@Override
	public List<Topic> findTopicsByUserId(long userId){
		try {
			return topicRepo.findTopicsByUserId(userId).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Category> findCategoriesByUserId(long userId){
		
		try {
			return categoryRepo.findCategoriesByUserId(userId).get();
		}catch(InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	@Override
	public List<Post> findPostsByUserId(long userId){
		try {
			return postRepo.findPostsByUserId(userId).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Optional<User> findByEmail(String email){		
		return userRepo.findByEmail(email);
	}
	
	@Override
	public List<User> findByAliasLike(String alias){
		List<User> result = new ArrayList<>();
		
		try {
			return result = userRepo.findByAliasLike(alias).get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	@Override
	public List<User> findByLastNameAndFirstName(String lastName, String firstName){
		List<User> result = new ArrayList<>();
		try {
			result = userRepo.findByLastNameLikeAndFirstNameLike(lastName, firstName).get();
		}catch(InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
		 
	}
	
	@Override
	public List<User> findByActiveStatus(boolean active){
		try {
			return active ? userRepo.findByActiveTrue().get() : userRepo.findByActiveFalse().get();
		}catch(InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
		
	}
	
	@Override
	public User save(User user) {
		return userRepo.save(user);
	}

	@Override
	public User asyncFindById(long id) {
		try {
			return userRepo.asyncFindById(id).get();
		}catch(InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public User asyncFindByEmail(String email) {
		
		try {
			return userRepo.asyncFindByEmail(email).get();
		}catch(InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	

}
