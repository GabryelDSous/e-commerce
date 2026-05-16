package dev.gabryel.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_purchases")
@Getter
@Setter
public class PurchaseModel implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
