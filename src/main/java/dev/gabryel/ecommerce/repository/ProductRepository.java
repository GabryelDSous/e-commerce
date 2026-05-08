package dev.gabryel.ecommerce.repository;

import dev.gabryel.ecommerce.model.ProductModel;
import dev.gabryel.ecommerce.model.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    List<ProductModel> findByNameContainingIgnoreCase(String name);
    List<ProductModel> findByDescriptionContainingIgnoreCase(String description);
    List<ProductModel> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<ProductModel> findByStatus(ProductStatus status);
    @Modifying
    @Query("""
            UPDATE ProductModel p
            SET p.stock = p.stock - :qty
            WHERE p.name = :name
            AND p.stock >= :qty
            """)
    int decreaseStock(@Param(value = "name") String name, @Param(value = "qty") int qty);
}
