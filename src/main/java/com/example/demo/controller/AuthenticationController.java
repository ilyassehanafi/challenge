package com.example.demo.controller;

import com.example.demo.dto.AuthRequestDTO;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo._service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/")
@AllArgsConstructor
public class AuthenticationController {

	private AuthenticationService authenticationService;

	@PostMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO){
		var jwtToken = authenticationService.login(authRequestDTO.getUsername(),authRequestDTO.getPassword());
		AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
				.accessToken(jwtToken)
				.build();
		return ResponseEntity.ok().body(authResponseDTO);
	}
	}
