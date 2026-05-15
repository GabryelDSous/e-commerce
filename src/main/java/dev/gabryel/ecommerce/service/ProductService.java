package dev.gabryel.ecommerce.service;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.dto.product.request.ProductFindByPriceRequest;
import dev.gabryel.ecommerce.dto.product.request.ProductRegisterRequest;
import dev.gabryel.ecommerce.dto.product.request.ProductUpdateRequest;
import dev.gabryel.ecommerce.dto.product.response.*;
import dev.gabryel.ecommerce.exception.ProductException;
import dev.gabryel.ecommerce.exception.UserException;
import dev.gabryel.ecommerce.mapper.ProductMapper;
import dev.gabryel.ecommerce.model.ProductModel;
import dev.gabryel.ecommerce.model.UserModel;
import dev.gabryel.ecommerce.model.enums.ProductStatus;
import dev.gabryel.ecommerce.repository.ProductRepository;
import dev.gabryel.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<ProductListResponse> productListAll() {
        List<ProductModel> productModels = productRepository.findAll();
        if (productModels.isEmpty())
            throw new ProductException("Products not found", HttpStatus.NOT_FOUND.value());
        return productModels.stream()
                .map(ProductMapper::toProductListResponse)
                .toList();
    }

    public List<ProductListByAttributeResponse> productListByName(String name) {
        List<ProductModel> productModels = productRepository.findByNameContainingIgnoreCase(name);
        if (productModels.isEmpty())
            throw new ProductException("Products not found", HttpStatus.NOT_FOUND.value());
        return productModels.stream()
                .map(ProductMapper::toProductListByAttribute)
                .toList();
    }

    public List<ProductListByAttributeResponse> productListByDescription(String description) {
        List<ProductModel> productModels = productRepository.findByDescriptionContainingIgnoreCase(description);
        if (productModels.isEmpty())
            throw new ProductException("Products not found", HttpStatus.NOT_FOUND.value());
        return productModels.stream()
                .map(ProductMapper::toProductListByAttribute)
                .toList();
    }

    public List<ProductListByAttributeResponse> productListByPrice(ProductFindByPriceRequest productRequest) {
        List<ProductModel> productModels = productRepository.findByPriceBetweenOrderByPriceAsc(productRequest.minPrice(), productRequest.maxPrice());
        if (productModels.isEmpty())
            throw new ProductException("Products not found", HttpStatus.NOT_FOUND.value());
        return productModels.stream()
                .map(ProductMapper::toProductListByAttribute)
                .toList();
    }

    public List<ProductListByAttributeResponse> productListByStatus(String status) {
        List<ProductModel> productModels = productRepository.findByStatus(ProductStatus.valueOf(status.toUpperCase()));
        if (productModels.isEmpty())
            throw new ProductException("Products not found", HttpStatus.NOT_FOUND.value());
        return productModels.stream()
                .map(ProductMapper::toProductListByAttribute)
                .toList();
    }

    @Transactional
    public ProductUpdateResponse productUpdate(UUID id, ProductUpdateRequest productRequest) {
        ProductModel productModel = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND.value()));
        productModel.setName(productRequest.name());
        productModel.setDescription(productRequest.description());
        productModel.setPrice(productRequest.price());
        productModel.setSellerName(productRequest.sellerName());
        productModel.setStock(productRequest.stock());
        return ProductMapper.toProductUpdateResponse(productModel);
    }

    public void productDelete(UUID id) {
        ProductModel productModel = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND.value()));
        productRepository.delete(productModel);
    }
}
