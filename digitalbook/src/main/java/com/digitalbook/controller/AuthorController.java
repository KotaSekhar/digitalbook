package com.digitalbook.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.DTO.BookDTO;
import com.digitalbook.DTO.EditBookDTO;
import com.digitalbook.security.jwt.JwtUtils;
import com.digitalbook.service.AuthorBookService;
import com.digitalbook.service.TokenValidator;
@CrossOrigin(origins = "*", maxAge = 3600)
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
	public ResponseEntity<String> createBookByAuthor(@RequestHeader("Authorization") String token,
			@PathVariable String authorId, @RequestBody BookDTO bookDTO) {
		int authorid = Integer.parseInt(authorId);
		ResponseEntity<String> response = null;
		System.out.println("token===="+token);
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userName = jsonObject.getString("userName");
		String userRole = jsonObject.getString("userRole");
		bookDTO.setAuthor(userName);
		if (userRole.equals("ROLE_AUTHOR")) {
			String validateBookDTO = authorBookService.validateBookDTO(bookDTO);
			if (validateBookDTO.equalsIgnoreCase("Success")) {
				String createBookByAuthor = authorBookService.createBookByAuthor(authorid, bookDTO);
				if (!createBookByAuthor.isEmpty()) {
					response = new ResponseEntity<String>(createBookByAuthor, HttpStatus.OK);
				} else {
					response = new ResponseEntity<String>(
							new JSONObject().put("response", "create book failure").toString(), HttpStatus.NOT_FOUND);
				}
			} else {
				response = new ResponseEntity<String>(new JSONObject().put("response", validateBookDTO).toString(),
						HttpStatus.OK);
			}

		} else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString(),
					HttpStatus.OK);
		}
		return response;

	}

	@PostMapping(value = "/author/{authorId}/books/{bookId}")
	public ResponseEntity<String> editBlockUnBLockBookByAuthor(@RequestHeader("Authorization") String token,@PathVariable String authorId, @PathVariable int bookId,
			@RequestBody EditBookDTO editBookDTO) {
		
		ResponseEntity<String> response=null;
		String verifyToken = tokenValidator.verifyToken(token);
		JSONObject jsonObject = new JSONObject(verifyToken.toString());
		String userRole = jsonObject.getString("userRole");
		String blockUnblockBookByAuthor = null;
		if(userRole.equals("ROLE_READER")) {
		if (editBookDTO.getType().equals("EDIT")) {
			System.out.println("abc");
			blockUnblockBookByAuthor = authorBookService.editBookByAuthor(authorId, bookId, editBookDTO);
		} else {
			blockUnblockBookByAuthor = authorBookService.blockUnblockBookByAuthor(authorId, bookId,
					editBookDTO.getType());
		}
		}else {
			response = new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString(),HttpStatus.NOT_FOUND);
			
		}
		if (!blockUnblockBookByAuthor.isEmpty()) {
			response = new ResponseEntity<String>(blockUnblockBookByAuthor, HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return response;

	}
}
