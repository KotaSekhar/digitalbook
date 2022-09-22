package com.digitalbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.OrderRepository;
import com.digitalbook.repository.ReadresRepository;
import com.digitalbook.service.MyQueryRepositoryCustom;
import com.digitalbook.service.ReaderBookService;

@ExtendWith(MockitoExtension.class)
public class ReaderServiceTestController {

	@InjectMocks
	private ReaderBookService readerBookService;

	@Mock
	BookRepository bookRepository;

	@Mock
	MyQueryRepositoryCustom myQueryRepositoryCustom;

	@Mock
	OrderRepository orderRepository;

	@Test
	void buyABook() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 1);
		jsonObject.put("readerName", "kumar");
		jsonObject.put("readerEmailId", "kumar@gmail.com");
//		ResponseEntity<String>	response = new ResponseEntity<String>("Buy a book Successfully", HttpStatus.OK);
		String response = "failure";
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("response", response);
		List<Object[]> book = new ArrayList<>();
//		when(readerBookService.buyABook(jsonObject.toString())).thenReturn(jsonObject2.toString());
		String buyABook = readerBookService.buyABook(jsonObject.toString());
		System.out.println("buyABook=="+buyABook);
		assertEquals(readerBookService.buyABook(jsonObject.toString()), jsonObject2.toString());
	}

	@Test
	void readABook() {
		String response = "invalid user";
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("response", response);
		 assertEquals(readerBookService.readABook("kumar@gamil.com", 1), jsonObject2.toString());
	}

}
