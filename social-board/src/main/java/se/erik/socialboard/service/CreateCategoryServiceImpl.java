package se.erik.socialboard.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.User;

@Service
@Transactional
public class CreateCategoryServiceImpl implements CreateCategoryService {
	
	private ForumService forumService;
	private UserService userService;	
	
	@Autowired
	public CreateCategoryServiceImpl(ForumService forumService, UserService userService) {
		this.forumService = forumService;
		this.userService = userService;
	}

	@Override
	public Category createNewCategory(String categoryName, String description, long creatorId) {
		User creator = userService.findById(creatorId).orElse(null);
		if(creator == null) {
			return null;
		}
			
		Category newCategory = new Category(categoryName, description);
		newCategory.setCreator(creator);
		
		return forumService.save(newCategory);		
	}

}
