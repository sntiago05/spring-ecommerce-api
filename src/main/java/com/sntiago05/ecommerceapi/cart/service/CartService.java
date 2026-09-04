package com.sntiago05.ecommerceapi.cart.service;

import com.sntiago05.ecommerceapi.cart.entity.Cart;
import com.sntiago05.ecommerceapi.cart.entity.CartItem;
import com.sntiago05.ecommerceapi.cart.exception.CartNotFoundException;
import com.sntiago05.ecommerceapi.cart.repository.CartRepository;
import com.sntiago05.ecommerceapi.product.entity.Product;
import com.sntiago05.ecommerceapi.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    @Transactional
    public Cart addItemTocart(Long cartId, Long productId, Integer quantity) {
        Product product = productService.findById(productId);
        Cart cart = cartRepository.findByIdWithItems(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        CartItem item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElse(null);
        if (item == null) {
            cart.getItems().add(CartItem.builder().product(product).quantity(quantity).cart(cart).build());
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        return cart;
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findByIdWithItems(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
    }
}
