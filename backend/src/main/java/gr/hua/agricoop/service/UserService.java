package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Role;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.repository.RoleRepository;
import gr.hua.agricoop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Transactional
    public List<User> getFarmers() {
        List<User> farmers = userRepository.findAll();
        Role role = roleRepository.findById(1).orElse(null);
        farmers.removeIf(farmer-> !farmer.getRoles().contains(role));
        return farmers;
    }

    @Transactional
    public User saveUser(User user) {
        roleRepository.findById(1).ifPresent(role -> user.getRoles().add(role));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User editUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setVat(user.getVat());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(encoder.encode(user.getPassword()));
            existingUser.setEmail(user.getEmail());
            existingUser.setRoles(user.getRoles());
            userRepository.save(existingUser);
        }
        return existingUser;
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public ResponseEntity<?> addRole(Long userId, Integer roleId) {
        User user = getUser(userId);
        Role role = roleRepository.findById(roleId).orElse(null);
        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }
        user.getRoles().add(role);
        editUser(userId, user);
        return ResponseEntity.ok(user);
    }

    @Transactional
    public ResponseEntity<?> removeRole(Long userId, Integer roleId) {
        User user = getUser(userId);
        Role role = roleRepository.findById(roleId).orElse(null);
        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }
        user.getRoles().remove(role);
        editUser(userId, user);
        return ResponseEntity.ok(user);
    }
}
