package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDigitalProduct is a Querydsl query type for DigitalProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDigitalProduct extends EntityPathBase<DigitalProduct> {

    private static final long serialVersionUID = -1372353134L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDigitalProduct digitalProduct = new QDigitalProduct("digitalProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProduct product;

    public final StringPath productFileName = createString("productFileName");

    public QDigitalProduct(String variable) {
        this(DigitalProduct.class, forVariable(variable), INITS);
    }

    public QDigitalProduct(Path<? extends DigitalProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDigitalProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDigitalProduct(PathMetadata metadata, PathInits inits) {
        this(DigitalProduct.class, metadata, inits);
    }

    public QDigitalProduct(Class<? extends DigitalProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

