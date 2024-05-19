package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.Role;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.repository.RoleRepository;
import gr.hua.agricoop.repository.UserRepository;
import gr.hua.agricoop.service.CooperativeService;
import gr.hua.agricoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CooperativeService cooperativeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public List<User> showUsers() {
        return userService.getUsers();
    }

    @PostMapping("/empty")
    public User addUser() {
        User user = new User("username", "email@hua.gr", "password");
        Role role = roleRepository.findById(1).orElse(null);

        if (role != null) {
            user.getRoles().add(role);
        }

        return userService.saveUser(user);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{user_id}")
    public User editUser(@PathVariable Long user_id, @RequestBody User user) {
        return userService.editUser(user_id, user);
    }

    @PostMapping("/new")
    public List<User> saveUser(@RequestBody User user) {
        Role role = roleRepository.findById(1).orElse(null);

        if (role != null) {
            user.getRoles().add(role);
        }

        userService.saveUser(user);
        return userService.getUsers();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{user_id}")
    public List<User> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return userService.getUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{user_id}")
    public User getUser(@PathVariable Long user_id) {
        return userService.getUser(user_id);
    }

    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/{user_id}/processed_applications")
    public List<Cooperative> getProcessedApplications(@PathVariable Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow();
        return user.getApplications();
    }

    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/unprocessed_applications")
    public List<Cooperative> getUnprocessedApplications() {
        return cooperativeService.getUnprocessedApplications();
    }

    @Secured("ROLE_MODERATOR")
    @GetMapping("/cooperative/{cooperative_id}/check")
    public String checkApplication(@PathVariable Integer cooperative_id) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        return cooperative.check();
    }

    @Secured("ROLE_MODERATOR")
    @PostMapping("/employee/{user_id}/{cooperative_id}/approve")
    public List<Cooperative> approveApplication(
            @PathVariable Long user_id,
            @PathVariable Integer cooperative_id,
            @RequestBody Map<String, String> requestBody
    ) {
        String notes = requestBody.get("notes");
        cooperativeService.approveApplication(cooperative_id, user_id, notes);
        return cooperativeService.getProcessedApplications(user_id);
    }

    @Secured("ROLE_MODERATOR")
    @PostMapping("/employee/{user_id}/{cooperative_id}/reject")
    public List<Cooperative> rejectApplication(
            @PathVariable Long user_id,
            @PathVariable Integer cooperative_id,
            @RequestBody Map<String, String> requestBody
    ) {
        String notes = requestBody.get("notes");
        cooperativeService.rejectApplication(cooperative_id, user_id, notes);
        return cooperativeService.getProcessedApplications(user_id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/role/delete/{user_id}/{role_id}")
    public ResponseEntity<?> deleteRoleFromUser(@PathVariable Long user_id, @PathVariable Integer role_id) {
        User user = userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).orElse(null);

        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }

        user.getRoles().remove(role);
        userService.editUser(user_id, user);

        return ResponseEntity.ok(user);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/role/add/{user_id}/{role_id}")
    public ResponseEntity<?> addRoleToUser(@PathVariable Long user_id, @PathVariable Integer role_id) {
        User user = userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).orElse(null);

        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }

        user.getRoles().add(role);
        userService.editUser(user_id, user);

        return ResponseEntity.ok(user);
    }
}