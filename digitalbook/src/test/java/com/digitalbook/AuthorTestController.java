package com.digitalbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.digitalbook.DTO.BookDTO;
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
	

	
	
	@Test
    void testGetBooks() throws Exception {
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
		
		
		Book books = Book.builder().title("testTitle")
				.category(Category.valueOf("COMIC")).author("sekhar")
				.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
				.publishedDate(timestamp).active("ACTIVE").content("This is a comic book").build();
		ResponseEntity<String>	response = new ResponseEntity<String>(books.toString(), HttpStatus.OK);
       when(authorBookService.createBookByAuthor(1, bookDTO)).thenReturn(response.toString());
       String authorId="1";
       String token="Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZWtoYXIiLCJpYXQiOjE2NjM4NTE2MzYsImV4cCI6MTY2MzkwMTYzNn0.X6NWPYSr_X7TKrdM4wBxWfkq9knT1BBXoNDUu0ZPzmeChPDUuGEMOhmydUYumsm0i-3s0_VmS8W0bNNdC4pJ1A";
       ResponseEntity<String> createBookByAuthor = authorController.createBookByAuthor(token, authorId, bookDTO);
       assertEquals(response, createBookByAuthor);
       
   }
}
