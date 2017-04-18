package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDescription is a Querydsl query type for Description
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QDescription extends EntityPathBase<Description> {

    private static final long serialVersionUID = -1270634495L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDescription description1 = new QDescription("description1");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public QDescription(String variable) {
        this(Description.class, forVariable(variable), INITS);
    }

    public QDescription(Path<? extends Description> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDescription(PathMetadata metadata, PathInits inits) {
        this(Description.class, metadata, inits);
    }

    public QDescription(Class<? extends Description> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

