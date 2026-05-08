package dev.gabryel.ecommerce.service;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.dto.product.request.ProductRegisterRequest;
import dev.gabryel.ecommerce.dto.product.response.ProductRegisterResponse;
import dev.gabryel.ecommerce.exception.UserException;
import dev.gabryel.ecommerce.mapper.ProductMapper;
import dev.gabryel.ecommerce.model.ProductModel;
import dev.gabryel.ecommerce.model.UserModel;
import dev.gabryel.ecommerce.repository.ProductRepository;
import dev.gabryel.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProductRegisterResponse productRegister(JWTUserData userData, ProductRegisterRequest productRequest) {
        ProductModel productModel = ProductMapper.toProductModel(productRequest);
        UserModel userModel = userRepository.findByEmail(userData.email()).orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND.value()));
        productModel.setSellerName(userModel.getName());
        productRepository.save(productModel);
        return ProductMapper.toProductRegisterResponse(productModel);
    }
}
