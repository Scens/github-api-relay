package brzezinski.rafal.githubapirelay.controller;

import brzezinski.rafal.githubapirelay.dto.UserDTO;
import brzezinski.rafal.githubapirelay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{login}")
    public UserDTO getUser(@PathVariable(value = "login") String login) {
        return userService.getUser(login);
    }
}
