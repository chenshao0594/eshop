package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProductPrice is a Querydsl query type for OrderProductPrice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderProductPrice extends EntityPathBase<OrderProductPrice> {

    private static final long serialVersionUID = -1610787763L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProductPrice orderProductPrice = new QOrderProductPrice("orderProductPrice");

    public final BooleanPath defaultPrice = createBoolean("defaultPrice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrderProduct orderProduct;

    public final NumberPath<java.math.BigDecimal> productPrice = createNumber("productPrice", java.math.BigDecimal.class);

    public final StringPath productPriceCode = createString("productPriceCode");

    public final StringPath productPriceName = createString("productPriceName");

    public final NumberPath<java.math.BigDecimal> productPriceSpecial = createNumber("productPriceSpecial", java.math.BigDecimal.class);

    public final DatePath<java.time.LocalDate> productPriceSpecialEndDate = createDate("productPriceSpecialEndDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> productPriceSpecialStartDate = createDate("productPriceSpecialStartDate", java.time.LocalDate.class);

    public QOrderProductPrice(String variable) {
        this(OrderProductPrice.class, forVariable(variable), INITS);
    }

    public QOrderProductPrice(Path<? extends OrderProductPrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProductPrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProductPrice(PathMetadata metadata, PathInits inits) {
        this(OrderProductPrice.class, metadata, inits);
    }

    public QOrderProductPrice(Class<? extends OrderProductPrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderProduct = inits.isInitialized("orderProduct") ? new QOrderProduct(forProperty("orderProduct"), inits.get("orderProduct")) : null;
    }

}

