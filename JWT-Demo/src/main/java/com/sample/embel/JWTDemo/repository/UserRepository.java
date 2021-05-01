package com.sample.embel.JWTDemo.repository;

import com.sample.embel.JWTDemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String username);

}