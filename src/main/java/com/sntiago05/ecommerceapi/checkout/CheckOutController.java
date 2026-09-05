package com.sntiago05.ecommerceapi.checkout;

import com.sntiago05.ecommerceapi.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckOutController {
    private final CheckOutService service;

    @PostMapping("{cartId}")
    public ResponseEntity<OrderResponse> oncheckOut(@PathVariable Long cartId) {
        OrderResponse response = service.processCheckOut(cartId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
