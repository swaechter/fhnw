package ch.swaechter.eaf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/{username}")
    public User saveUser(@PathVariable String username) {
        return userService.saveUser(new User(username));
    }

    @PutMapping("/{id}/{username}")
    public User updateUser(@PathVariable Long id, @PathVariable String username) {
        User user = userService.getUser(id).get();
        user.setUsername(username);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
