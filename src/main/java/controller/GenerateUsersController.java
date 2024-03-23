package controller;

import dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import service.UserServiceImpl;

import java.util.List;

@RestController()
@AllArgsConstructor
public class GenerateUsersController {

    private UserServiceImpl userService;

    @GetMapping("/api/users/generate")
    public List<UserDTO> generateUsers(@PathVariable int count){
        List<UserDTO> users =  this.userService.generateUsers(count);
        return users;
    }
}
