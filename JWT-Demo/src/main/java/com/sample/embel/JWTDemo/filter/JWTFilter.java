package com.sample.embel.JWTDemo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sample.embel.JWTDemo.service.CustomUserDetailsService;
import com.sample.embel.JWTDemo.util.JWTUtil;

@Component
public class JWTFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
	//	Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxOTMwNzIyNCwiaWF0IjoxNjE5MjcxMjI0fQ.WMUNCFi5S6hozmtd_dHsf98Q7U4gIKUTmmSA-O4mDKA
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
			userName = jwtUtil.extractUsername(token);
		}
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		 UserDetails userDetails = service.loadUserByUsername(userName);
		 if(jwtUtil.validateToken(token, userDetails)) {
			 
			 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					 userDetails, null, userDetails.getAuthorities());
			 usernamePasswordAuthenticationToken
			 .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);			 
		 }
		 filterChain.doFilter(request, response);
		}
		
	}

}
