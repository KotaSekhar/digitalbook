package com.digitalbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.Category;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.OrderRepository;
import com.digitalbook.service.MyQueryRepositoryCustom;
import com.digitalbook.service.MyQueryRepositoryImpl;
import com.digitalbook.service.ReaderBookService;

@ExtendWith(MockitoExtension.class)
public class ReaderServiceTestController {

	@InjectMocks
	 ReaderBookService readerBookService;

	@Mock
	BookRepository bookRepository;

	@Mock
	MyQueryRepositoryCustom myQueryRepositoryCustom;

	@Mock
	OrderRepository orderRepository;
	@Mock
	MyQueryRepositoryImpl myQueryRepositoryImpl;
	@Test
	void buyABook() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 1);
		jsonObject.put("readerName", "kumar");
		jsonObject.put("readerEmailId", "kumar@gmail.com");
		
		String response = "Buy a book Successfully";
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("response", response);
		Object[] myArray = {"ironman",2};
		int i=1;
		List<Object[]> checkExistUserAndBooks = bookRepository.checkExistUserAndBooks(i,"kumar","kumar@gmail.com");
		checkExistUserAndBooks.add(myArray);
		System.out.println("checkExistUserAndBooks=="+myArray[0]);
		
	when(bookRepository.checkExistUserAndBooks(i,"kumar","kumar@gmail.com")).thenReturn(checkExistUserAndBooks);
		String buyABook = readerBookService.buyABook(jsonObject.toString());
		System.out.println("buyABook=="+buyABook);
		
		assertEquals(readerBookService.buyABook(jsonObject.toString()), jsonObject2.toString());
	}

	
	@Test
	void buyABookFailureTest() {
		String response = "failure";
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("response", response);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 1);
		jsonObject.put("readerName", "kumar");
		jsonObject.put("readerEmailId", "kumar@gmail.com");
		 assertEquals(readerBookService.buyABook(jsonObject.toString()), jsonObject2.toString());
	}
	
	@Test
	void readABook() {
		String response = "failure";
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("response", response);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "ironman");
		jsonObject.put("content", "it is comic book");
		jsonObject.put("author", "sekhar");
		jsonObject.put("category", "COMIC");
		jsonObject.put("logo", "https://www.pexels.com");
		jsonObject.put("price", 500.00);
		jsonObject.put("publishedDate","2022-09-22 23:34:41");
		jsonObject.put("publisher", "rajesh");
		Object[] myArray = {"ironman","it is comic book","sekhar","COMIC","https://www.pexels.com",500.00,"2022-09-22 23:34:41","rajesh"};
		int i=1;
		List<Object[]> checkExistUserAndBooks = orderRepository.checkExistUserByEmailAndBook("kumar@gamil.com",i);
		checkExistUserAndBooks.add(myArray);
		System.out.println("checkExistUserAndBooks=="+myArray[0]);
		
	when(orderRepository.checkExistUserByEmailAndBook("kumar@gamil.com",i)).thenReturn(checkExistUserAndBooks);
		
		
		 assertEquals(readerBookService.readABook("kumar@gamil.com", i), jsonObject.toString());
	}
	@Test
	void readABookFailureTest() {
		String response = "failure";
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("response", response);
		 assertEquals(readerBookService.readABook("kumar@gamil.com", 1), jsonObject2.toString());
	}

	
	@Test
	void getallPurchagedbook() {
		String emailId="kumar@gamil.com";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 1);
		jsonObject.put("title","ironman");
		jsonObject.put("author","sekhar");
		jsonObject.put("category","COMIC");
		jsonObject.put("price", 500.00);
		jsonObject.put("publisher","rajesh");
		jsonObject.put("logo","https://www.pexels.com");
		jsonObject.put("publishedDate", "2022-09-22 23:34:41");
		jsonObject.put("content","it is comic book");
		jsonObject.put("readerId",2);
		JSONArray jsonArray = new JSONArray().put(jsonObject);
		Object[] myArray = {1,"ironman","sekhar","COMIC",500.00,"rajesh","https://www.pexels.com","2022-09-22 23:34:41",2,"kumar","kumar@gmail.com","it is comic book"};
		List<Object[]> allPurchagedBooks = bookRepository.getAllPurchagedBooks(emailId);
		allPurchagedBooks.add(myArray);
		
	when(bookRepository.getAllPurchagedBooks(emailId)).thenReturn(allPurchagedBooks);
		 assertEquals(readerBookService.getAllPurchagedBooks(emailId), jsonArray.toString());
	}
	
	@Test
	void getallPurchagedbookFailureTest() {
		String response = "failure";
		String email="kumar@gamil.com";
		JSONObject jsonObject2 = new JSONObject().put("response", response);
		JSONArray jsonArray = new JSONArray().put(jsonObject2);
		String allPurchagedBooks = readerBookService.getAllPurchagedBooks("kumar@gamil.com");
		System.out.println("allPurchagedBooks=="+allPurchagedBooks);
		 assertEquals(readerBookService.getAllPurchagedBooks(email), jsonArray.toString());
	}
	
	@Test
	void searchBook() {
//		String emailId="kumar@gamil.com";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 1);
		jsonObject.put("title","ironman");
		jsonObject.put("author","sekhar");
		jsonObject.put("category","COMIC");
		jsonObject.put("price", 500.00);
		jsonObject.put("publisher","Rajesh");
		jsonObject.put("logo","https://www.pexels.com");
		jsonObject.put("publishedDate", "2022-09-22 23:34:41");
		jsonObject.put("active","active");
		jsonObject.put("content","it is comic book");
//		Timestamp timestamp = Timestamp.valueOf( "2022-09-22 23:34:41");
		Book books = Book.builder().id(1).title("ironman")
				.author("sekhar").category(Category.valueOf("COMIC"))
				.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
				.publishedDate("2022-09-22 23:34:41").active("active").content("it is comic book").build();
		JSONArray jsonArray = new JSONArray().put(jsonObject);
		List<Book> book = bookRepository.getBookDetails();
		book.add(books);
		
	when(bookRepository.getBookDetails()).thenReturn(book);
		 assertEquals(readerBookService.searchBooks("NA", "NA", "NA", "NA"), jsonArray.toString());
	}
	
	@Test
	void searchBooktest2() {
String dynamicquery="select * from books where  book_price= '500' and  book_category= 'COMIC' and  book_author= 'sekhar' and  book_publisher= 'rajesh'";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 1);
		jsonObject.put("title","ironman");
		jsonObject.put("author","sekhar");
		jsonObject.put("category","COMIC");
		jsonObject.put("price", 500.00);
		jsonObject.put("publisher","rajesh");
		jsonObject.put("logo","https://www.pexels.com");
		jsonObject.put("publishedDate", "2022-09-22 23:34:41");
		jsonObject.put("active","active");
		jsonObject.put("content","it is comic book");
		JSONArray jsonArray = new JSONArray().put(jsonObject);
		Object[] myArray = {1,"sekhar","COMIC","https://www.pexels.com",500.00,"2022-09-22 23:34:41","rajesh","ironman","active","it is comic book"};
		List<Object[]> book = myQueryRepositoryCustom.executeQuery(dynamicquery);
		book.add(myArray);
		
		when(myQueryRepositoryCustom.executeQuery(dynamicquery)).thenReturn(book);
		 assertEquals(readerBookService.searchBooks("COMIC", "sekhar", "500", "rajesh"), jsonArray.toString());
	}
	
	@Test
	void getBookByPaymentId() {
		String response = "failure";
		String emailId="kumar@gamil.com";
		String pid="2647";
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("title","ironman");
		jsonObject.put("content","it is comic book");
		jsonObject.put("author","sekhar");
		jsonObject.put("category","COMIC");
		jsonObject.put("logo","https://www.pexels.com");
		jsonObject.put("price", 500.00);
		jsonObject.put("publishedDate", "2022-09-22 23:34:41");
		jsonObject.put("publisher","rajesh");
		jsonObject.put("bookId", 1);
		
		Object[] myArray = {"ironman","it is comic book","sekhar","COMIC","https://www.pexels.com",500.00,"2022-09-22 23:34:41","rajesh",1};
		List<Object[]> book =  orderRepository.checkExistUserByEmailAndBookByPaymentId(emailId,pid);
		book.add(myArray);
		when(orderRepository.checkExistUserByEmailAndBookByPaymentId(emailId,pid)).thenReturn(book);
		 assertEquals(readerBookService.getBookByPaymentId(emailId,pid), jsonObject.toString());
	}
	@Test
	void getBookByPaymentIdFailureTest() {
		String response = "failure";
		String email="kumar@gamil.com";
		JSONObject jsonObject2 = new JSONObject().put("response", response);
		 assertEquals(readerBookService.getBookByPaymentId(email,"1234"), jsonObject2.toString());
	}
	
	@Test
	void validateBuyABookRequest() {
		String response = "success";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 0);
		jsonObject.put("readerName", "sekhar");
		jsonObject.put("readerEmailId", "sekhar@gmail.com");
		 assertEquals(readerBookService.validateBuyABookRequest(jsonObject.toString()), response);
	}
	
}
