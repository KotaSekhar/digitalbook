package com.digitalbook.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.DTO.BookDTO;
import com.digitalbook.service.AuthorBookService;


@RestController
@RequestMapping(value = "/digitalbooks")
public class AuthorController {

	@Autowired
	AuthorBookService authorBookService;
	
	@PostMapping(value = "/author/{authorId}/books")
	public ResponseEntity<String> createBookByAuthor(@PathVariable String authorId,@RequestBody BookDTO bookDTO ) throws JSONException {
		int authorid = Integer.parseInt(authorId);
		String createBookByAuthor = authorBookService.createBookByAuthor(authorid,bookDTO);
		 
		 ResponseEntity<String> response;
		 if(!createBookByAuthor.isEmpty()) {
			 response= new ResponseEntity<String>(createBookByAuthor,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		return response;

	}
}
