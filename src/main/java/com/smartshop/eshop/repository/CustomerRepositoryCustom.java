package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.temp.CustomerCriteria;
import com.smartshop.eshop.temp.CustomerList;

public interface CustomerRepositoryCustom {
	CustomerList listByStore(MerchantStore store, CustomerCriteria criteria);

}
