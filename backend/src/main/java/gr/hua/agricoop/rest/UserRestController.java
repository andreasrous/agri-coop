package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.CooperativeService;
import gr.hua.agricoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{user_id}")
    public User getUser(@PathVariable Long user_id) {
        return userService.getUser(user_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/farmers")
    public List<User> getFarmers() {
        return userService.getFarmers();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public List<User> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.getUsers();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{user_id}")
    public ResponseEntity<User> editUser(@PathVariable Long user_id, @RequestBody User user) {
        User updatedUser = userService.editUser(user_id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{user_id}")
    public List<User> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return userService.getUsers();
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("/unprocessed-applications")
    public List<Cooperative> getUnprocessedApplications() {
        return cooperativeService.getUnprocessedApplications();
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("/{user_id}/processed-applications")
    public List<Cooperative> getProcessedApplications(@PathVariable Long user_id) {
        return cooperativeService.getProcessedApplications(user_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping(value = "/cooperative/{cooperative_id}/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public String checkApplication(@PathVariable Integer cooperative_id) {
        return cooperativeService.checkApplication(cooperative_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @PostMapping("/{user_id}/approve/{cooperative_id}")
    public List<Cooperative> approveApplication(@PathVariable Long user_id, @PathVariable Integer cooperative_id, @RequestBody Map<String, String> requestBody) {
        return cooperativeService.approveApplication(cooperative_id, user_id, requestBody.get("notes"));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @PostMapping("/{user_id}/reject/{cooperative_id}")
    public List<Cooperative> rejectApplication(@PathVariable Long user_id, @PathVariable Integer cooperative_id, @RequestBody Map<String, String> requestBody) {
        return cooperativeService.rejectApplication(cooperative_id, user_id, requestBody.get("notes"));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{user_id}/add-role/{role_id}")
    public ResponseEntity<?> addRole(@PathVariable Long user_id, @PathVariable Integer role_id) {
        return userService.addRole(user_id, role_id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{user_id}/remove-role/{role_id}")
    public ResponseEntity<?> removeRole(@PathVariable Long user_id, @PathVariable Integer role_id) {
        return userService.removeRole(user_id, role_id);
    }
}