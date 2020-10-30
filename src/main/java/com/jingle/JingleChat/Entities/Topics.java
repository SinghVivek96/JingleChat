package com.jingle.JingleChat.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQuery(name = "Topics.fetchPublicTopics",query = "Select topic.topicName from Topics topic where topic.password is null")
@Entity
@Table(name = "Topics")
public class Topics {
	@Id
	@Column(name = "topicId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long topicId;
	@Column(name = "topicName")
	private String topicName;
	@Column(name = "password")
	private String password;
	
	//Constructors
	public Topics() {
	}
	
	public Topics(long topicId, String topicName, String password) {
		super();
		this.topicId = topicId;
		this.topicName = topicName;
		this.password = password;
	}
	
	//Gettors and Settors
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Topics [topicId=" + topicId + ", topicName=" + topicName + ", password=" + password + "]";
	}
	
	
	
}
