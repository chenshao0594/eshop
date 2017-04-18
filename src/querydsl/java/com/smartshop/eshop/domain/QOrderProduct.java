package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProduct is a Querydsl query type for OrderProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderProduct extends EntityPathBase<OrderProduct> {

    private static final long serialVersionUID = 1766917724L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProduct orderProduct = new QOrderProduct("orderProduct");

    public final SetPath<OrderProductDownload, QOrderProductDownload> downloads = this.<OrderProductDownload, QOrderProductDownload>createSet("downloads", OrderProductDownload.class, QOrderProductDownload.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> oneTimeCharge = createNumber("oneTimeCharge", java.math.BigDecimal.class);

    public final QSalesOrder order;

    public final SetPath<OrderProductAttribute, QOrderProductAttribute> orderAttributes = this.<OrderProductAttribute, QOrderProductAttribute>createSet("orderAttributes", OrderProductAttribute.class, QOrderProductAttribute.class, PathInits.DIRECT2);

    public final SetPath<OrderProductPrice, QOrderProductPrice> prices = this.<OrderProductPrice, QOrderProductPrice>createSet("prices", OrderProductPrice.class, QOrderProductPrice.class, PathInits.DIRECT2);

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> productQuantity = createNumber("productQuantity", Integer.class);

    public final StringPath sku = createString("sku");

    public QOrderProduct(String variable) {
        this(OrderProduct.class, forVariable(variable), INITS);
    }

    public QOrderProduct(Path<? extends OrderProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProduct(PathMetadata metadata, PathInits inits) {
        this(OrderProduct.class, metadata, inits);
    }

    public QOrderProduct(Class<? extends OrderProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QSalesOrder(forProperty("order"), inits.get("order")) : null;
    }

}

