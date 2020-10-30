package com.jingle.JingleChat.Entities;



import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicsRepository extends JpaRepository<Topics, Long> {	
	Topics findByTopicName(String topicName);
}
