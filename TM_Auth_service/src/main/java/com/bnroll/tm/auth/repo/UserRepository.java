package com.bnroll.tm.auth.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bnroll.tm.user.User;



@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	 Optional<User>  findByEmail(String email);

}