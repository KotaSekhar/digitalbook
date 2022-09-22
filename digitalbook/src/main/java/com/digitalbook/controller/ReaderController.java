package com.digitalbook.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.service.ReaderBookService;
import com.digitalbook.service.TokenValidator;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/digitalbooks")
public class ReaderController {
	@Autowired
	ReaderBookService readerBookService;

	@Autowired
	TokenValidator tokenValidator;
	
	@GetMapping(value = "/books/search/category={category}/author={author}/price={price}/publisher={publisher}")
	public ResponseEntity<String> searchBooks(@RequestHeader("Authorization") String token,@PathVariable String category, @PathVariable String author,
			@PathVariable String price, @PathVariable String publisher) {
		ResponseEntity<String> response=null;
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userRole = jsonObject.getString("userRole");
		String searchBook =null;
		if(userRole.equals("ROLE_READER")) {
			 searchBook = readerBookService.searchBooks(category, author, price, publisher);
		}else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString(),HttpStatus.NOT_FOUND);
		}
		if (!searchBook.isEmpty()) {
			response = new ResponseEntity<String>(searchBook, HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Failed to search books").toString(),HttpStatus.NOT_FOUND);
		}
		return response;

	}

	@GetMapping(value = "/readers/emailId={emailId}/books")
	public ResponseEntity<String> getAllPurchagedBooks(@RequestHeader("Authorization") String token,@PathVariable String emailId) {
		ResponseEntity<String> response=null;
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userRole = jsonObject.getString("userRole");
		String allPurchagedBooks =null;
		if(userRole.equals("ROLE_READER")) {
			allPurchagedBooks = readerBookService.getAllPurchagedBooks(emailId);
		}else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString(),HttpStatus.NOT_FOUND);
		}
		 
		if (!allPurchagedBooks.isEmpty()) {
			response = new ResponseEntity<String>(allPurchagedBooks, HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Failed to get a Book reader by all purchaged books").toString(),HttpStatus.NOT_FOUND);
		}
		return response;

	}

	@PostMapping(value = "/books/buy")
	public ResponseEntity<String> buyABook(@RequestHeader("Authorization") String token,@RequestBody String request) {
		ResponseEntity<String> response=null;
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userRole = jsonObject.getString("userRole");
		String buyabook =null;
		if(userRole.equals("ROLE_READER")) {
		 String validateBuyABookRequest = readerBookService.validateBuyABookRequest(request);
	
		if(validateBuyABookRequest.equals("success")) {
			buyabook =readerBookService.buyABook(request);
		}else {
			response = new ResponseEntity<String>(new JSONObject().put("response", validateBuyABookRequest).toString(),
					HttpStatus.OK);
			
		}
		}else {
			 response=  new ResponseEntity<String>(
						new JSONObject().put("response", "Invalid User").toString(), HttpStatus.NOT_FOUND);
		}
		 if(!buyabook.isEmpty()) {
			 response= new ResponseEntity<String>(buyabook,HttpStatus.OK);
		 }else {
			 response=  new ResponseEntity<String>(
						new JSONObject().put("response", "Failed to Buy a Book").toString(), HttpStatus.NOT_FOUND);
		 }
		return response;

	}

	@GetMapping(value = "/readers/{emailId}/books/{bookId}")
	public ResponseEntity<String> readABook(@RequestHeader("Authorization") String token,@PathVariable String emailId, @PathVariable int bookId) {
		
		ResponseEntity<String> response=null;
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userRole = jsonObject.getString("userRole");
		String readABook =null;
		if(userRole.equals("ROLE_READER")) {
		 readABook = readerBookService.readABook(emailId, bookId);
		}else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString(),HttpStatus.NOT_FOUND);
		}
		if (!readABook.isEmpty()) {
			response = new ResponseEntity<String>(readABook, HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Failed to Read a Book").toString(),HttpStatus.NOT_FOUND);
		}
		return response;

	}

	@PostMapping(value = "readers/{emailId}/books/pid={pid}")
	public ResponseEntity<String> getBookByPaymentId(@RequestHeader("Authorization") String token,@PathVariable String emailId, @PathVariable String pid) {
		
		ResponseEntity<String> response=null;
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userRole = jsonObject.getString("userRole");
		String getBookByPaymentId =null;
		if(userRole.equals("ROLE_READER")) {
			getBookByPaymentId = readerBookService.getBookByPaymentId(emailId, pid);
		}else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString(),HttpStatus.NOT_FOUND);
			
		}
		if (!getBookByPaymentId.isEmpty()) {
			response = new ResponseEntity<String>(getBookByPaymentId, HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Failed to get a Book by paymentId").toString(),HttpStatus.NOT_FOUND);
		}
		return response;

	}

}
