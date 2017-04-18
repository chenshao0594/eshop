package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShippingOrigin is a Querydsl query type for ShippingOrigin
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShippingOrigin extends EntityPathBase<ShippingOrigin> {

    private static final long serialVersionUID = 1127587919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShippingOrigin shippingOrigin = new QShippingOrigin("shippingOrigin");

    public final BooleanPath active = createBoolean("active");

    public final StringPath address = createString("address");

    public final StringPath city = createString("city");

    public final QCountry country;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchantStore;

    public final StringPath postalCode = createString("postalCode");

    public final StringPath state = createString("state");

    public final QZone zone;

    public QShippingOrigin(String variable) {
        this(ShippingOrigin.class, forVariable(variable), INITS);
    }

    public QShippingOrigin(Path<? extends ShippingOrigin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShippingOrigin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShippingOrigin(PathMetadata metadata, PathInits inits) {
        this(ShippingOrigin.class, metadata, inits);
    }

    public QShippingOrigin(Class<? extends ShippingOrigin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country"), inits.get("country")) : null;
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
        this.zone = inits.isInitialized("zone") ? new QZone(forProperty("zone"), inits.get("zone")) : null;
    }

}

