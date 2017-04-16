package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.temp.SalesOrderCriteria;
import com.smartshop.eshop.temp.SalesOrderList;

public interface SalesOrderRepositoryCustom {

	SalesOrderList listByStore(MerchantStore store, SalesOrderCriteria criteria);

}
