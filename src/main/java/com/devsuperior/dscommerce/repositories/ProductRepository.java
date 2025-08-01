package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscommerce.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

    @Query("SELECT obj " +
            "FROM Product obj " +
            "WHERE UPPER(obj.name) LIKE UPPER(CONCAT(:name,'%'))")
    Page<Product> findByName(String name, Pageable pageable);

}
