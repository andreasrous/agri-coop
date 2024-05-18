package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

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
            userRepository.save(existingUser);
        }
        return existingUser;
    }

    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public List<User> getFarmersWithoutCooperative() {
        List<User> farmers = userRepository.findAll();
        farmers.removeIf(farmer-> farmer.getCooperative() != null);
        return farmers;
    }

    @Transactional
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
}

