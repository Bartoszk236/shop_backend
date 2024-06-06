package com.shop.shop.controller;

import com.shop.shop.entity.Product;
import com.shop.shop.entity.User;
import com.shop.shop.service.ProductService;
import com.shop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        List<Product> products = productService.findAll();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Product product){
        User user = userService.findByEmail(userDetails.getUsername());

        if (!user.getRole().equals("admin")) return ResponseEntity.status(401).body("You aren't admin");

        productService.save(product);
        return ResponseEntity.status(200).body(product);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId){
        User user = userService.findByEmail(userDetails.getUsername());

        if (!user.getRole().equals("admin")) return ResponseEntity.status(401).body("You aren't admin");

        productService.deleteById(productId);
        return ResponseEntity.status(200).body("Product delete successful");
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long productId, @RequestBody Product product){
        User user = userService.findByEmail(userDetails.getUsername());

        if (!user.getRole().equals("admin")) return ResponseEntity.status(401).body("You aren't admin");

        productService.updateProductById(product, productId);
        return ResponseEntity.status(200).body(product);
    }
}
