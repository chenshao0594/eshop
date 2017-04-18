package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductDescription is a Querydsl query type for ProductDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductDescription extends EntityPathBase<ProductDescription> {

    private static final long serialVersionUID = 1853521896L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductDescription productDescription = new QProductDescription("productDescription");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath metatagDescription = createString("metatagDescription");

    public final StringPath metatagKeywords = createString("metatagKeywords");

    public final StringPath metatagTitle = createString("metatagTitle");

    public final StringPath name = createString("name");

    public final QProduct product;

    public final StringPath productExternalDl = createString("productExternalDl");

    public final StringPath productHighlight = createString("productHighlight");

    public final StringPath seUrl = createString("seUrl");

    public final StringPath title = createString("title");

    public QProductDescription(String variable) {
        this(ProductDescription.class, forVariable(variable), INITS);
    }

    public QProductDescription(Path<? extends ProductDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductDescription(PathMetadata metadata, PathInits inits) {
        this(ProductDescription.class, metadata, inits);
    }

    public QProductDescription(Class<? extends ProductDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

