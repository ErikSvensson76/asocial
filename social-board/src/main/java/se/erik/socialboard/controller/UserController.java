package se.erik.socialboard.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;
import se.erik.socialboard.service.UserService;

@RestController
@RequestMapping("main/user")
public class UserController {
	
	private UserService userService;		
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<User>> findActiveUsers(){		
		List<User> allUsers = userService.findByActiveStatus(true);
		if(allUsers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}		
		return ResponseEntity.ok().body(allUsers);		
	}
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User newUser){
		newUser = userService.save(newUser);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
				
	}
	
	@PutMapping("{id}")
	public ResponseEntity<User> update(@PathVariable long id, @RequestBody User updatedUser){
		User toUpdate = userService.findById(id).orElse(null);
		
		if(toUpdate == null) {
			return ResponseEntity.notFound().build();
		}
		
		toUpdate.setAlias(updatedUser.getAlias());
		toUpdate.setEmail(updatedUser.getEmail());
		toUpdate.setFirstName(updatedUser.getFirstName());
		toUpdate.setLastName(updatedUser.getLastName());
		toUpdate.setActive(updatedUser.isActive());
		
		return ResponseEntity.accepted().body(userService.save(toUpdate));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> findById(@PathVariable long id) throws InterruptedException{
		User user = userService.asyncFindById(id);
		
				
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();		
	}
	
	@GetMapping("email")
	public ResponseEntity<User> findByEmail(@RequestParam("email") String email){
		User user = userService.asyncFindByEmail(email);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("alias")
	public ResponseEntity<List<User>> findByAlias(@RequestParam("alias") String alias){
		List<User> result = userService.findByAliasLike(alias)
				.stream()
				.sorted((u1,u2) -> u1.getAlias().compareTo(u2.getAlias()))
				.collect(Collectors.toList());
		
		return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
	}
	
	@GetMapping("{id}/category")
	public ResponseEntity<List<Category>> findCategoryByUserId(@PathVariable long id){
		List<Category> categories = userService.findCategoriesByUserId(id);
				
		return categories.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categories);		
	}
	
	@GetMapping("{id}/topic")
	public ResponseEntity<List<Topic>> findTopicByUserId(@PathVariable long id){
		List<Topic> topics = userService.findTopicsByUserId(id);
		
		return topics.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(topics);
	}
	
	@GetMapping("{id}/post")
	public ResponseEntity<List<Post>> findPostByUserId(@PathVariable long id){
		List<Post> posts = userService.findPostsByUserId(id);
		return posts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(posts);
	}
	
	@GetMapping("inactive")
	public ResponseEntity<List<User>> findInactiveUsers(){
		List<User> inactiveUsers = userService.findByActiveStatus(false);
		
		return inactiveUsers.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(inactiveUsers);
		
	}
	
	

}
