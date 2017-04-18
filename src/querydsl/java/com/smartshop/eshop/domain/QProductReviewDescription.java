package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductReviewDescription is a Querydsl query type for ProductReviewDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductReviewDescription extends EntityPathBase<ProductReviewDescription> {

    private static final long serialVersionUID = -171177872L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductReviewDescription productReviewDescription = new QProductReviewDescription("productReviewDescription");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final QProductReview productReview;

    public final StringPath title = createString("title");

    public QProductReviewDescription(String variable) {
        this(ProductReviewDescription.class, forVariable(variable), INITS);
    }

    public QProductReviewDescription(Path<? extends ProductReviewDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductReviewDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductReviewDescription(PathMetadata metadata, PathInits inits) {
        this(ProductReviewDescription.class, metadata, inits);
    }

    public QProductReviewDescription(Class<? extends ProductReviewDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.productReview = inits.isInitialized("productReview") ? new QProductReview(forProperty("productReview"), inits.get("productReview")) : null;
    }

}

