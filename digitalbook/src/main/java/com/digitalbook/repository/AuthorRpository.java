package com.digitalbook.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Author;

@Repository
public interface AuthorRpository extends CrudRepository<Author, Integer> {

	Author findById(int authorId);

}
