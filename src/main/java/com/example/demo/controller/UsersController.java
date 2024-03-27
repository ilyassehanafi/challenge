package com.example.demo.controller;

import com.example.demo.dto.CreationDetail;
import com.example.demo._service.UserServiceImpl;
import com.example.demo.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("api/users")
@AllArgsConstructor
public class UsersController {

    private UserServiceImpl userService;

    @GetMapping(value = "generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> generateUsers(@RequestParam int count) {
        List<UserDTO> users = this.userService.generateUsers(count);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.json")
                .body(users);
    }

    @PostMapping(value = "batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreationDetail> saveUsers(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.ok(new CreationDetail());
        }
        CreationDetail creationDetail = this.userService.saveUsers(file);

        return ResponseEntity.ok(creationDetail);
    }

    @GetMapping(value = "me")
    public ResponseEntity<UserDTO> viewActualUser() {
        return ResponseEntity.ok()
                .body(this.userService.getActualUser());
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDTO> viewProfil(@PathVariable String username) {
        UserDTO userDTO = this.userService.viewProfil(username);
        return ResponseEntity.ok().body(userDTO);
    }
}
