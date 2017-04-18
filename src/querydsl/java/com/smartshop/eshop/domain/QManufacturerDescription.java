package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManufacturerDescription is a Querydsl query type for ManufacturerDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QManufacturerDescription extends EntityPathBase<ManufacturerDescription> {

    private static final long serialVersionUID = -529764368L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManufacturerDescription manufacturerDescription = new QManufacturerDescription("manufacturerDescription");

    public final DatePath<java.time.LocalDate> dateLastClick = createDate("dateLastClick", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final QManufacturer manufacturer;

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public final NumberPath<Integer> urlClicked = createNumber("urlClicked", Integer.class);

    public QManufacturerDescription(String variable) {
        this(ManufacturerDescription.class, forVariable(variable), INITS);
    }

    public QManufacturerDescription(Path<? extends ManufacturerDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManufacturerDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManufacturerDescription(PathMetadata metadata, PathInits inits) {
        this(ManufacturerDescription.class, metadata, inits);
    }

    public QManufacturerDescription(Class<? extends ManufacturerDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.manufacturer = inits.isInitialized("manufacturer") ? new QManufacturer(forProperty("manufacturer"), inits.get("manufacturer")) : null;
    }

}

