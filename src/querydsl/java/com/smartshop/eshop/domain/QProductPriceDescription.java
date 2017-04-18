package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPriceDescription is a Querydsl query type for ProductPriceDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductPriceDescription extends EntityPathBase<ProductPriceDescription> {

    private static final long serialVersionUID = 1379310215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPriceDescription productPriceDescription = new QProductPriceDescription("productPriceDescription");

    public final StringPath dEFAULTPRICEDESCRIPTION = createString("dEFAULTPRICEDESCRIPTION");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final QProductPrice productPrice;

    public final StringPath title = createString("title");

    public QProductPriceDescription(String variable) {
        this(ProductPriceDescription.class, forVariable(variable), INITS);
    }

    public QProductPriceDescription(Path<? extends ProductPriceDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPriceDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPriceDescription(PathMetadata metadata, PathInits inits) {
        this(ProductPriceDescription.class, metadata, inits);
    }

    public QProductPriceDescription(Class<? extends ProductPriceDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.productPrice = inits.isInitialized("productPrice") ? new QProductPrice(forProperty("productPrice"), inits.get("productPrice")) : null;
    }

}

