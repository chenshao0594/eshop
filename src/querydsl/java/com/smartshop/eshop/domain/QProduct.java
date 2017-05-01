package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 678476660L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final SetPath<ProductAttribute, QProductAttribute> attributes = this.<ProductAttribute, QProductAttribute>createSet("attributes", ProductAttribute.class, QProductAttribute.class, PathInits.DIRECT2);

    public final SetPath<ProductAvailability, QProductAvailability> availabilities = this.<ProductAvailability, QProductAvailability>createSet("availabilities", ProductAvailability.class, QProductAvailability.class, PathInits.DIRECT2);

    public final BooleanPath available = createBoolean("available");

    public final SetPath<Category, QCategory> categories = this.<Category, QCategory>createSet("categories", Category.class, QCategory.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> dateAvailable = createDate("dateAvailable", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final SetPath<ProductDescription, QProductDescription> descriptions = this.<ProductDescription, QProductDescription>createSet("descriptions", ProductDescription.class, QProductDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<ProductImage, QProductImage> images = this.<ProductImage, QProductImage>createSet("images", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final QManufacturer manufacturer;

    public final QMerchantStore merchantStore;

    public final StringPath metatagDescription = createString("metatagDescription");

    public final StringPath metatagKeywords = createString("metatagKeywords");

    public final StringPath metatagTitle = createString("metatagTitle");

    public final StringPath name = createString("name");

    public final BooleanPath preOrder = createBoolean("preOrder");

    public final StringPath productExternalDl = createString("productExternalDl");

    public final NumberPath<java.math.BigDecimal> productHeight = createNumber("productHeight", java.math.BigDecimal.class);

    public final StringPath productHighlight = createString("productHighlight");

    public final BooleanPath productIsFree = createBoolean("productIsFree");

    public final NumberPath<java.math.BigDecimal> productLength = createNumber("productLength", java.math.BigDecimal.class);

    public final SetPath<ProductOption, QProductOption> productOptions = this.<ProductOption, QProductOption>createSet("productOptions", ProductOption.class, QProductOption.class, PathInits.DIRECT2);

    public final NumberPath<Integer> productOrdered = createNumber("productOrdered", Integer.class);

    public final NumberPath<java.math.BigDecimal> productReviewAvg = createNumber("productReviewAvg", java.math.BigDecimal.class);

    public final NumberPath<Integer> productReviewCount = createNumber("productReviewCount", Integer.class);

    public final BooleanPath productShipeable = createBoolean("productShipeable");

    public final BooleanPath productVirtual = createBoolean("productVirtual");

    public final NumberPath<java.math.BigDecimal> productWeight = createNumber("productWeight", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> productWidth = createNumber("productWidth", java.math.BigDecimal.class);

    public final StringPath refSku = createString("refSku");

    public final SetPath<ProductRelationship, QProductRelationship> relationships = this.<ProductRelationship, QProductRelationship>createSet("relationships", ProductRelationship.class, QProductRelationship.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> retailPrice = createNumber("retailPrice", java.math.BigDecimal.class);

    public final StringPath searchUrl = createString("searchUrl");

    public final StringPath sku = createString("sku");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final NumberPath<java.math.BigDecimal> standardPrice = createNumber("standardPrice", java.math.BigDecimal.class);

    public final QTaxClass taxClass;

    public final StringPath title = createString("title");

    public final QProductType type;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.manufacturer = inits.isInitialized("manufacturer") ? new QManufacturer(forProperty("manufacturer"), inits.get("manufacturer")) : null;
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
        this.taxClass = inits.isInitialized("taxClass") ? new QTaxClass(forProperty("taxClass"), inits.get("taxClass")) : null;
        this.type = inits.isInitialized("type") ? new QProductType(forProperty("type")) : null;
    }

}

