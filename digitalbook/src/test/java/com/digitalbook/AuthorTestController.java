package com.digitalbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.digitalbook.DTO.BookDTO;
import com.digitalbook.DTO.EditBookDTO;
import com.digitalbook.controller.AuthorController;
import com.digitalbook.entity.Book;
import com.digitalbook.entity.Category;
import com.digitalbook.service.AuthorBookService;
import com.digitalbook.service.TokenValidator;

@ExtendWith(MockitoExtension.class)
public class AuthorTestController {

	
	@InjectMocks
	private AuthorController authorController;
	@Mock 
	AuthorBookService authorBookService;
	@Mock
	TokenValidator tokenValidator;

	
	
	@Test
    void createbook(){
		  String token="Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZWtoYXIiLCJpYXQiOjE2NjM5NjkwMzYsImV4cCI6MTY2NDAxOTAzNn0.vfFyKCp0fv2EazWJ-Tcu9vxfpi0aTPFzO192bAoczuVY6_x1IpmtqxDWCx-dsF5F3B138LM7vKT2fk4zQPjojg";
		  String authorId="1";
		  int authorid=1;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("COMIC");
		bookDTO.setAuthor("sekhar");
		bookDTO.setPrice(new BigDecimal("500"));
		bookDTO.setPublisher("Rajesh");
		bookDTO.setPublishedDate(timestamp.toString());
		bookDTO.setLogo("https://www.pexels.com");
		bookDTO.setActive("ACTIVE");
		bookDTO.setContent("This is a comic book");
		 String validateBookDTO = authorBookService.validateBookDTO(bookDTO);
		 validateBookDTO="Success";
		 when(authorBookService.validateBookDTO(bookDTO)).thenReturn(validateBookDTO);
		
		 String createBookByAuthor = authorBookService.createBookByAuthor(authorid, bookDTO);
		 createBookByAuthor=new JSONObject().put("response", "create book successfully").put("bookId", 1).toString();
		 when(authorBookService.createBookByAuthor(authorid, bookDTO)).thenReturn(createBookByAuthor);
		ResponseEntity<String>	response = new ResponseEntity<String>(new JSONObject().put("response", "create book successfully").put("bookId", 1).toString(), HttpStatus.OK);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","sekhar");
		jsonObject.put("userRole", "ROLE_AUTHOR");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
       assertEquals(authorController.createBookByAuthor(token, authorId, bookDTO), response);
       
   }
	
	@Test
    void createbookFailureTest() {
		  String token="Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZWtoYXIiLCJpYXQiOjE2NjM5NjkwMzYsImV4cCI6MTY2NDAxOTAzNn0.vfFyKCp0fv2EazWJ-Tcu9vxfpi0aTPFzO192bAoczuVY6_x1IpmtqxDWCx-dsF5F3B138LM7vKT2fk4zQPjojg";
		  String authorId="1";
			ResponseEntity<String>	response = new ResponseEntity<String>(new JSONObject().put("response", "Invalid User").toString(), HttpStatus.OK);
			BookDTO bookDTO = new BookDTO();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","sekhar");
		jsonObject.put("userRole", "ROLE_ADMIN");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
       assertEquals(authorController.createBookByAuthor(token, authorId, bookDTO), response);
       
   }
	
	@Test
    void editBlockUnBLockBookByAuthor() {
		 String token="Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZWtoYXIiLCJpYXQiOjE2NjM5NjkwMzYsImV4cCI6MTY2NDAxOTAzNn0.vfFyKCp0fv2EazWJ-Tcu9vxfpi0aTPFzO192bAoczuVY6_x1IpmtqxDWCx-dsF5F3B138LM7vKT2fk4zQPjojg";
		  int authorid=1;
		  int bookid=1;
		  Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
		
		EditBookDTO editBookDTO = new EditBookDTO();
		editBookDTO.setTitle("testTitle");
		editBookDTO.setCategory("COMIC");
		editBookDTO.setPrice(new BigDecimal("500"));
		editBookDTO.setPublisher("Rajesh");
		editBookDTO.setLogo("https://www.pexels.com");
		editBookDTO.setContent("This is a comic book");
		editBookDTO.setType("EDIT");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","sekhar");
		jsonObject.put("userRole", "ROLE_AUTHOR");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
		 
		 String editBookByAuthor = authorBookService.editBookByAuthor(authorid,bookid, editBookDTO);
		 editBookByAuthor=new JSONObject().put("response", "Edit the book Successfully").toString();
		 
		 when(authorBookService.editBookByAuthor(authorid,bookid, editBookDTO)).thenReturn(editBookByAuthor);
		ResponseEntity<String>	response = new ResponseEntity<String>(new JSONObject().put("response", "Edit the book Successfully").toString(), HttpStatus.OK);
		
      assertEquals(authorController.editBlockUnBLockBookByAuthor(token, authorid,bookid, editBookDTO), response);
       
   }
}
