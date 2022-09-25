package com.digitalbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.digitalbook.controller.AuthorController;
import com.digitalbook.repository.UserRepository;
import com.digitalbook.security.jwt.JwtUtils;
import com.digitalbook.service.AuthorBookService;
import com.digitalbook.service.TokenValidator;

@ExtendWith(MockitoExtension.class)
public class TokenValidatorTestController {

	@InjectMocks
	TokenValidator tokenValidator;
	@Mock
	JwtUtils jwtUtils;

	@Mock
	UserRepository userRepository;
	
	@Test
    void verifyToken(){
		  String token1="Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZWtoYXIiLCJpYXQiOjE2NjM5NjkwMzYsImV4cCI6MTY2NDAxOTAzNn0.vfFyKCp0fv2EazWJ-Tcu9vxfpi0aTPFzO192bAoczuVY6_x1IpmtqxDWCx-dsF5F3B138LM7vKT2fk4zQPjojg";
		  String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZWtoYXIiLCJpYXQiOjE2NjM5NjkwMzYsImV4cCI6MTY2NDAxOTAzNn0.vfFyKCp0fv2EazWJ-Tcu9vxfpi0aTPFzO192bAoczuVY6_x1IpmtqxDWCx-dsF5F3B138LM7vKT2fk4zQPjojg";
			
		 
		String userNameFromJwt = jwtUtils.getUserNameFromJwtToken(token);
		userNameFromJwt="kumar";
		 when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(userNameFromJwt);
		 Object[] myArray = {"kumar","ROLE_AUTHOR"};
		List<Object[]> userdetails = userRepository.getUserdetails(userNameFromJwt);
		userdetails.add(myArray);
		 when(userRepository.getUserdetails(userNameFromJwt)).thenReturn(userdetails);
		 JSONObject jsonObject = new JSONObject();
		 jsonObject.put("userName", "kumar");
			jsonObject.put("userRole", "ROLE_AUTHOR");
		  assertEquals(tokenValidator.verifyToken(token1), jsonObject.toString());
	}
	
}
