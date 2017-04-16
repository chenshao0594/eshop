import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EshopBookModule } from './book/book.module';
import { EshopProductModule } from './product/product.module';
import { EshopOrderProductDownloadModule } from './order-product-download/order-product-download.module';
import { EshopProductOptionValueModule } from './product-option-value/product-option-value.module';
import { EshopProductDescriptionModule } from './product-description/product-description.module';
import { EshopTaxClassModule } from './tax-class/tax-class.module';
import { EshopShoppingCartAttributeItemModule } from './shopping-cart-attribute-item/shopping-cart-attribute-item.module';
import { EshopProductPriceModule } from './product-price/product-price.module';
import { EshopGeoZoneModule } from './geo-zone/geo-zone.module';
import { EshopProductTypeModule } from './product-type/product-type.module';
import { EshopManufacturerDescriptionModule } from './manufacturer-description/manufacturer-description.module';
import { EshopShippingOriginModule } from './shipping-origin/shipping-origin.module';
import { EshopOrderAccountProductModule } from './order-account-product/order-account-product.module';
import { EshopCountryDescriptionModule } from './country-description/country-description.module';
import { EshopCustomerOptionValueDescriptionModule } from './customer-option-value-description/customer-option-value-description.module';
import { EshopOrderTotalModule } from './order-total/order-total.module';
import { EshopSystemNotificationModule } from './system-notification/system-notification.module';
import { EshopOrderProductAttributeModule } from './order-product-attribute/order-product-attribute.module';
import { EshopOrderStatusHistoryModule } from './order-status-history/order-status-history.module';
import { EshopContentDescriptionModule } from './content-description/content-description.module';
import { EshopProductAvailabilityModule } from './product-availability/product-availability.module';
import { EshopOptinModule } from './optin/optin.module';
import { EshopProductOptionDescriptionModule } from './product-option-description/product-option-description.module';
import { EshopTaxRateModule } from './tax-rate/tax-rate.module';
import { EshopCustomerOptionModule } from './customer-option/customer-option.module';
import { EshopTransactionModule } from './transaction/transaction.module';
import { EshopOrderProductModule } from './order-product/order-product.module';
import { EshopShoppingCartItemModule } from './shopping-cart-item/shopping-cart-item.module';
import { EshopMerchantStoreModule } from './merchant-store/merchant-store.module';
import { EshopSystemConfigurationModule } from './system-configuration/system-configuration.module';
import { EshopCurrencyModule } from './currency/currency.module';
import { EshopMerchantLogModule } from './merchant-log/merchant-log.module';
import { EshopShoppingCartModule } from './shopping-cart/shopping-cart.module';
import { EshopProductRelationshipModule } from './product-relationship/product-relationship.module';
import { EshopProductReviewDescriptionModule } from './product-review-description/product-review-description.module';
import { EshopOrderAccountModule } from './order-account/order-account.module';
import { EshopDigitalProductModule } from './digital-product/digital-product.module';
import { EshopMerchantConfigurationModule } from './merchant-configuration/merchant-configuration.module';
import { EshopZoneDescriptionModule } from './zone-description/zone-description.module';
import { EshopProductOptionModule } from './product-option/product-option.module';
import { EshopProductOptionValueDescriptionModule } from './product-option-value-description/product-option-value-description.module';
import { EshopCustomerOptionValueModule } from './customer-option-value/customer-option-value.module';
import { EshopProductImageModule } from './product-image/product-image.module';
import { EshopProductPriceDescriptionModule } from './product-price-description/product-price-description.module';
import { EshopCategoryDescriptionModule } from './category-description/category-description.module';
import { EshopGeoZoneDescriptionModule } from './geo-zone-description/geo-zone-description.module';
import { EshopCustomerOptionSetModule } from './customer-option-set/customer-option-set.module';
import { EshopContentModule } from './content/content.module';
import { EshopCustomerOptionDescriptionModule } from './customer-option-description/customer-option-description.module';
import { EshopProductImageDescriptionModule } from './product-image-description/product-image-description.module';
import { EshopOrderProductPriceModule } from './order-product-price/order-product-price.module';
import { EshopFileHistoryModule } from './file-history/file-history.module';
import { EshopLanguageModule } from './language/language.module';
import { EshopCustomerModule } from './customer/customer.module';
import { EshopProductReviewModule } from './product-review/product-review.module';
import { EshopCategoryModule } from './category/category.module';
import { EshopCustomerAttributeModule } from './customer-attribute/customer-attribute.module';
import { EshopCountryModule } from './country/country.module';
import { EshopIntegrationModuleModule } from './integration-module/integration-module.module';
import { EshopCustomerOptinModule } from './customer-optin/customer-optin.module';
import { EshopManufacturerModule } from './manufacturer/manufacturer.module';
import { EshopZoneModule } from './zone/zone.module';
import { EshopProductAttributeModule } from './product-attribute/product-attribute.module';
import { EshopTaxRateDescriptionModule } from './tax-rate-description/tax-rate-description.module';
import { EshopSalesOrderModule } from './sales-order/sales-order.module';
import { EshopTemplateModule } from './template/template.module';
import { EshopEmailTemplateModule } from './email-template/email-template.module';
import { EshopEmailSettingModule } from './email-setting/email-setting.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EshopBookModule,
        EshopProductModule,
        EshopOrderProductDownloadModule,
        EshopProductOptionValueModule,
        EshopProductDescriptionModule,
        EshopTaxClassModule,
        EshopShoppingCartAttributeItemModule,
        EshopProductPriceModule,
        EshopGeoZoneModule,
        EshopProductTypeModule,
        EshopManufacturerDescriptionModule,
        EshopShippingOriginModule,
        EshopOrderAccountProductModule,
        EshopCountryDescriptionModule,
        EshopCustomerOptionValueDescriptionModule,
        EshopOrderTotalModule,
        EshopSystemNotificationModule,
        EshopOrderProductAttributeModule,
        EshopOrderStatusHistoryModule,
        EshopContentDescriptionModule,
        EshopProductAvailabilityModule,
        EshopOptinModule,
        EshopProductOptionDescriptionModule,
        EshopTaxRateModule,
        EshopCustomerOptionModule,
        EshopTransactionModule,
        EshopOrderProductModule,
        EshopShoppingCartItemModule,
        EshopMerchantStoreModule,
        EshopSystemConfigurationModule,
        EshopCurrencyModule,
        EshopMerchantLogModule,
        EshopShoppingCartModule,
        EshopProductRelationshipModule,
        EshopProductReviewDescriptionModule,
        EshopOrderAccountModule,
        EshopDigitalProductModule,
        EshopMerchantConfigurationModule,
        EshopZoneDescriptionModule,
        EshopProductOptionModule,
        EshopProductOptionValueDescriptionModule,
        EshopCustomerOptionValueModule,
        EshopProductImageModule,
        EshopProductPriceDescriptionModule,
        EshopCategoryDescriptionModule,
        EshopGeoZoneDescriptionModule,
        EshopCustomerOptionSetModule,
        EshopContentModule,
        EshopCustomerOptionDescriptionModule,
        EshopProductImageDescriptionModule,
        EshopOrderProductPriceModule,
        EshopFileHistoryModule,
        EshopLanguageModule,
        EshopCustomerModule,
        EshopProductReviewModule,
        EshopCategoryModule,
        EshopCustomerAttributeModule,
        EshopCountryModule,
        EshopIntegrationModuleModule,
        EshopCustomerOptinModule,
        EshopManufacturerModule,
        EshopZoneModule,
        EshopProductAttributeModule,
        EshopTaxRateDescriptionModule,
        EshopSalesOrderModule,
        EshopTemplateModule,
        EshopEmailTemplateModule,
        EshopEmailSettingModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopEntityModule {}
