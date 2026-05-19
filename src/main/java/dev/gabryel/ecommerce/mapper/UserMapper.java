package dev.gabryel.ecommerce.mapper;

import dev.gabryel.ecommerce.dto.user.request.UserRegisterRequest;
import dev.gabryel.ecommerce.dto.user.response.UserListResponse;
import dev.gabryel.ecommerce.model.PurchaseModel;
import dev.gabryel.ecommerce.model.UserModel;

public class UserMapper {

    public static UserModel toUserModel(UserRegisterRequest userRequest) {
        UserModel userModel = new UserModel();
        userModel.setEmail(userRequest.email());
        userModel.setName(userRequest.name());
        return userModel;
    }

    public static UserListResponse toUserListResponse(UserModel userModel) {
        return new UserListResponse(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail(),
                userModel.getRole().toString(),
                userModel.getProducts().stream().map(PurchaseModel::getProductName).toList()
        );
    }
}
