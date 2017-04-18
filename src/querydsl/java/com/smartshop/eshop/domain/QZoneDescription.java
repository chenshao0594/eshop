package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZoneDescription is a Querydsl query type for ZoneDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QZoneDescription extends EntityPathBase<ZoneDescription> {

    private static final long serialVersionUID = 593485365L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZoneDescription zoneDescription = new QZoneDescription("zoneDescription");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public final QZone zone;

    public QZoneDescription(String variable) {
        this(ZoneDescription.class, forVariable(variable), INITS);
    }

    public QZoneDescription(Path<? extends ZoneDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZoneDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZoneDescription(PathMetadata metadata, PathInits inits) {
        this(ZoneDescription.class, metadata, inits);
    }

    public QZoneDescription(Class<? extends ZoneDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.zone = inits.isInitialized("zone") ? new QZone(forProperty("zone"), inits.get("zone")) : null;
    }

}

