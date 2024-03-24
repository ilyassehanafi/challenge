package com.example.demo.controller;

import com.example.demo.dto.CreationDetail;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("/api/users")
@AllArgsConstructor
public class UsersController {

    private UserServiceImpl userService;

    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> generateUsers(@RequestParam int count) {
        List<UserDTO> users =  this.userService.generateUsers(count) ;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.json")
                .body(users);
    }

    @PostMapping(value = "/batch")
    public ResponseEntity<CreationDetail> saveUsers(@RequestBody MultipartFile file) {
        CreationDetail creationDetail = this.userService.saveUsers(file);
        return ResponseEntity.ok(new CreationDetail());
    }
}
