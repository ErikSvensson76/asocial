package se.erik.socialboard.data;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import se.erik.socialboard.entity.Category;
import se.erik.socialboard.entity.User;

public interface CategoryRepository extends CrudRepository<Category, Long>{
	
	@Async
	@Query("SELECT cat FROM Category cat WHERE cat.creator.id = :userId")
	Future<List<Category>> findCategoriesByUserId(@Param("userId") long userId);
	
	@Query("SELECT cat FROM Category cat WHERE cat.categoryName LIKE :categoryName")
	List<Category> findByCategoryNameLike(@Param("categoryName")String categoryName);
	
	@Async
	@Query("SELECT cat FROM Category cat WHERE cat.categoryName LIKE :categoryName")
	Future<List<Category>> asyncFindByCategoryNameLike(@Param("categoryName")String categoryName);
	
	
	@Query("SELECT cat FROM Category cat")
	List<Category> findAllCategories();
		
	@Async
	@Query("SELECT cat FROM Category cat")
	Future<List<Category>> asyncFindAllCategories();
	
	@Async
	@Query("SELECT cat FROM Category cat WHERE cat.id = :id")
	Future<Category> findCategoryById(@Param("id") long id);
	
	@Async
	@Query("SELECT cat FROM Category cat JOIN FETCH cat.creator WHERE cat.id = :id")
	Future<Category> eagerfindCategoryById(@Param("id") long id);
	
	
	
	
	
	
	

}
