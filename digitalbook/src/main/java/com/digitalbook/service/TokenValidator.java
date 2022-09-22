package com.digitalbook.service;


import java.util.List;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.repository.UserRepository;
import com.digitalbook.security.jwt.JwtUtils;

@Service  
public class TokenValidator {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserRepository userRepository;
	
	public String verifyToken(String token)  {
		System.out.println("token===="+token);
		String[] splited = token.split(" ");
		token=splited[1];
		System.out.println("token====++++"+token);
		String userNameFromJwt = jwtUtils.getUserNameFromJwtToken(token);
		List<Object[]> userdetails = userRepository.getUserdetails(userNameFromJwt);
		JSONObject jsonObject = new JSONObject();
		for(Object[] obj:userdetails){
			jsonObject.put("userName", obj[0]);
			jsonObject.put("userRole", obj[1]);
		}
		System.out.println("jsonObject=="+jsonObject.toString());
		return jsonObject.toString();
		
	}

}
