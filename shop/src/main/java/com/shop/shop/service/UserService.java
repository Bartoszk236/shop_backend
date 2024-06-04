package com.shop.shop.service;

import com.shop.shop.entity.User;
import com.shop.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Map<String, String> verificationTokens = new HashMap<>();

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public void createVerificationToken(User user, String token) {
        verificationTokens.put(token, user.getEmail());
    }

    public boolean validateVerificationToken(String token){
        String email = verificationTokens.get(token);

        if (email != null){
            User user = findByEmail(email);
            if (user != null) {
                user.setVerify(true);
                userRepository.save(user);
                verificationTokens.remove(token);
                return true;
            }
        }
        return false;

    }
}
