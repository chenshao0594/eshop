package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGeoZoneDescription is a Querydsl query type for GeoZoneDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGeoZoneDescription extends EntityPathBase<GeoZoneDescription> {

    private static final long serialVersionUID = 1798110586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGeoZoneDescription geoZoneDescription = new QGeoZoneDescription("geoZoneDescription");

    public final StringPath description = createString("description");

    public final QGeoZone geoZone;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public QGeoZoneDescription(String variable) {
        this(GeoZoneDescription.class, forVariable(variable), INITS);
    }

    public QGeoZoneDescription(Path<? extends GeoZoneDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGeoZoneDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGeoZoneDescription(PathMetadata metadata, PathInits inits) {
        this(GeoZoneDescription.class, metadata, inits);
    }

    public QGeoZoneDescription(Class<? extends GeoZoneDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.geoZone = inits.isInitialized("geoZone") ? new QGeoZone(forProperty("geoZone")) : null;
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

