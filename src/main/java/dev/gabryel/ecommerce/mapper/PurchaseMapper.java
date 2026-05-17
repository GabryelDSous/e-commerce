package dev.gabryel.ecommerce.mapper;

import dev.gabryel.ecommerce.dto.purchase.response.PurchaseProductResponse;
import dev.gabryel.ecommerce.model.ProductModel;
import dev.gabryel.ecommerce.model.PurchaseModel;
import dev.gabryel.ecommerce.model.UserModel;

import java.time.LocalDateTime;

public class PurchaseMapper {
    public static PurchaseModel toPurchaseModel(UserModel userModel, ProductModel productModel) {
        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setUserId(userModel.getId());
        purchaseModel.setUserName(userModel.getName());
        purchaseModel.setProductId(productModel.getId());
        purchaseModel.setProductName(productModel.getName());
        purchaseModel.setPurchaseDate(LocalDateTime.now());
        return purchaseModel;
    }
    public static PurchaseProductResponse toPurchaseProductResponse(PurchaseModel purchaseModel) {
        return new PurchaseProductResponse(
                purchaseModel.getId(), purchaseModel.getProductName(), purchaseModel.getUserName(),
                purchaseModel.getPurchaseDate(), purchaseModel.getQuantity()
        );
    }
}
