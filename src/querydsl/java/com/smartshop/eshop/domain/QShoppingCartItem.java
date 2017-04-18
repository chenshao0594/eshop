package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoppingCartItem is a Querydsl query type for ShoppingCartItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShoppingCartItem extends EntityPathBase<ShoppingCartItem> {

    private static final long serialVersionUID = 1169022710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShoppingCartItem shoppingCartItem = new QShoppingCartItem("shoppingCartItem");

    public final SetPath<ShoppingCartAttributeItem, QShoppingCartAttributeItem> attributes = this.<ShoppingCartAttributeItem, QShoppingCartAttributeItem>createSet("attributes", ShoppingCartAttributeItem.class, QShoppingCartAttributeItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final QShoppingCart shoppingCart;

    public QShoppingCartItem(String variable) {
        this(ShoppingCartItem.class, forVariable(variable), INITS);
    }

    public QShoppingCartItem(Path<? extends ShoppingCartItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShoppingCartItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShoppingCartItem(PathMetadata metadata, PathInits inits) {
        this(ShoppingCartItem.class, metadata, inits);
    }

    public QShoppingCartItem(Class<? extends ShoppingCartItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shoppingCart = inits.isInitialized("shoppingCart") ? new QShoppingCart(forProperty("shoppingCart"), inits.get("shoppingCart")) : null;
    }

}

