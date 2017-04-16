package com.smartshop.eshop.temp;

import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.exception.BusinessException;

public interface ProductImageRemove extends ImageRemove {

	public void removeProductImage(ProductImage productImage) throws BusinessException;

	public void removeProductImages(Product product) throws BusinessException;

}
