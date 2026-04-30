package dev.gabryel.ecommerce.repository;

import dev.gabryel.ecommerce.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);
    List<UserModel> findByNameContainingIgnoreCase(String name);
}
