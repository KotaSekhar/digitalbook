package com.digitalbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.digitalbook.controller.ReaderController;
import com.digitalbook.repository.UserRepository;
import com.digitalbook.security.jwt.JwtUtils;
import com.digitalbook.service.ReaderBookService;
import com.digitalbook.service.TokenValidator;

@ExtendWith(MockitoExtension.class)
public class ReaderTestController {
	
	
	@InjectMocks
	private ReaderController readerController;
	
	@Mock
	 TokenValidator tokenValidator;
	
	@Mock
	ReaderBookService readerBookService;
	
	
	
	@Test
	void searchBooks() {
		String category="NA";
		String author="NA";
		String price="NA";
		String publisher="NA";
		String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrdW1hciIsImlhdCI6MTY2Mzg2MzM0OSwiZXhwIjoxNjYzOTEzMzQ5fQ.ZtK2ZhHsnCvB8-_0vlnSA5DZAn5QPXPM21KFP9xDOcEafIesoCqdshzc4CeDzunKpflLLOKe6WVi1N8mDehscg";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","kumar");
		jsonObject.put("userRole", "ROLE_READER");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
		 JSONArray jsonArray = new JSONArray();
		 JSONObject object = new JSONObject();
		 object.put("bookId", 1);
		 object.put("title","ironman");
		 object.put("author","sekhar");
		 object.put("category","COMIC");
		 object.put("price", 500.00);
		 object.put("publisher","Rajesh");
		 object.put("logo","https://www.pexels.com");
		 object.put("publishedDate", "2022-09-22 23:34:41.0");
		 object.put("active","active");
		 object.put("content","it is comic book");
		 jsonArray.put(object);
		 String getPurchagedBooks = readerBookService.searchBooks(category,author,price,publisher);
		 getPurchagedBooks=jsonArray.toString();
		 when(readerBookService.searchBooks(category,author,price,publisher)).thenReturn(getPurchagedBooks);
		 ResponseEntity<String>	response = new ResponseEntity<String>(jsonArray.toString(), HttpStatus.OK);
			
		 assertEquals(readerController.searchBooks(token, category,author,price,publisher),response);

	}
	
	@Test
	void getAllPurchagedBooks() {
		String emailId="kumar@gmail.com";
		String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrdW1hciIsImlhdCI6MTY2Mzg2MzM0OSwiZXhwIjoxNjYzOTEzMzQ5fQ.ZtK2ZhHsnCvB8-_0vlnSA5DZAn5QPXPM21KFP9xDOcEafIesoCqdshzc4CeDzunKpflLLOKe6WVi1N8mDehscg";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","kumar");
		jsonObject.put("userRole", "ROLE_READER");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
		 JSONArray jsonArray = new JSONArray();
		 JSONObject object = new JSONObject();
		
		 object.put("bookId", 1);
		 object.put("title", "ironman");
		 object.put("author", "sekhar");
		 object.put("category", "COMIC");
		 object.put("price", 500.00);
		 object.put("publisher", "rajesh");
		 object.put("logo", "https://www.pexels.com");
		 object.put("publishedDate","2022-09-22 23:34:41");
		 object.put("content", "it is comic book");
		 jsonArray.put(object);
		 String getPurchagedBooks = readerBookService.getAllPurchagedBooks(emailId);
		 getPurchagedBooks=jsonArray.toString();
		 when(readerBookService.getAllPurchagedBooks(emailId)).thenReturn(getPurchagedBooks);
		 ResponseEntity<String>	response = new ResponseEntity<String>(jsonArray.toString(), HttpStatus.OK);
			
		 assertEquals(readerController.getAllPurchagedBooks(token, emailId),response);

	}
	
	@Test
	void buyABook() {
		String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrdW1hciIsImlhdCI6MTY2Mzg2MzM0OSwiZXhwIjoxNjYzOTEzMzQ5fQ.ZtK2ZhHsnCvB8-_0vlnSA5DZAn5QPXPM21KFP9xDOcEafIesoCqdshzc4CeDzunKpflLLOKe6WVi1N8mDehscg";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","kumar");
		jsonObject.put("userRole", "ROLE_READER");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
		 JSONObject object = new JSONObject();
		 
		 object.put("bookId", 1);
		 object.put("readerName", "kumar");
		 object.put("readerEmailId", "kumar@gmail.com");
		 
		 String validateBuyABookRequest = readerBookService.validateBuyABookRequest(object.toString());
		 validateBuyABookRequest="success";
		 when(readerBookService.validateBuyABookRequest(object.toString())).thenReturn(validateBuyABookRequest);
		
		
		String buyABook = readerBookService.buyABook(object.toString());
		buyABook=new JSONObject().put("response", "Buy a book Successfully").toString();
		 when(readerBookService.buyABook(object.toString())).thenReturn(buyABook);
		
		 ResponseEntity<String>	response = new ResponseEntity<String>(buyABook.toString(), HttpStatus.OK);
			
		 assertEquals(readerController.buyABook(token, object.toString()),response);

	}
	
	
	@Test
	void readABook() {
		String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrdW1hciIsImlhdCI6MTY2Mzg2MzM0OSwiZXhwIjoxNjYzOTEzMzQ5fQ.ZtK2ZhHsnCvB8-_0vlnSA5DZAn5QPXPM21KFP9xDOcEafIesoCqdshzc4CeDzunKpflLLOKe6WVi1N8mDehscg";
		String emailId="kumar@gmail.com";
		int bookId=1;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","kumar");
		jsonObject.put("userRole", "ROLE_READER");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
		 JSONObject objectBook = new JSONObject();
		 objectBook.put("title", "ironman");
		 objectBook.put("content", "it is comic book");
		 objectBook.put("author", "sekhar");
		 objectBook.put("category", "COMIC");
		 objectBook.put("logo", "https://www.pexels.com");
		 objectBook.put("price", 500.00);
		 objectBook.put("publishedDate","2022-09-22 23:34:41");
		 objectBook.put("publisher", "rajesh");
		
		String buyABook = readerBookService.readABook(emailId,bookId);
		buyABook=objectBook.toString();
		 when(readerBookService.readABook(emailId,bookId)).thenReturn(buyABook);
		
		 ResponseEntity<String>	response = new ResponseEntity<String>(buyABook.toString(), HttpStatus.OK);
			
		 assertEquals(readerController.readABook(token, emailId,bookId),response);

	}
	
	@Test
	void getBookByPaymentId() {
		String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrdW1hciIsImlhdCI6MTY2Mzg2MzM0OSwiZXhwIjoxNjYzOTEzMzQ5fQ.ZtK2ZhHsnCvB8-_0vlnSA5DZAn5QPXPM21KFP9xDOcEafIesoCqdshzc4CeDzunKpflLLOKe6WVi1N8mDehscg";
		String emailId="kumar@gmail.com";
		String pid="2475";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName","kumar");
		jsonObject.put("userRole", "ROLE_READER");
		String verifyToken = tokenValidator.verifyToken(token);
		verifyToken=jsonObject.toString();
		 when(tokenValidator.verifyToken(token)).thenReturn(verifyToken);
		 
		 JSONObject objectBook = new JSONObject();
		 objectBook.put("title", "ironman");
		 objectBook.put("content", "it is comic book");
		 objectBook.put("author", "sekhar");
		 objectBook.put("category", "COMIC");
		 objectBook.put("logo", "https://www.pexels.com");
		 objectBook.put("price", 500.00);
		 objectBook.put("publishedDate","2022-09-22 23:34:41");
		 objectBook.put("publisher", "rajesh");
		 objectBook.put("bookId",1);
		String getBook = readerBookService.getBookByPaymentId(emailId,pid);
		getBook=objectBook.toString();
		 when(readerBookService.getBookByPaymentId(emailId,pid)).thenReturn(getBook);
		
		 ResponseEntity<String>	response = new ResponseEntity<String>(getBook.toString(), HttpStatus.OK);
			
		 assertEquals(readerController.getBookByPaymentId(token, emailId,pid),response);

	}
}
