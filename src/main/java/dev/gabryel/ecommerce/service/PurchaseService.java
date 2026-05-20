package dev.gabryel.ecommerce.service;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.dto.purchase.request.PurchaseProductRequest;
import dev.gabryel.ecommerce.dto.purchase.response.PurchaseProductResponse;
import dev.gabryel.ecommerce.exception.ProductException;
import dev.gabryel.ecommerce.exception.PurchaseException;
import dev.gabryel.ecommerce.exception.UserException;
import dev.gabryel.ecommerce.mapper.PurchaseMapper;
import dev.gabryel.ecommerce.model.ProductModel;
import dev.gabryel.ecommerce.model.PurchaseModel;
import dev.gabryel.ecommerce.model.UserModel;
import dev.gabryel.ecommerce.repository.ProductRepository;
import dev.gabryel.ecommerce.repository.PurchaseRepository;
import dev.gabryel.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(UserRepository userRepository, ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional
    public PurchaseProductResponse purchaseProduct(JWTUserData userData, PurchaseProductRequest purchaseRequest) {
        UserModel userModel = userRepository.findById(UUID.fromString(userData.userId()))
                .orElseThrow(() -> new UserException("Users not found", HttpStatus.NOT_FOUND.value()));
        ProductModel productModel = productRepository.findById(purchaseRequest.id())
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND.value()));
        int decreased = productRepository.decreaseStock(productModel.getName(), purchaseRequest.quantity());
        if (decreased == 0)
            throw new PurchaseException("Product cannot be purchased", HttpStatus.NOT_FOUND.value());
        PurchaseModel purchaseModel = PurchaseMapper.toPurchaseModel(userModel, productModel);
        purchaseModel.setUserModel(userModel);
        purchaseModel.setProductModel(productModel);
        purchaseModel.setQuantity(purchaseRequest.quantity());
        purchaseRepository.save(purchaseModel);
        return PurchaseMapper.toPurchaseProductResponse(purchaseModel);
    }

    public List<PurchaseProductResponse> purchaseListAll() {
        List<PurchaseModel> purchaseModels = purchaseRepository.findAll();
        if (purchaseModels.isEmpty())
            throw new PurchaseException("Product cannot be purchased", HttpStatus.NOT_FOUND.value());
        return purchaseModels.stream()
                .map(PurchaseMapper::toPurchaseProductResponse)
                .toList();
    }

    public List<PurchaseProductResponse> purchaseListByUserId(UUID userId) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Users not found", HttpStatus.NOT_FOUND.value()));;
        List<PurchaseModel> purchaseModels = purchaseRepository.findByUserModel(userModel);
        if (purchaseModels.isEmpty())
            throw new PurchaseException("Product cannot be purchased", HttpStatus.NOT_FOUND.value());
        return purchaseModels.stream()
                .map(PurchaseMapper::toPurchaseProductResponse)
                .toList();
    }

    public void purchaseDeleteById(UUID id) {
        PurchaseModel purchaseModel = purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Product cannot be purchased", HttpStatus.NOT_FOUND.value()));
        purchaseRepository.delete(purchaseModel);
    }
}
