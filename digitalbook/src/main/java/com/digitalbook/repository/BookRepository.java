package com.digitalbook.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

	@Query("From Book")
	List<Book> getBookDetails();

}
