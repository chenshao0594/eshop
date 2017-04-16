package com.smartshop.eshop.temp;

import java.util.List;

import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.type.FileContentType;
import com.smartshop.eshop.type.OutputContentFile;

public interface ImageGet {

	public List<OutputContentFile> getImages(final String merchantStoreCode, FileContentType imageContentType)
			throws BusinessException;

}
