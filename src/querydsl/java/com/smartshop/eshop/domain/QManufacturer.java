package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManufacturer is a Querydsl query type for Manufacturer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QManufacturer extends EntityPathBase<Manufacturer> {

    private static final long serialVersionUID = -782991252L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManufacturer manufacturer = new QManufacturer("manufacturer");

    public final StringPath code = createString("code");

    public final SetPath<ManufacturerDescription, QManufacturerDescription> descriptions = this.<ManufacturerDescription, QManufacturerDescription>createSet("descriptions", ManufacturerDescription.class, QManufacturerDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final QMerchantStore merchantStore;

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public QManufacturer(String variable) {
        this(Manufacturer.class, forVariable(variable), INITS);
    }

    public QManufacturer(Path<? extends Manufacturer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManufacturer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManufacturer(PathMetadata metadata, PathInits inits) {
        this(Manufacturer.class, metadata, inits);
    }

    public QManufacturer(Class<? extends Manufacturer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

