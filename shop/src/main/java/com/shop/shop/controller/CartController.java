package com.shop.shop.controller;

import com.shop.shop.entity.Cart;
import com.shop.shop.entity.CartItem;
import com.shop.shop.entity.Product;
import com.shop.shop.entity.User;
import com.shop.shop.service.CartItemService;
import com.shop.shop.service.CartService;
import com.shop.shop.service.ProductService;
import com.shop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null){
            return ResponseEntity.status(404).body("User not found");
        }

        Product product = productService.findById(productId);
        if (product == null){
            return ResponseEntity.status(404).body("Product not found");
        }

        CartItem cartItem = cartService.addItemToCart(user, product);

        return ResponseEntity.status(200).body(cartItem);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) return ResponseEntity.status(404).body("User not found");

        Cart cart = cartService.getCartByUser(user);
        if (cart == null) return ResponseEntity.status(404).body("Cart not found");

        List<CartItem> cartItems = cart.getCartItems();
        return ResponseEntity.status(200).body(cartItems);
    }

    @DeleteMapping("/delete/{itemCartId}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable Long itemCartId){
        cartService.deleteById(itemCartId);
        return ResponseEntity.status(200).body("Delete successful");
    }

    @PutMapping("/addItemQuantity/{itemCartId}")
    public ResponseEntity<?> addItemQuantity(@PathVariable Long itemCartId){
        CartItem cartItem = cartItemService.getItemById(itemCartId);
        int quantity = cartItem.getQuantity();
        cartItem.setQuantity(quantity + 1);
        cartItemService.save(cartItem);
        return ResponseEntity.status(200).body(cartItem);
    }

    @PutMapping("/subtractItemQuantity/{itemCartId}")
    public ResponseEntity<?> subtractItemQuantity(@PathVariable Long itemCartId){
        CartItem cartItem = cartItemService.getItemById(itemCartId);
        int quantity = cartItem.getQuantity();
        if (quantity > 1){
            cartItem.setQuantity(quantity - 1);
            cartItemService.save(cartItem);
            return ResponseEntity.status(200).body(cartItem);
        } else return ResponseEntity.status(400).body("Quantity equals 1");
    }
}
