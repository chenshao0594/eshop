package com.smartshop.eshop.config;

import java.util.concurrent.TimeUnit;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

	private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

	public CacheConfiguration(JHipsterProperties jHipsterProperties) {
		JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

		jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Object.class, Object.class,
						ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
				.withExpiry(
						Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
				.build());
	}

	@Bean
	public JCacheManagerCustomizer cacheManagerCustomizer() {
		return cm -> {
			cm.createCache(com.smartshop.eshop.domain.User.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Authority.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.User.class.getName() + ".authorities", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.PersistentToken.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Book.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Product.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Product.class.getName() + ".images", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Product.class.getName() + ".availabilities", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Product.class.getName() + ".attributes", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Product.class.getName() + ".descriptions", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Product.class.getName() + ".relationships", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderProductDownload.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOptionValue.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOptionValue.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOptionValue.class.getName() + ".productOption",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.TaxClass.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.TaxClass.class.getName() + ".products", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.TaxClass.class.getName() + ".taxRates", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ShoppingCartAttributeItem.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductPrice.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductPrice.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.GeoZone.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.GeoZone.class.getName() + ".countries", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.GeoZone.class.getName() + ".descriptions", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductType.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ManufacturerDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ShippingOrigin.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderAccountProduct.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CountryDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOptionValueDescription.class.getName(),
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderTotal.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.SystemNotification.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderProductAttribute.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderStatusHistory.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ContentDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductAvailability.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductAvailability.class.getName() + ".prices",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Optin.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOptionDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.TaxRate.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.TaxRate.class.getName() + ".descriptions", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.TaxRate.class.getName() + ".taxRates", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOption.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOption.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Transaction.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderProduct.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderProduct.class.getName() + ".downloads", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderProduct.class.getName() + ".orderAttributes",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderProduct.class.getName() + ".prices", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ShoppingCartItem.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ShoppingCartItem.class.getName() + ".attributes",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.MerchantStore.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.MerchantStore.class.getName() + ".languages",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.SystemConfiguration.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Currency.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.MerchantLog.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ShoppingCart.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ShoppingCart.class.getName() + ".lineItems", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductRelationship.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductReviewDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderAccount.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderAccount.class.getName() + ".orderAccountProducts",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.DigitalProduct.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.MerchantConfiguration.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ZoneDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOption.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOption.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOption.class.getName() + ".productOptionValues",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductOptionValueDescription.class.getName(),
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOptionValue.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOptionValue.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductImage.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductImage.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductPriceDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CategoryDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.GeoZoneDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOptionSet.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Content.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Content.class.getName() + ".descriptions", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOptionDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductImageDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.OrderProductPrice.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.FileHistory.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Language.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Language.class.getName() + ".storesDefaultLanguages",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Language.class.getName() + ".stores", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Customer.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Customer.class.getName() + ".attributes", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Customer.class.getName() + ".reviews", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductReview.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductReview.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Category.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Category.class.getName() + ".categories", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Category.class.getName() + ".descriptions", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerAttribute.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Country.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Country.class.getName() + ".descriptions", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Country.class.getName() + ".zones", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.IntegrationModule.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.CustomerOptin.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Manufacturer.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Manufacturer.class.getName() + ".descriptions",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Zone.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Zone.class.getName() + ".descriptions", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.ProductAttribute.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.TaxRateDescription.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.SalesOrder.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.SalesOrder.class.getName() + ".orderTotals", jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.SalesOrder.class.getName() + ".orderHistories",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.SalesOrder.class.getName() + ".orderProducts",
					jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.Template.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.EmailTemplate.class.getName(), jcacheConfiguration);
			cm.createCache(com.smartshop.eshop.domain.EmailSetting.class.getName(), jcacheConfiguration);
cm.createCache(com.smartshop.eshop.domain.Attachment.class.getName(), jcacheConfiguration);
			// jhipster-needle-ehcache-add-entry
		};
	}
}
