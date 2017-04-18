package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryDescription is a Querydsl query type for CategoryDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCategoryDescription extends EntityPathBase<CategoryDescription> {

    private static final long serialVersionUID = 772887331L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryDescription categoryDescription = new QCategoryDescription("categoryDescription");

    public final QCategory category;

    public final StringPath categoryHighlight = createString("categoryHighlight");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath metatagDescription = createString("metatagDescription");

    public final StringPath metatagKeywords = createString("metatagKeywords");

    public final StringPath metatagTitle = createString("metatagTitle");

    public final StringPath name = createString("name");

    public final StringPath seUrl = createString("seUrl");

    public final StringPath title = createString("title");

    public QCategoryDescription(String variable) {
        this(CategoryDescription.class, forVariable(variable), INITS);
    }

    public QCategoryDescription(Path<? extends CategoryDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryDescription(PathMetadata metadata, PathInits inits) {
        this(CategoryDescription.class, metadata, inits);
    }

    public QCategoryDescription(Class<? extends CategoryDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category"), inits.get("category")) : null;
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

