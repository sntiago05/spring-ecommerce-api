package com.sntiago05.ecommerceapi.cart.service;

import com.sntiago05.ecommerceapi.cart.dto.CartItemRequest;
import com.sntiago05.ecommerceapi.cart.dto.CartItemUpdate;
import com.sntiago05.ecommerceapi.cart.dto.CartResponse;
import com.sntiago05.ecommerceapi.cart.entity.Cart;
import com.sntiago05.ecommerceapi.cart.entity.CartItem;
import com.sntiago05.ecommerceapi.cart.exception.CartItemNotFoundException;
import com.sntiago05.ecommerceapi.cart.exception.CartNotFoundException;
import com.sntiago05.ecommerceapi.cart.repository.CartRepository;
import com.sntiago05.ecommerceapi.config.CurrentUserService;
import com.sntiago05.ecommerceapi.product.entity.Product;
import com.sntiago05.ecommerceapi.product.service.ProductService;
import com.sntiago05.ecommerceapi.user.entity.User;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sntiago05.ecommerceapi.product.exceptions.ProductNotFoundException;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CurrentUserService currentUserService;

    /**
     * Adds an item to the current user's shopping cart. If the item already exists in the cart,
     * its quantity is updated; otherwise, the item is added as a new entry.
     *
     * @param request the request object containing the product ID and the quantity to be added to the cart
     * @return a {@code CartResponse} object representing the updated state of the cart
     * @throws CartNotFoundException    if the current user's cart is not found
     * @throws ProductNotFoundException if the specified product is not found
     */
    @Transactional
    public CartResponse addItemToCart(CartItemRequest request) {
        Product product = productService.findById(request.productId());
        Cart cart = cartRepository.findByEmailWithItems(currentUserService.getCurrentUserEmail()).orElseThrow(CartNotFoundException::new);
        CartItem item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(request.productId())).findFirst().orElse(null);
        if (item == null) {
            cart.getItems().add(CartItem.builder().product(product).quantity(request.quantity()).cart(cart).build());
        } else {
            item.setQuantity(item.getQuantity() + request.quantity());
        }
        return CartResponse.fromEntity(cart);
    }

    public Cart getCartEntityWithItemsByEmail() {
        String email = currentUserService.getCurrentUserEmail();
        return cartRepository.findByEmailWithItems(email).orElseThrow(CartNotFoundException::new);
    }

    public CartResponse getCartByEmailWithItems() {
        return CartResponse.fromEntity(getCartEntityWithItemsByEmail());
    }

    public void initCartToUser(User user) {
        cartRepository.save(Cart.builder().items(new ArrayList<>()).user(user).build());
    }

    @Transactional
    public void updateCartItem(CartItemUpdate cartItemUpdate, Long itemId) {
        getCartItem(itemId).setQuantity(cartItemUpdate.quantity());
    }
    @Transactional
    public void deleteItem(Long itemId) {
        CartItem cartItem = getCartItem(itemId);
        cartItem.getCart().getItems().remove(cartItem);
    }
    @Transactional
    public void clearCart() {
        getCartEntityWithItemsByEmail().getItems().clear();
    }

    private @NonNull CartItem getCartItem(Long itemId) {
        return getCartEntityWithItemsByEmail().getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst().orElseThrow(CartItemNotFoundException::new);
    }

}
