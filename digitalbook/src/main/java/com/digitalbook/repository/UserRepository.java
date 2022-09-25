package com.digitalbook.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.springjwt.models.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query(value = "SELECT u.username,r.name FROM digitalbooksdb.users u ,digitalbooksdb.user_roles ur,digitalbooksdb.roles r where u.id=ur.user_id and r.id=ur.role_id and u.username=:userNameFromJwt", nativeQuery = true)
	List<Object[]> getUserdetails(String userNameFromJwt);

	@Modifying
	@Query(value = "UPDATE `digitalbooksdb`.`users` SET `status` = 'LOGIN' WHERE (username =:name)", nativeQuery = true)
	void updateStatus(String name);
}
