package org.msa.hub.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.msa.hub.global.audit.AuditingEntity;
import org.msa.hub.application.dto.product.ProductRequestDTO;

import java.util.UUID;

@Entity
@Table(name = "p_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Product extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "hub_id", nullable = false)
    private Hub hub;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock", nullable = false)
    private int stock;

    // 상품 수정
    public void updateProduct(ProductRequestDTO productRequestDTO, Company company, Hub hub){
        this.name = productRequestDTO.getName();
        this.company = company;
        this.hub =hub;
        this.price = productRequestDTO.getPrice();
        this.stock = productRequestDTO.getStock();
    }

    // 상품 삭제 - 논리적 삭제
    public void deleteProduct(){
        this.setIsDeleted(true);
    }

    // 주문 수량만큼 재고 차감
    public void reduceProductStock(int quantity){
        this.stock = this.stock - quantity;
    }

    // 주문 수량만큼 재고 되돌리기
    public void returnProductStock(int quantity){
        this.stock = this.stock + quantity;
    }
}
