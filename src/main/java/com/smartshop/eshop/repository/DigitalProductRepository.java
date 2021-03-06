package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartshop.eshop.domain.DigitalProduct;

public interface DigitalProductRepository extends JpaRepository<DigitalProduct, Long> {

	@Query("select p from DigitalProduct p inner join fetch p.product pp inner join fetch pp.merchantStore ppm where ppm.id =?1 and pp.id = ?2")
	DigitalProduct findByProduct(Integer storeId, Long productId);

	@Override
	@Query("select p from DigitalProduct p inner join fetch p.product pp inner join fetch pp.merchantStore ppm where p.id = ?1")
	DigitalProduct findOne(Long id);

}
