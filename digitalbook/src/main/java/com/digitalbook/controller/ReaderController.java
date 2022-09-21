package com.digitalbook.controller;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.service.ReaderBookService;


@RestController
@RequestMapping(value = "/digitalbooks")
public class ReaderController {
	@Autowired
	ReaderBookService readerBookService;

	@GetMapping(value = "/books/search/category={category}/author={author}/price={price}/publisher={publisher}")
	public ResponseEntity<String> searchBooks(@PathVariable String category, @PathVariable String author, @PathVariable String price,
			@PathVariable String publisher) {
		
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
	public ResponseEntity<String> getAllPurchagedBooks(@PathVariable String emailId) {
		 String allPurchagedBooks = readerBookService.getAllPurchagedBooks(emailId);
		 ResponseEntity<String> response;
		 if(!allPurchagedBooks.isEmpty()) {
			 response= new ResponseEntity<String>(allPurchagedBooks,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		return response;

	}
	
	@PostMapping(value = "/books/buy")
	public ResponseEntity<String> buyABook(@RequestBody String request) {
		 String allPurchagedBooks = readerBookService.buyABook(request);
		 ResponseEntity<String> response;
		 if(!allPurchagedBooks.isEmpty()) {
			 response= new ResponseEntity<String>(allPurchagedBooks,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		return response;

	}
	
	@GetMapping(value = "/readers/{emailId}/books/{bookId}")
	public ResponseEntity<String> readABook(@PathVariable String emailId,@PathVariable int bookId) {
		 String allPurchagedBooks = readerBookService.readABook(emailId,bookId);
		 ResponseEntity<String> response;
		 if(!allPurchagedBooks.isEmpty()) {
			 response= new ResponseEntity<String>(allPurchagedBooks,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		return response;

	}
	
	@PostMapping(value = "readers/{emailId}/books/pid={pid}")
	public ResponseEntity<String> getBookByPaymentId(@PathVariable String emailId,@PathVariable String pid) {
		 String allPurchagedBooks = readerBookService.getBookByPaymentId(emailId,pid);
		 ResponseEntity<String> response;
		 if(!allPurchagedBooks.isEmpty()) {
			 response= new ResponseEntity<String>(allPurchagedBooks,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		return response;

	}
	
}
