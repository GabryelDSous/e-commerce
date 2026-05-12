package dev.gabryel.ecommerce.controller;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.dto.product.request.ProductRegisterRequest;
import dev.gabryel.ecommerce.dto.product.request.ProductUpdateRequest;
import dev.gabryel.ecommerce.dto.product.response.ProductListResponse;
import dev.gabryel.ecommerce.dto.product.response.ProductRegisterResponse;
import dev.gabryel.ecommerce.dto.product.response.ProductUpdateResponse;
import dev.gabryel.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-product")
    public ResponseEntity<ProductRegisterResponse> productRegister(@AuthenticationPrincipal JWTUserData userData,
                                                                   @RequestBody @Valid ProductRegisterRequest productRequest) {
        ProductRegisterResponse productResponse = productService.productRegister(userData, productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<ProductListResponse>> productListAll() {
        List<ProductListResponse> productListResponses = productService.productListAll();
        return ResponseEntity.ok(productListResponses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-product")
    public ResponseEntity<ProductUpdateResponse> productUpdate(@RequestParam(value = "id") UUID id,
                                                               @RequestBody @Valid ProductUpdateRequest productRequest) {
        ProductUpdateResponse productResponse = productService.productUpdate(id, productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-product")
    public ResponseEntity<String> productDelete(@RequestParam(value = "id") UUID id) {
        productService.productDelete(id);
        return ResponseEntity.ok("Successfully deleted");
    }
}
