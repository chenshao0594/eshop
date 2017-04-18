package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOptionValueDescription is a Querydsl query type for ProductOptionValueDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductOptionValueDescription extends EntityPathBase<ProductOptionValueDescription> {

    private static final long serialVersionUID = 1825428724L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOptionValueDescription productOptionValueDescription = new QProductOptionValueDescription("productOptionValueDescription");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final QProductOptionValue productOptionValue;

    public final StringPath title = createString("title");

    public QProductOptionValueDescription(String variable) {
        this(ProductOptionValueDescription.class, forVariable(variable), INITS);
    }

    public QProductOptionValueDescription(Path<? extends ProductOptionValueDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOptionValueDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOptionValueDescription(PathMetadata metadata, PathInits inits) {
        this(ProductOptionValueDescription.class, metadata, inits);
    }

    public QProductOptionValueDescription(Class<? extends ProductOptionValueDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.productOptionValue = inits.isInitialized("productOptionValue") ? new QProductOptionValue(forProperty("productOptionValue"), inits.get("productOptionValue")) : null;
    }

}

