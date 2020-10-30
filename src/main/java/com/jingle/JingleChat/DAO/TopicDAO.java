package com.jingle.JingleChat.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jingle.JingleChat.Entities.Topics;
import com.jingle.JingleChat.Entities.TopicsRepository;

@Repository
@Transactional
public class TopicDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<String> fetchPublicTopics()//chcek for null
	{
		return entityManager.createNamedQuery("Topics.fetchPublicTopics").getResultList(); 
	}

}
