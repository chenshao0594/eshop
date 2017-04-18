package com.smartshop.eshop.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBilling is a Querydsl query type for Billing
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QBilling extends BeanPath<Billing> {

    private static final long serialVersionUID = -968511725L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBilling billing = new QBilling("billing");

    public final StringPath address = createString("address");

    public final StringPath city = createString("city");

    public final StringPath company = createString("company");

    public final com.smartshop.eshop.domain.QCountry country;

    public final StringPath firstName = createString("firstName");

    public final StringPath lastName = createString("lastName");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath state = createString("state");

    public final StringPath telephone = createString("telephone");

    public final com.smartshop.eshop.domain.QZone zone;

    public QBilling(String variable) {
        this(Billing.class, forVariable(variable), INITS);
    }

    public QBilling(Path<? extends Billing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBilling(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBilling(PathMetadata metadata, PathInits inits) {
        this(Billing.class, metadata, inits);
    }

    public QBilling(Class<? extends Billing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new com.smartshop.eshop.domain.QCountry(forProperty("country"), inits.get("country")) : null;
        this.zone = inits.isInitialized("zone") ? new com.smartshop.eshop.domain.QZone(forProperty("zone"), inits.get("zone")) : null;
    }

}

