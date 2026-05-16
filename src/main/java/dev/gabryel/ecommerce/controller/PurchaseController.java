package dev.gabryel.ecommerce.controller;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.dto.purchase.request.PurchaseProductRequest;
import dev.gabryel.ecommerce.dto.purchase.response.PurchaseProductResponse;
import dev.gabryel.ecommerce.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseProductResponse> purchaseProduct(@AuthenticationPrincipal JWTUserData userData,
                                                                   @RequestBody @Valid PurchaseProductRequest purchaseRequest) {
        PurchaseProductResponse response = purchaseService.purchaseProduct(userData, purchaseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
