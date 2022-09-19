package com.digitalbook.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.service.ReaderBookService;


@RestController
@RequestMapping(value = "/v1/digitalbooks")
public class ReaderController {
	@Autowired
	ReaderBookService readerBookService;

	@GetMapping(value = "/books/search/category={category}/author={author}/price={price}/publisher={publisher}")
	public ResponseEntity<String> searchBooks(@PathVariable String category, @PathVariable String author, @PathVariable String price,
			@PathVariable String publisher) throws JSONException {
		
		 String searchBook = readerBookService.searchBooks(category, author, price, publisher);
		 ResponseEntity<String> response;
		 if(!searchBook.isEmpty()) {
			 response= new ResponseEntity<String>(searchBook,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		return response;

	}
	
	@GetMapping(value = "/readers/emailId={emailId}/books")
	public ResponseEntity<String> getAllPurchagedBooks(@PathVariable String emailId) throws JSONException {
		 String allPurchagedBooks = readerBookService.getAllPurchagedBooks(emailId);
		 ResponseEntity<String> response;
		 if(!allPurchagedBooks.isEmpty()) {
			 response= new ResponseEntity<String>(allPurchagedBooks,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		return response;

	}
	
	
}
