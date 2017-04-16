package com.smartshop.eshop.temp;

import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.type.ImageContentFile;

public interface ProductImagePut {

	public void addProductImage(ProductImage productImage, ImageContentFile contentImage) throws BusinessException;

}
