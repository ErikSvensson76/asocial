package se.erik.socialboard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import se.erik.socialboard.data.UserRepository;
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.Post;
import se.erik.socialboard.entity.Topic;
import se.erik.socialboard.entity.User;
import se.erik.socialboard.service.ForumService;
import se.erik.socialboard.service.UserService;
import static se.erik.socialboard.utils.StringUtility.*;

@Component
@Transactional
public class DatabaseSeeder implements CommandLineRunner{
	
	private UserService userService;
	private ForumService forumService;
	private UserRepository userRepo;
	
	@Autowired
	public DatabaseSeeder(UserService userService, ForumService forumService, UserRepository userRepo) {
		this.userService = userService;
		this.forumService = forumService;
		this.userRepo = userRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		createUsers();
		createCategories();
		createTopics();
		createPosts();		
	}

	private void createPosts() {
		User nisse = userService.findByEmail("nisselito@gmail.com").get();
		User erik = userService.findByEmail("erik.svensson@hotmail.com").get();
		User anna = userService.findByEmail("annanas@telia.se").get();
		User sofia = userService.findByEmail("sorso@tele2.se").get();
		Topic cooking = forumService.findTopicsByTopicName("Best pasta recipes").stream().findFirst().get();
		Topic gaming = forumService.findTopicsByTopicName("Good strategy games for beginners").stream().findFirst().get();
		
		Post p1 = new Post(nisse.getAlias());
		Post p2 = new Post(nisse.getAlias());
		Post p3 = new Post(erik.getAlias());
		Post p4 = new Post(erik.getAlias());
		Post p5 = new Post(anna.getAlias());
		Post p6 = new Post(sofia.getAlias());
		
		p1.setAuthor(nisse);
		p1.setTextContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Montes nascetur ridiculus mus mauris vitae ultricies. Eu nisl nunc mi ipsum faucibus. Neque egestas congue quisque egestas diam in. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae.");
		p1.setTopic(gaming);
		p1.setLatestActivity();
		
		p2.setAuthor(nisse);
		p2.setTextContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Enim eu turpis egestas pretium aenean pharetra magna ac placerat. Ac tortor vitae purus faucibus ornare suspendisse sed nisi.");
		p2.setTopic(cooking);
		p2.setLatestActivity();
		
		p3.setAuthor(erik);
		p3.setTextContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Dictum sit amet justo donec. Mi sit amet mauris commodo.");
		p3.setTopic(gaming);
		p3.setLatestActivity();
		
		p4.setAuthor(erik);
		p4.setTextContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Auctor eu augue ut lectus arcu. Nullam eget felis eget nunc. Non blandit massa enim nec.");
		p4.setTopic(gaming);
		p4.setLatestActivity();
		
		p5.setAuthor(anna);
		p5.setTextContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. A lacus vestibulum sed arcu non odio euismod. Nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet. Eget aliquet nibh praesent tristique magna.");
		p5.setTopic(gaming);
		p5.setLatestActivity();
		
		p6.setAuthor(sofia);
		p6.setTextContent("convallis tellus id interdum");
		p6.setTopic(gaming);
		p6.setLatestActivity();
		
		forumService.savePost(p1);
		forumService.savePost(p2);
		forumService.savePost(p3);
		forumService.savePost(p4);
		forumService.savePost(p5);
		forumService.savePost(p6);
	}

	private void createTopics() {
		Topic t1 = new Topic(LocalDateTime.now(),"Best pasta recipes");
		Topic t2 = new Topic(LocalDateTime.of(2017, 5, 23, 13, 10),"Good strategy games for beginners");
		Category c1 = forumService.findByCategoryNameLike("Cooking").stream().findFirst().orElseThrow(IllegalArgumentException::new);
		Category c2 = forumService.findAllCategories()
				.stream()
				.filter(c -> c.getCategoryName().equals(nameify.apply("gaming")))
				.findFirst().get();
		
		User u1 = userService.findById(4).orElse(null);
		User u2 = userService.findById(1).orElse(null);
		
		t1.setCategory(c1);
		t1.setTopicCreator(u2);
		
		t2.setCategory(c2);
		t2.setTopicCreator(u1);
		
		forumService.saveTopic(t1);
		forumService.saveTopic(t2);				
	}

	private void createCategories() {
		Category cat1 = new Category("Cooking","Everything from fast food to gourmet dinner");
		Category cat2 = new Category("Gaming", "Everything gaming related goes here");
		User u1 = userService.findByEmail("nisselito@gmail.com").get();
		
		cat1.setCreator(u1);
		cat2.setCreator(userService.findById(2).get());	
		forumService.save(cat1);
		forumService.save(cat2);
		
	
		
	}

	private void createUsers() {
		Set<User> toCreate = new HashSet<>();
		toCreate.add(new User("nisselito@gmail.com","Nils","Olsson","Olly", LocalDate.of(2016, 3, 4)));
		toCreate.add(new User("erik.svensson@hotmail.com","Erik","Svensson","Smelly", LocalDate.of(2017, 4, 6)));
		toCreate.add(new User("annanas@telia.se","Anna","Alvbåge","Ananas",LocalDate.of(2018, 6, 29)));
		toCreate.add(new User("sorso@tele2.se","Sofia","Örnberg","Sorso",LocalDate.now()));		
		toCreate.forEach(userService::save);		
	}

}
