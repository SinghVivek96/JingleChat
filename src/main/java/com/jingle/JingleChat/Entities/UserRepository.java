package com.jingle.JingleChat.Entities;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
//@NamedQuery(name = "findByUserName",query = "select u_s_e_r from users u_s_e_r where u_s_e_r.user_name = ?1")
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUserName(String userName);

	
}
