package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCountry is a Querydsl query type for Country
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCountry extends EntityPathBase<Country> {

    private static final long serialVersionUID = 1945781787L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCountry country = new QCountry("country");

    public final StringPath countryName = createString("countryName");

    public final SetPath<CountryDescription, QCountryDescription> descriptions = this.<CountryDescription, QCountryDescription>createSet("descriptions", CountryDescription.class, QCountryDescription.class, PathInits.DIRECT2);

    public final QGeoZone geoZone;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isoCode = createString("isoCode");

    public final BooleanPath supported = createBoolean("supported");

    public final SetPath<Zone, QZone> zones = this.<Zone, QZone>createSet("zones", Zone.class, QZone.class, PathInits.DIRECT2);

    public QCountry(String variable) {
        this(Country.class, forVariable(variable), INITS);
    }

    public QCountry(Path<? extends Country> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCountry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCountry(PathMetadata metadata, PathInits inits) {
        this(Country.class, metadata, inits);
    }

    public QCountry(Class<? extends Country> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.geoZone = inits.isInitialized("geoZone") ? new QGeoZone(forProperty("geoZone")) : null;
    }

}

