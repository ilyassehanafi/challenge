package controller;

import dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController("/api/users")
@AllArgsConstructor
public class GenerateUsersController {

    private UserService userService;

    @GetMapping("/generate")
    public ResponseEntity<UserDTO> generateUsers(@PathVariable int count){
        this.userService.generateUsers(count);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
