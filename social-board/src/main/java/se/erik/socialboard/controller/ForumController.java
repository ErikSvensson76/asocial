package se.erik.socialboard.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;
import se.erik.socialboard.forms.CategoryForm;
import se.erik.socialboard.forms.TopicForm;
import se.erik.socialboard.service.CreateCategoryService;
import se.erik.socialboard.service.CreatePostService;
import se.erik.socialboard.service.CreateTopicService;
import se.erik.socialboard.service.ForumService;

@RestController
@RequestMapping("main/forum")
public class ForumController {
	
	private ForumService forumService;
	private CreateTopicService createTopicService;
	private CreatePostService createPostService;
	private CreateCategoryService createCategoryService;
	
	@Autowired
	public ForumController(ForumService forumService, CreateTopicService createTopicService,
			CreatePostService createPostService, CreateCategoryService createCategoryService) {
		this.forumService = forumService;
		this.createTopicService = createTopicService;
		this.createPostService = createPostService;
		this.createCategoryService = createCategoryService;
	}
	
	@GetMapping("category")
	public ResponseEntity<List<Category>> findAllCategories(){
		List<Category> categories = forumService.asyncFindAllCategories();
		
		return categories.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categories);
	}
	
	@PostMapping("category")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryForm form){
		Category newCategory = createCategoryService.createNewCategory(form.getCategoryName(), form.getDescription(), form.getCreatorId());
		
		return newCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.CREATED).body(newCategory);		
	}
	
	@GetMapping("category/{id}")
	public ResponseEntity<Category> findById(@PathVariable long id){
		Category category = forumService.asyncFindById(id);
		
		return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(category);
	}
	
	@PutMapping("category/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category updated){
		Category toUpdate = forumService.asyncFindById(id);
		
		if(toUpdate == null) {
			return ResponseEntity.notFound().build();
		}
		
		toUpdate.setCategoryName(updated.getCategoryName());
		toUpdate.setDescription(updated.getDescription());
		
		return ResponseEntity.ok(forumService.save(toUpdate));		
	}
	
	@GetMapping("category/{id}/topic")
	public ResponseEntity<List<Topic>> findTopicsInCategory(@PathVariable long id){
		List<Topic> result = forumService.findTopicsByCategoryId(id);
		
		
		return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result.stream().sorted().collect(Collectors.toList()));
	}
	
	@PostMapping("category/{id}/topic")
	public ResponseEntity<Topic> createTopic(@PathVariable long id, @RequestBody TopicForm topicForm){
		Topic newTopic = createTopicService.createNewTopic(topicForm.getTopicName(), topicForm.getCategoryId(), topicForm.getTopicCreatorId());
		
		return newTopic == null ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.CREATED).body(newTopic);
	}
	
	@PutMapping("category/{categoryId}/topic/{topicId}")
	public ResponseEntity<Topic> updateTopic(@PathVariable long categoryId, @PathVariable long topicId, @RequestBody Topic updated){
		Topic toUpdate = forumService.asyncFindTopicById(topicId);
		
		if(toUpdate == null) {
			return ResponseEntity.notFound().build();
		}
		
		toUpdate.setCreatorAlias(updated.getCreatorAlias());
		toUpdate.setTopicName(updated.getTopicName());
		
		return ResponseEntity.ok(forumService.saveTopic(toUpdate));		
	}
	
	
	
	
	
	
	
	
	
	 

}
