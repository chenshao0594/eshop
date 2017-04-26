package com.smartshop.eshop.common;

import java.util.Currency;
import java.util.Locale;

/**
 * Application constants.
 */
public final class BusinessConstants {
	public final static String TEST_ENVIRONMENT = "TEST";
	public final static String PRODUCTION_ENVIRONMENT = "PROD";

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String ANONYMOUS_USER = "anonymoususer";
	public final static String DISTANCE_KEY = "DISTANCE_KEY";

	public static final String ALL_REGIONS = "*";

	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public final static String DEFAULT_DATE_FORMAT_YEAR = "yyyy";
	public final static String DEFAULT_LANGUAGE = "en";

	public final static String EMAIL_CONFIG = "EMAIL_CONFIG";
	// public final static String MERCHANT_CONFIG = "MERCHANT_CONFIG";

	public final static String UNDERSCORE = "_";
	public final static String SLASH = "/";
	public final static String TRUE = "true";
	public final static String FALSE = "false";
	public final static String OT_ITEM_PRICE_MODULE_CODE = "itemprice";
	public final static String OT_SUBTOTAL_MODULE_CODE = "subtotal";
	public final static String OT_TOTAL_MODULE_CODE = "total";
	public final static String OT_SHIPPING_MODULE_CODE = "shipping";
	public final static String OT_HANDLING_MODULE_CODE = "handling";
	public final static String OT_TAX_MODULE_CODE = "tax";
	public final static String OT_REFUND_MODULE_CODE = "refund";
	public final static String OT_DISCOUNT_TITLE = "order.total.discount";

	public final static Locale DEFAULT_LOCALE = Locale.US;
	public final static Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.US);

	private BusinessConstants() {
	}
}
