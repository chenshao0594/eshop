package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContent is a Querydsl query type for Content
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContent extends EntityPathBase<Content> {

    private static final long serialVersionUID = 1939481342L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContent content = new QContent("content");

    public final StringPath code = createString("code");

    public final EnumPath<com.smartshop.eshop.domain.enumeration.ContentPositionEnum> contentPosition = createEnum("contentPosition", com.smartshop.eshop.domain.enumeration.ContentPositionEnum.class);

    public final EnumPath<com.smartshop.eshop.domain.enumeration.ContentTypeEnum> contentType = createEnum("contentType", com.smartshop.eshop.domain.enumeration.ContentTypeEnum.class);

    public final SetPath<ContentDescription, QContentDescription> descriptions = this.<ContentDescription, QContentDescription>createSet("descriptions", ContentDescription.class, QContentDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchantStore;

    public final StringPath productGroup = createString("productGroup");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final BooleanPath visible = createBoolean("visible");

    public QContent(String variable) {
        this(Content.class, forVariable(variable), INITS);
    }

    public QContent(Path<? extends Content> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContent(PathMetadata metadata, PathInits inits) {
        this(Content.class, metadata, inits);
    }

    public QContent(Class<? extends Content> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

