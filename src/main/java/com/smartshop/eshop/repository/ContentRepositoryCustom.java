package com.smartshop.eshop.repository;

import java.util.List;

import com.smartshop.eshop.domain.ContentDescription;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.enumeration.ContentTypeEnum;


public interface ContentRepositoryCustom {

	List<ContentDescription> listNameByType(List<ContentTypeEnum> contentType,
			MerchantStore store, Language language);

	ContentDescription getBySeUrl(MerchantStore store, String seUrl);
	

}
