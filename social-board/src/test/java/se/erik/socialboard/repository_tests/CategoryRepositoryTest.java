package se.erik.socialboard.repository_tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import se.erik.socialboard.data.CategoryRepository;
import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {
	
	@Autowired
	private CategoryRepository repo;
	
	@Autowired
	private TestEntityManager em;
	
	private Category testCategory;
	private Category testCategory2;
	private User testUser;	
	
	private List<Category> testCategories;
	
	@Before
	public void setup() {
		this.testCategory = new Category("Test category","Test decription");
		this.testCategory2 = new Category("Test category2", "Test description2");
		Category testCategory3 = new Category("Test category3", "Test description3");
		testCategories = new ArrayList<>();
		testCategories.add(testCategory);
		testCategories.add(testCategory2);
		testCategories.add(testCategory3);		
		testUser = new User("test@test.com", "Test", "Testsson", "Testy", LocalDate.now());
		testCategory.setCreator(testUser);
		testCategory2.setCreator(testUser);
		testCategories.forEach(em::persist);
		em.flush();		
	}
	
	@After
	public void tearDown() {
		repo.deleteAll(testCategories);
		em.remove(testUser);		
		em.flush();
	}
	
	@Test
	public void testFindCategoriesByUserId() throws InterruptedException, ExecutionException {
		List<Category> expected = Arrays.asList(testCategory, testCategory2);
		long userId = testUser.getId();
		
		Future<List<Category>> result = repo.findCategoriesByUserId(userId);
		
		assertEquals(expected, result.get());
	}
	
	@Test
	public void testFindByCategoryNameLike() {
		String query = "Test category%";		
		assertTrue(repo.findByCategoryNameLike(query).stream().allMatch(c->c.getCategoryName().contains(query.substring(0, query.length()-1))));
	}
	
	@Test
	public void testAsyncFindByCategoryNameLike() throws InterruptedException, ExecutionException {
		String query = "%cat%";
		Future<List<Category>> result = repo.asyncFindByCategoryNameLike(query);
		assertTrue(result.get().stream().allMatch(c->c.getCategoryName().contains(query.substring(1, query.length()-1))));		
	}
	
	@Test
	public void testFindAllCategories() {
		List<Category> expected = testCategories;
		assertEquals(expected, repo.findAllCategories());
	}
	
	@Test
	public void testAsyncFindAllCategories() throws InterruptedException, ExecutionException {
		List<Category> expected = testCategories;
		assertEquals(expected, repo.asyncFindAllCategories().get());
	}
	
	@Test
	public void testFindCategoryById() {
		long id = testCategory.getId();
		Category expected = testCategory;
		assertEquals(expected, repo.findById(id).get());
	}
	
	@Test
	public void testEagerFindCategoryById() throws InterruptedException, ExecutionException {
		assertEquals(testCategory, repo.eagerfindCategoryById(testCategory.getId()).get());
	}

}
