package com.shop.shop.controller;

import com.shop.shop.entity.User;
import com.shop.shop.entity.UserInformation;
import com.shop.shop.service.UserInformationService;
import com.shop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/information")
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserInformation> createInformation(@RequestBody UserInformation userInformation, @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());
        if (user != null && user.getUserInformation() == null){
            userInformation.setUser(user);
            userInformationService.save(userInformation);
            return ResponseEntity.ok(userInformation);
        }else return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getInformation(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());
        if (user != null && user.getUserInformation() != null){
            UserInformation userInformation = userInformationService.getInformation(user.getEmail());
            return ResponseEntity.status(200).body(userInformation);
        } else return ResponseEntity.status(404).body("User not found");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInformation(@PathVariable Long id, @RequestBody UserInformation userInformation,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        UserInformation oldUserInformation = userInformationService.getInformationById(id);
        if (oldUserInformation == null) {
            return ResponseEntity.status(404).body("User information not found");
        }

        oldUserInformation.setFirstName(userInformation.getFirstName());
        oldUserInformation.setLastName(userInformation.getLastName());
        oldUserInformation.setPhoneNumber(userInformation.getPhoneNumber());
        oldUserInformation.setStreet(userInformation.getStreet());
        oldUserInformation.setBuildingNumber(userInformation.getBuildingNumber());
        oldUserInformation.setLocalNumber(userInformation.getLocalNumber());
        oldUserInformation.setCity(userInformation.getCity());
        oldUserInformation.setZipCode(userInformation.getZipCode());

        userInformationService.save(oldUserInformation);

        return ResponseEntity.status(200).body(oldUserInformation);
    }
}
