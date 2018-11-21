package se.erik.socialboard.data;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import se.erik.socialboard.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	@Async
	CompletableFuture<List<User>> findByAliasLike(String alias);
	@Async
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Future<User> asyncFindByEmail(@Param("email")String email);
	@Async
	CompletableFuture<List<User>> findByActiveTrue();
	@Async
	CompletableFuture<List<User>> findByActiveFalse();
	@Async
	CompletableFuture<List<User>> findByLastNameLikeAndFirstNameLike(String lastName, String firstName);
	@Async
	@Query("SELECT u FROM User u WHERE u.id = :id")
	CompletableFuture<User> asyncFindById(@Param("id")long id);
	Optional<User> findByEmail(String email);

	
	
	
}
