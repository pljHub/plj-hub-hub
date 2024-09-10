package org.msa.hub.domain.repository;

import org.msa.hub.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository {

    Page<Product> findAllByIsDeletedFalse(Pageable pageable);

    Product save(Product product);

    Optional<Product> findById(UUID productId);
}
