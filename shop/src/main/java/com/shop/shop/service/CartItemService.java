package com.shop.shop.service;

import com.shop.shop.entity.CartItem;
import com.shop.shop.repository.CartItemRepository;
import com.shop.shop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItem getItemById(Long id){
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        return cartItem;
    }

    public CartItem save(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }
}
