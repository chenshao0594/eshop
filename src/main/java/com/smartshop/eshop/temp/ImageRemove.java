package com.smartshop.eshop.temp;

import com.smartshop.eshop.exception.BusinessException;

public interface ImageRemove {

	public void removeImages(final String merchantStoreCode) throws BusinessException;

}
