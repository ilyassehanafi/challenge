package com.example.demo.controller;

import com.example.demo.service.UserServiceImpl;
import com.example.demo.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController()
@AllArgsConstructor
public class GenerateUsersController {

    private UserServiceImpl userService;

    @GetMapping(value = "/api/users/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> generateUsers(@RequestParam int count) throws InterruptedException, ExecutionException {
        List<UserDTO> users =  this.userService.generateUsers(count) ;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.json");
        return ResponseEntity.ok().header(headers.toString()).body(users);
    }
}
