package com.jingle.JingleChat.Entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.jingle.JingleChat.DAO.TopicDAO;

@DataJpaTest //TO TEST JPA
@AutoConfigureTestDatabase(replace = Replace.NONE) //TO TEST WITH REAL DATABASE
@Rollback(false)
public class TopicRepositoryTest {
	@Autowired
	TopicDAO repo;
	@Autowired 
	private TopicsRepository repoTopics;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void findByTopicName()
	{
		Topics topic = repoTopics.findByTopicName("Flower");
		System.out.println(topic.getTopicName());
		assertThat(topic.getTopicName()).isEqualTo("Flower");
	}
	
	@Test
	public void fetchPublicTopics()
	{
		List<String> topics = repo.fetchPublicTopics();
		System.out.println("topics are   "+topics);
		assertThat(topics).isNotNull();
	}
	
}
