package idu.cs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import idu.cs.domain.User;

public interface UserRepository 
	extends JpaRepository<User, Long> {
	public List<User> findByName(String name);
	

}
