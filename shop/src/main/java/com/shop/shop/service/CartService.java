package com.shop.shop.service;

import com.shop.shop.entity.Cart;
import com.shop.shop.entity.CartItem;
import com.shop.shop.entity.Product;
import com.shop.shop.entity.User;
import com.shop.shop.repository.CartItemRepository;
import com.shop.shop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItem addItemToCart(User user, Product product){
        Cart cart = user.getCart();
        if (cart == null){
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
        }

        return cartItemRepository.save(cartItem);

    }

    public Cart getCartByUser(User user){
        return cartRepository.findByUser(user);
    }

    public void deleteById(Long id){
        cartItemRepository.deleteById(id);
    }
}
