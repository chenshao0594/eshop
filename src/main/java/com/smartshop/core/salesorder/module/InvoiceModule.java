package com.smartshop.core.salesorder.module;

import java.io.ByteArrayOutputStream;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.SalesOrder;

public interface InvoiceModule {
	ByteArrayOutputStream createInvoice(MerchantStore store, SalesOrder order, Language language) throws Exception;

}
