package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartshop.eshop.domain.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	@Override
	@Query("select p from ProductImage p left join fetch p.descriptions pd inner join fetch p.product pp inner join fetch pp.merchantStore ppm where p.id = ?1")
	ProductImage findOne(Long id);

}
