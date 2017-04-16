package com.smartshop.eshop.repository;

import java.util.List;

import com.smartshop.eshop.domain.ContentDescription;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.enumeration.ContentType;


public interface ContentRepositoryCustom {

	List<ContentDescription> listNameByType(List<ContentType> contentType,
			MerchantStore store, Language language);

	ContentDescription getBySeUrl(MerchantStore store, String seUrl);
	

}
