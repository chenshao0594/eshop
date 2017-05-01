package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPrice is a Querydsl query type for ProductPrice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductPrice extends EntityPathBase<ProductPrice> {

    private static final long serialVersionUID = -2107961803L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPrice productPrice = new QProductPrice("productPrice");

    public final StringPath code = createString("code");

    public final BooleanPath defaultPrice = createBoolean("defaultPrice");

    public final StringPath defaultPriceCode = createString("defaultPriceCode");

    public final SetPath<ProductPriceDescription, QProductPriceDescription> descriptions = this.<ProductPriceDescription, QProductPriceDescription>createSet("descriptions", ProductPriceDescription.class, QProductPriceDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProductAvailability productAvailability;

    public final NumberPath<java.math.BigDecimal> productPriceAmount = createNumber("productPriceAmount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> productPriceSpecialAmount = createNumber("productPriceSpecialAmount", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> productPriceSpecialEndDate = createDateTime("productPriceSpecialEndDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> productPriceSpecialStartDate = createDateTime("productPriceSpecialStartDate", java.util.Date.class);

    public final EnumPath<com.smartshop.eshop.domain.enumeration.ProductPriceEnum> productPriceType = createEnum("productPriceType", com.smartshop.eshop.domain.enumeration.ProductPriceEnum.class);

    public QProductPrice(String variable) {
        this(ProductPrice.class, forVariable(variable), INITS);
    }

    public QProductPrice(Path<? extends ProductPrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPrice(PathMetadata metadata, PathInits inits) {
        this(ProductPrice.class, metadata, inits);
    }

    public QProductPrice(Class<? extends ProductPrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productAvailability = inits.isInitialized("productAvailability") ? new QProductAvailability(forProperty("productAvailability"), inits.get("productAvailability")) : null;
    }

}

