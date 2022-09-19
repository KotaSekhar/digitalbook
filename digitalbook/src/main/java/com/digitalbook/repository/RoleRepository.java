package com.digitalbook.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.springjwt.models.ERole;
import com.digitalbook.springjwt.models.Role;
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

	Optional<Role> findByName(ERole name);
}
