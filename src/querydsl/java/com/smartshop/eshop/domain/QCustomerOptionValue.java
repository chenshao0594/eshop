package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerOptionValue is a Querydsl query type for CustomerOptionValue
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerOptionValue extends EntityPathBase<CustomerOptionValue> {

    private static final long serialVersionUID = 2044435011L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerOptionValue customerOptionValue = new QCustomerOptionValue("customerOptionValue");

    public final StringPath code = createString("code");

    public final StringPath customerOptionValueImage = createString("customerOptionValueImage");

    public final SetPath<CustomerOptionValueDescription, QCustomerOptionValueDescription> descriptions = this.<CustomerOptionValueDescription, QCustomerOptionValueDescription>createSet("descriptions", CustomerOptionValueDescription.class, QCustomerOptionValueDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchantStore;

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public QCustomerOptionValue(String variable) {
        this(CustomerOptionValue.class, forVariable(variable), INITS);
    }

    public QCustomerOptionValue(Path<? extends CustomerOptionValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerOptionValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerOptionValue(PathMetadata metadata, PathInits inits) {
        this(CustomerOptionValue.class, metadata, inits);
    }

    public QCustomerOptionValue(Class<? extends CustomerOptionValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

