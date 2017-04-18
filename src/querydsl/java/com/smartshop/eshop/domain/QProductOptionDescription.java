package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOptionDescription is a Querydsl query type for ProductOptionDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductOptionDescription extends EntityPathBase<ProductOptionDescription> {

    private static final long serialVersionUID = 222008115L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOptionDescription productOptionDescription = new QProductOptionDescription("productOptionDescription");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final QProductOption productOption;

    public final StringPath productOptionComment = createString("productOptionComment");

    public final StringPath title = createString("title");

    public QProductOptionDescription(String variable) {
        this(ProductOptionDescription.class, forVariable(variable), INITS);
    }

    public QProductOptionDescription(Path<? extends ProductOptionDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOptionDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOptionDescription(PathMetadata metadata, PathInits inits) {
        this(ProductOptionDescription.class, metadata, inits);
    }

    public QProductOptionDescription(Class<? extends ProductOptionDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.productOption = inits.isInitialized("productOption") ? new QProductOption(forProperty("productOption"), inits.get("productOption")) : null;
    }

}

