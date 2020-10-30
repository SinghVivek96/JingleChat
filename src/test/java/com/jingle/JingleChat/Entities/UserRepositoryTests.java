package com.jingle.JingleChat.Entities;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

	@DataJpaTest //TO TEST JPA
	@AutoConfigureTestDatabase(replace = Replace.NONE) //TO TEST WITH REAL DATABASE
	@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser()
	{
		User user = new User("itsvivek96@gmail.com","1234567890","VIVEK","SINGH","vivek.singh13");
		User savedUser = repo.save(user);
		User savedUserCheck = entityManager.find(User.class, savedUser.getId());
		
		assertThat(savedUserCheck.getEmail()).isEqualTo(user.getEmail());
		
		
	}
	
	@Test
	public void fetchByUserName()
	{
		User userResult=repo.findByUserName("vivek.singh13");
		assertThat(userResult).isNotNull();
	}
	
}
