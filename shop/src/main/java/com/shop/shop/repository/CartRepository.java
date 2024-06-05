package com.shop.shop.repository;

import com.shop.shop.entity.Cart;
import com.shop.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
