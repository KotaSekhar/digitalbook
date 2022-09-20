package com.digitalbook.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.configurationprocessor.json.JSONException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.DTO.BookDTO;
import com.digitalbook.security.jwt.JwtUtils;
import com.digitalbook.security.services.TokenValidator;
import com.digitalbook.service.AuthorBookService;


@RestController
@RequestMapping(value = "/digitalbooks")
public class AuthorController {

	@Autowired
	AuthorBookService authorBookService;
	@Autowired
	TokenValidator tokenValidator;
	
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping(value = "/author/{authorId}/books")
	public ResponseEntity<String> createBookByAuthor(@RequestHeader("Authorization") String token, @PathVariable String authorId,@RequestBody BookDTO bookDTO ) throws JSONException {
		int authorid = Integer.parseInt(authorId);
		ResponseEntity<String> response;
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userName = jsonObject.getString("userName");
		String userRole = jsonObject.getString("userRole");
		bookDTO.setAuthor(userName);
		if(userRole.equals("ROLE_AUTHOR")) {
			System.out.println("============");
			String createBookByAuthor = authorBookService.createBookByAuthor(authorid,bookDTO);
		 if(!createBookByAuthor.isEmpty()) {
			 response= new ResponseEntity<String>(createBookByAuthor,HttpStatus.OK);
		 }else {
			 response= new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		 }
		}else {
			response= new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString() ,HttpStatus.OK);
		}
		return response;

	}
}
