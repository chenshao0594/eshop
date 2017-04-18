package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoppingCartAttributeItem is a Querydsl query type for ShoppingCartAttributeItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShoppingCartAttributeItem extends EntityPathBase<ShoppingCartAttributeItem> {

    private static final long serialVersionUID = -746703028L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShoppingCartAttributeItem shoppingCartAttributeItem = new QShoppingCartAttributeItem("shoppingCartAttributeItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> productAttributeId = createNumber("productAttributeId", Long.class);

    public final QShoppingCartItem shoppingCartItem;

    public QShoppingCartAttributeItem(String variable) {
        this(ShoppingCartAttributeItem.class, forVariable(variable), INITS);
    }

    public QShoppingCartAttributeItem(Path<? extends ShoppingCartAttributeItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShoppingCartAttributeItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShoppingCartAttributeItem(PathMetadata metadata, PathInits inits) {
        this(ShoppingCartAttributeItem.class, metadata, inits);
    }

    public QShoppingCartAttributeItem(Class<? extends ShoppingCartAttributeItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shoppingCartItem = inits.isInitialized("shoppingCartItem") ? new QShoppingCartItem(forProperty("shoppingCartItem"), inits.get("shoppingCartItem")) : null;
    }

}

