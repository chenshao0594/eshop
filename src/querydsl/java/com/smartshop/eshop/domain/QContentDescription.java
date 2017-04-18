package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentDescription is a Querydsl query type for ContentDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContentDescription extends EntityPathBase<ContentDescription> {

    private static final long serialVersionUID = 1593695774L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentDescription contentDescription = new QContentDescription("contentDescription");

    public final QContent content;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath metatagDescription = createString("metatagDescription");

    public final StringPath metatagKeywords = createString("metatagKeywords");

    public final StringPath metatagTitle = createString("metatagTitle");

    public final StringPath name = createString("name");

    public final StringPath seUrl = createString("seUrl");

    public final StringPath title = createString("title");

    public QContentDescription(String variable) {
        this(ContentDescription.class, forVariable(variable), INITS);
    }

    public QContentDescription(Path<? extends ContentDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentDescription(PathMetadata metadata, PathInits inits) {
        this(ContentDescription.class, metadata, inits);
    }

    public QContentDescription(Class<? extends ContentDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new QContent(forProperty("content"), inits.get("content")) : null;
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

