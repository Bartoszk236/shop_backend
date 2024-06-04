package com.shop.shop.service;

import com.shop.shop.entity.User;
import com.shop.shop.entity.UserInformation;
import com.shop.shop.repository.UserInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private UserService userService;

    public void save(UserInformation userInformation){
        userInformationRepository.save(userInformation);
    }

    public UserInformation getInformation(String email){
        User user = userService.findByEmail(email);
        return user.getUserInformation();
    }

    public UserInformation getInformationById(Long id){
        UserInformation userInformation = userInformationRepository.getById(id);
        return userInformation;
    }
}
