package com.example.demo.controller;

import com.example.demo.dto.AuthRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/")
public class AuthenticationController {


	@PostMapping
	public ResponseEntity<String> authenticate(@RequestBody AuthRequestDTO authRequestDTO){
		var jwtToken = 
		return ResponseEntity.ok().body("");
	}

}
