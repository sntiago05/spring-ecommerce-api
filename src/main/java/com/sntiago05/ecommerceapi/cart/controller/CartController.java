package com.sntiago05.ecommerceapi.cart.controller;

import com.sntiago05.ecommerceapi.cart.dto.CartItemRequest;
import com.sntiago05.ecommerceapi.cart.dto.CartItemUpdate;
import com.sntiago05.ecommerceapi.cart.dto.CartResponse;
import com.sntiago05.ecommerceapi.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByEmailWithItems());
    }

    @PostMapping
    public ResponseEntity<CartResponse> addItemToCart(@RequestBody @Valid CartItemRequest request) {
        return ResponseEntity.ok().body(cartService.addItemToCart(request));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<Void> updateQuantity(@PathVariable Long itemId, @RequestBody @Valid CartItemUpdate cartItemUpdate) {
        cartService.updateCartItem(cartItemUpdate, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
