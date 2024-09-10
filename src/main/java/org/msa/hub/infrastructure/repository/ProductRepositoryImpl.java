package org.msa.hub.infrastructure.repository;

import org.msa.hub.domain.model.Product;
import org.msa.hub.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepositoryImpl extends JpaRepository<Product, UUID> , ProductRepository {

    @Override
    Page<Product> findAllByIsDeletedFalse(Pageable pageable);

    @Override
    Product save(Product product);
}
