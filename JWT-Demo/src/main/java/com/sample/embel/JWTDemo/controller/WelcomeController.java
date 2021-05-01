package com.sample.embel.JWTDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.embel.JWTDemo.entity.RequestAuthUserDetails;
import com.sample.embel.JWTDemo.util.JWTUtil;

@RestController
public class WelcomeController {
	
	@Autowired
	private JWTUtil jwtutil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@GetMapping("/")
	public String welcome() {
		
		return "Welcome to Embel";
	}
	
	@PostMapping("/authenticate") 
	public String generateToken(@RequestBody RequestAuthUserDetails authRequest) throws Exception {
		try {
			
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
			);
		}catch(Exception e) {
			
			throw new Exception("Invalide UserName and Password");
		}	
		return jwtutil.generateToken(authRequest.getUsername())	;
	}

}
