package com.smartshop.eshop.repository;

import com.smartshop.core.salesorder.model.SalesOrderList;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.temp.SalesOrderCriteria;

public interface SalesOrderRepositoryCustom {

	SalesOrderList listByStore(MerchantStore store, SalesOrderCriteria criteria);

}
