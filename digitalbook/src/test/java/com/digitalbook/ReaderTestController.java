package com.digitalbook;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
	
	
//	@InjectMocks
//	private ReaderController readerController;
	
	@InjectMocks
	private TokenValidator tokenValidator;
	

	@Mock
	private JwtUtils jwtUtils;

	@Mock
	private UserRepository userRepository;
	@Test
	void testGet() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookId", 1);
		jsonObject.put("readerName", "kumar");
		jsonObject.put("readerEmailId", "kumar@gmail.com");
		String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrdW1hciIsImlhdCI6MTY2Mzg2MzM0OSwiZXhwIjoxNjYzOTEzMzQ5fQ.ZtK2ZhHsnCvB8-_0vlnSA5DZAn5QPXPM21KFP9xDOcEafIesoCqdshzc4CeDzunKpflLLOKe6WVi1N8mDehscg";
		String verifyToken = tokenValidator.verifyToken(token);
		System.out.println("verifyToken "+verifyToken);

	}
}
