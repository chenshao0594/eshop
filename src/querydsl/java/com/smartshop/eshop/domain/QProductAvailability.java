package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductAvailability is a Querydsl query type for ProductAvailability
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductAvailability extends EntityPathBase<ProductAvailability> {

    private static final long serialVersionUID = 1248499439L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductAvailability productAvailability = new QProductAvailability("productAvailability");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<ProductPrice, QProductPrice> prices = this.<ProductPrice, QProductPrice>createSet("prices", ProductPrice.class, QProductPrice.class, PathInits.DIRECT2);

    public final QProduct product;

    public final DatePath<java.time.LocalDate> productDateAvailable = createDate("productDateAvailable", java.time.LocalDate.class);

    public final BooleanPath productIsAlwaysFreeShipping = createBoolean("productIsAlwaysFreeShipping");

    public final NumberPath<Integer> productQuantity = createNumber("productQuantity", Integer.class);

    public final NumberPath<Integer> productQuantityOrderMax = createNumber("productQuantityOrderMax", Integer.class);

    public final NumberPath<Integer> productQuantityOrderMin = createNumber("productQuantityOrderMin", Integer.class);

    public final BooleanPath productStatus = createBoolean("productStatus");

    public final StringPath region = createString("region");

    public final StringPath regionVariant = createString("regionVariant");

    public QProductAvailability(String variable) {
        this(ProductAvailability.class, forVariable(variable), INITS);
    }

    public QProductAvailability(Path<? extends ProductAvailability> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductAvailability(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductAvailability(PathMetadata metadata, PathInits inits) {
        this(ProductAvailability.class, metadata, inits);
    }

    public QProductAvailability(Class<? extends ProductAvailability> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

