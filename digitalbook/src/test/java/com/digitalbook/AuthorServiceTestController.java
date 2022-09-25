package com.digitalbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
import com.digitalbook.entity.Book;
import com.digitalbook.entity.Category;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.UserRepository;
import com.digitalbook.service.AuthorBookService;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTestController {

	@InjectMocks
	private AuthorBookService authorBookService;
	
	@Mock
	BookRepository bookRepository;
	@Mock
	UserRepository userRepository;
	
	@Test
    void createbook(){
		  int authorid=1;
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("COMIC");
		bookDTO.setAuthor("sekhar");
		bookDTO.setPrice(new BigDecimal("500"));
		bookDTO.setPublisher("Rajesh");
		bookDTO.setPublishedDate("2022-09-22 23:34:41");
		bookDTO.setLogo("https://www.pexels.com");
		bookDTO.setActive("ACTIVE");
		bookDTO.setContent("This is a comic book");
		
		Book book = Book.builder().id(1).title("testTitle")
		.category(Category.valueOf("COMIC")).author("sekhar")
		.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
		.publishedDate("2022-09-22 23:34:41").active("ACTIVE").content("This is a comic book").build();
		 when( bookRepository.save(Book.builder().title("testTitle")
					.category(Category.valueOf("COMIC")).author("sekhar")
					.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
					.publishedDate("2022-09-22 23:34:41").active("ACTIVE").content("This is a comic book").build())).thenReturn(book);
	String	response = new JSONObject().put("response", "create book successfully").put("bookId", 1).toString();
			
       assertEquals(authorBookService.createBookByAuthor( authorid, bookDTO), response);
       
   }
	
	@Test
    void createbookFailure(){
		  int authorid=1;
		  Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
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
		
		Book book = Book.builder().id(1).title("testTitle")
		.category(Category.valueOf("COMIC")).author("sekhar")
		.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
		.publishedDate("2022-09-22 23:34:41").active("ACTIVE").content("This is a comic book").build();
		 when( bookRepository.save(Book.builder().title("testTitle")
					.category(Category.valueOf("COMIC")).author("sekhar")
					.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
					.publishedDate("2022-09-22 23:34:41").active("ACTIVE").content("This is a comic book").build())).thenReturn(book);
	String	response = new JSONObject().put("response", "create book failure").toString();
			
       assertEquals(authorBookService.createBookByAuthor( authorid, bookDTO), response);
       
   }
	
	@Test
    void editBookByAuthor(){
		  int authorid=1;
		  int bookId=1;
		  Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
		  EditBookDTO editBookDTO = new EditBookDTO();
			editBookDTO.setTitle("testTitle");
			editBookDTO.setCategory("COMIC");
			editBookDTO.setPrice(new BigDecimal("500"));
			editBookDTO.setPublisher("Rajesh");
			editBookDTO.setLogo("https://www.pexels.com");
			editBookDTO.setContent("This is a comic book");
		
			Object[] myArray = {"ironman","sekhar","COMIC","https://www.pexels.com",500.00,"2022-09-22 23:34:41","rajesh","it is comic book","active"};
			List<Object[]> list = bookRepository.checkExistUserAndBook(bookId, authorid);
			list.add(myArray);
			when(bookRepository.checkExistUserAndBook(bookId, authorid)).thenReturn(list);
			Book book = bookRepository.findById(bookId);
		 book = Book.builder().id(1).title("testTitle")
		.category(Category.valueOf("COMIC")).author("sekhar")
		.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
		.publishedDate("2022-09-22 23:34:41").active("ACTIVE").content("This is a comic book").build();
		when(bookRepository.findById(bookId)).thenReturn(book);
		book.setTitle("Bat-Man");
		Book editbook = Book.builder().id(1).title("Bat-Man")
				.category(Category.valueOf("COMIC")).author("sekhar")
				.price(new BigDecimal("500")).publisher("Rajesh").logo("https://www.pexels.com")
				.publishedDate("2022-09-22 23:34:41").active("ACTIVE").content("This is a comic book").build();
		when( bookRepository.save(book)).thenReturn(editbook);
		
	String	response = new JSONObject().put("response", "Edit the book Successfully").toString();
			
       assertEquals(authorBookService.editBookByAuthor( authorid,bookId, editBookDTO), response);
       
   }
	
	@Test
    void blockUnblockBookByAuthor(){
		  int authorid=1;
		  int bookId=1;
		String type="Block";
			Object[] myArray = {"ironman","sekhar","COMIC","https://www.pexels.com",500.00,"2022-09-22 23:34:41","rajesh","it is comic book","active"};
			List<Object[]> list = bookRepository.checkExistUserAndBook(bookId, authorid);
			list.add(myArray);
			when(bookRepository.checkExistUserAndBook(bookId, authorid)).thenReturn(list);
			
			bookRepository.blockUnblockBook(bookId,type);
		
	String	response = new JSONObject().put("response", type.concat("Successfully")).toString();
			
       assertEquals(authorBookService.blockUnblockBookByAuthor( authorid,bookId, type), response);
       
   }
	
	
	@Test
    void validateBookDTO(){
		  Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
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
		String response="Success";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
	
	@Test
    void validateBookDTOtest2(){
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("");
		String response="Title Should Not Be Empty";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
	@Test
    void validateBookDTOtest3(){
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("abcd");
		String response="Please Enter Valid Category";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
	@Test
    void validateBookDTOtest4(){
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("COMIC");
		bookDTO.setAuthor("sekhar");
		bookDTO.setPublisher("");
		String response="Publisher Should Not Be Empty";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
	
	@Test
    void validateBookDTOtest5(){
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("COMIC");
		bookDTO.setAuthor("sekhar");
		bookDTO.setPublisher("Rajesh");
		bookDTO.setPublishedDate("");
		String response="PublishedDate Should Not Be Empty";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
	
	@Test
    void validateBookDTOtest6(){
		Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("COMIC");
		bookDTO.setAuthor("sekhar");
		bookDTO.setPublisher("Rajesh");
		bookDTO.setPublishedDate(timestamp.toString());
		bookDTO.setLogo("");
		String response="Logo Should Not Be Empty";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
	
	@Test
    void validateBookDTOtest7(){
		Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("COMIC");
		bookDTO.setAuthor("sekhar");
		bookDTO.setPublisher("Rajesh");
		bookDTO.setPublishedDate(timestamp.toString());
		bookDTO.setLogo("https://www.pexels.com");
		bookDTO.setActive("");
		String response="Active Should Not Be Empty";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
	@Test
    void validateBookDTOtest8(){
		Timestamp timestamp = Timestamp.valueOf( "2022-09-25 12:12:05.638");
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("testTitle");
		bookDTO.setCategory("COMIC");
		bookDTO.setAuthor("sekhar");
		bookDTO.setPublisher("Rajesh");
		bookDTO.setPublishedDate(timestamp.toString());
		bookDTO.setLogo("https://www.pexels.com");
		bookDTO.setActive("ACTIVE");
		bookDTO.setContent("");
		String response="Content Should Not Be Empty";
		 assertEquals(authorBookService.validateBookDTO(bookDTO), response);
		
	}
}
