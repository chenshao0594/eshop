package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerOption is a Querydsl query type for CustomerOption
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerOption extends EntityPathBase<CustomerOption> {

    private static final long serialVersionUID = -567363634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerOption customerOption = new QCustomerOption("customerOption");

    public final BooleanPath active = createBoolean("active");

    public final StringPath code = createString("code");

    public final StringPath customerOptionType = createString("customerOptionType");

    public final SetPath<CustomerOptionDescription, QCustomerOptionDescription> descriptions = this.<CustomerOptionDescription, QCustomerOptionDescription>createSet("descriptions", CustomerOptionDescription.class, QCustomerOptionDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchantStore;

    public final BooleanPath publicOption = createBoolean("publicOption");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public QCustomerOption(String variable) {
        this(CustomerOption.class, forVariable(variable), INITS);
    }

    public QCustomerOption(Path<? extends CustomerOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerOption(PathMetadata metadata, PathInits inits) {
        this(CustomerOption.class, metadata, inits);
    }

    public QCustomerOption(Class<? extends CustomerOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

