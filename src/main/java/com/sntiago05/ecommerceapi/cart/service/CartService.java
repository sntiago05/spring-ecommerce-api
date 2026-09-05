package com.sntiago05.ecommerceapi.cart.service;

import com.sntiago05.ecommerceapi.cart.dto.CartResponse;
import com.sntiago05.ecommerceapi.cart.entity.Cart;
import com.sntiago05.ecommerceapi.cart.entity.CartItem;
import com.sntiago05.ecommerceapi.cart.exception.CartNotFoundException;
import com.sntiago05.ecommerceapi.cart.repository.CartRepository;
import com.sntiago05.ecommerceapi.product.entity.Product;
import com.sntiago05.ecommerceapi.product.service.ProductService;
import com.sntiago05.ecommerceapi.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    /**
     * Adds an item to the specified cart. If the item is already present in the cart, the quantity is updated.
     *
     * @param cartId    the ID of the cart to which the item will be added
     * @param productId the ID of the product to be added to the cart
     * @param quantity  the quantity of the product to be added
     * @return a {@code CartResponse} object representing the updated state of the cart
     * @throws CartNotFoundException    if the cart with the given ID does not exist
     * @throws ProductNotFoundException if the product with the given ID does not exist
     */
    @Transactional
    public CartResponse addItemTocart(String email, Long productId, Integer quantity) {
        Product product = productService.findById(productId);
        Cart cart = cartRepository.findByIdWithItems(cartId).orElseThrow(CartNotFoundException::new);
        CartItem item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElse(null);
        if (item == null) {
            cart.getItems().add(CartItem.builder().product(product).quantity(quantity).cart(cart).build());
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        return CartResponse.fromEntity(cart);
    }

    public Cart getCartByIdWithItems(Long cartId) {
        return cartRepository.findByIdWithItems(cartId).orElseThrow(CartNotFoundException::new);
    }

    public CartResponse getCartByEmailWithItems(String email) {
        return CartResponse.fromEntity(cartRepository.findByEmailWithItems(email).orElseThrow(CartNotFoundException::new));
    }

    public void initCartToUser(User user) {
        cartRepository.save(Cart.builder().items(new ArrayList<>()).user(user).build());
    }
}
