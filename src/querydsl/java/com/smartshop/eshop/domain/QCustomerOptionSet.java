package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerOptionSet is a Querydsl query type for CustomerOptionSet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerOptionSet extends EntityPathBase<CustomerOptionSet> {

    private static final long serialVersionUID = -1633627724L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerOptionSet customerOptionSet = new QCustomerOptionSet("customerOptionSet");

    public final QCustomerOption customerOption;

    public final QCustomerOptionValue customerOptionValue;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public QCustomerOptionSet(String variable) {
        this(CustomerOptionSet.class, forVariable(variable), INITS);
    }

    public QCustomerOptionSet(Path<? extends CustomerOptionSet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerOptionSet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerOptionSet(PathMetadata metadata, PathInits inits) {
        this(CustomerOptionSet.class, metadata, inits);
    }

    public QCustomerOptionSet(Class<? extends CustomerOptionSet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerOption = inits.isInitialized("customerOption") ? new QCustomerOption(forProperty("customerOption"), inits.get("customerOption")) : null;
        this.customerOptionValue = inits.isInitialized("customerOptionValue") ? new QCustomerOptionValue(forProperty("customerOptionValue"), inits.get("customerOptionValue")) : null;
    }

}

