package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductImageDescription is a Querydsl query type for ProductImageDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductImageDescription extends EntityPathBase<ProductImageDescription> {

    private static final long serialVersionUID = 286147381L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductImageDescription productImageDescription = new QProductImageDescription("productImageDescription");

    public final StringPath altTag = createString("altTag");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final QProductImage productImage;

    public final StringPath title = createString("title");

    public QProductImageDescription(String variable) {
        this(ProductImageDescription.class, forVariable(variable), INITS);
    }

    public QProductImageDescription(Path<? extends ProductImageDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductImageDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductImageDescription(PathMetadata metadata, PathInits inits) {
        this(ProductImageDescription.class, metadata, inits);
    }

    public QProductImageDescription(Class<? extends ProductImageDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.productImage = inits.isInitialized("productImage") ? new QProductImage(forProperty("productImage"), inits.get("productImage")) : null;
    }

}

