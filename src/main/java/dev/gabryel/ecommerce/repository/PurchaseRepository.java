package dev.gabryel.ecommerce.repository;

import dev.gabryel.ecommerce.model.PurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseModel, UUID> {
}
