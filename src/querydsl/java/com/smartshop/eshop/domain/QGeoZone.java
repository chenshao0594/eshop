package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGeoZone is a Querydsl query type for GeoZone
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGeoZone extends EntityPathBase<GeoZone> {

    private static final long serialVersionUID = 908395810L;

    public static final QGeoZone geoZone = new QGeoZone("geoZone");

    public final StringPath code = createString("code");

    public final SetPath<Country, QCountry> countries = this.<Country, QCountry>createSet("countries", Country.class, QCountry.class, PathInits.DIRECT2);

    public final SetPath<GeoZoneDescription, QGeoZoneDescription> descriptions = this.<GeoZoneDescription, QGeoZoneDescription>createSet("descriptions", GeoZoneDescription.class, QGeoZoneDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QGeoZone(String variable) {
        super(GeoZone.class, forVariable(variable));
    }

    public QGeoZone(Path<? extends GeoZone> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGeoZone(PathMetadata metadata) {
        super(GeoZone.class, metadata);
    }

}

