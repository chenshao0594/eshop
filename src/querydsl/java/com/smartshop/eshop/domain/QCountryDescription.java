package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCountryDescription is a Querydsl query type for CountryDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCountryDescription extends EntityPathBase<CountryDescription> {

    private static final long serialVersionUID = -1957062943L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCountryDescription countryDescription = new QCountryDescription("countryDescription");

    public final QCountry country;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public QCountryDescription(String variable) {
        this(CountryDescription.class, forVariable(variable), INITS);
    }

    public QCountryDescription(Path<? extends CountryDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCountryDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCountryDescription(PathMetadata metadata, PathInits inits) {
        this(CountryDescription.class, metadata, inits);
    }

    public QCountryDescription(Class<? extends CountryDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country"), inits.get("country")) : null;
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

