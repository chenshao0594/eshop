package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoppingCart is a Querydsl query type for ShoppingCart
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShoppingCart extends EntityPathBase<ShoppingCart> {

    private static final long serialVersionUID = -391689917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShoppingCart shoppingCart = new QShoppingCart("shoppingCart");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<ShoppingCartItem, QShoppingCartItem> lineItems = this.<ShoppingCartItem, QShoppingCartItem>createSet("lineItems", ShoppingCartItem.class, QShoppingCartItem.class, PathInits.DIRECT2);

    public final QMerchantStore merchantStore;

    public final StringPath shoppingCartCode = createString("shoppingCartCode");

    public QShoppingCart(String variable) {
        this(ShoppingCart.class, forVariable(variable), INITS);
    }

    public QShoppingCart(Path<? extends ShoppingCart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShoppingCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShoppingCart(PathMetadata metadata, PathInits inits) {
        this(ShoppingCart.class, metadata, inits);
    }

    public QShoppingCart(Class<? extends ShoppingCart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

