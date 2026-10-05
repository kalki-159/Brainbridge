package com.brainbridge.service;

import com.brainbridge.dto.LoginRequest;
import com.brainbridge.dto.ProfileUpdateRequest;
import com.brainbridge.dto.RegisterRequest;
import com.brainbridge.entity.User;
import com.brainbridge.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setBio(request.getBio());
        user.setSkills(request.getSkills());
        user.setLearningGoals(request.getLearningGoals());
        user.setInterests(request.getInterests());
        user.setCollaborationPreferences(request.getCollaborationPreferences());

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return user;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User updateProfile(Long id, ProfileUpdateRequest request) {
        User user = getById(id);

        if (request.getName() != null) user.setName(request.getName().trim());
        if (request.getBio() != null) user.setBio(request.getBio());
        if (request.getSkills() != null) user.setSkills(request.getSkills());
        if (request.getLearningGoals() != null) user.setLearningGoals(request.getLearningGoals());
        if (request.getInterests() != null) user.setInterests(request.getInterests());
        if (request.getCollaborationPreferences() != null) {
            user.setCollaborationPreferences(request.getCollaborationPreferences());
        }

        return userRepository.save(user);
    }
}
