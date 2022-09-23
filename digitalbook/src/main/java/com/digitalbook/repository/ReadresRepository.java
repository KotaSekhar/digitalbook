package com.digitalbook.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Reader;

@Repository
public interface ReadresRepository extends CrudRepository<Reader, Integer> {

	

}
