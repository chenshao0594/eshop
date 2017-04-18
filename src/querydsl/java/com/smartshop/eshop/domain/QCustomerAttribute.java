package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerAttribute is a Querydsl query type for CustomerAttribute
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerAttribute extends EntityPathBase<CustomerAttribute> {

    private static final long serialVersionUID = 829001891L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerAttribute customerAttribute = new QCustomerAttribute("customerAttribute");

    public final QCustomer customer;

    public final QCustomerOption customerOption;

    public final QCustomerOptionValue customerOptionValue;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath textValue = createString("textValue");

    public QCustomerAttribute(String variable) {
        this(CustomerAttribute.class, forVariable(variable), INITS);
    }

    public QCustomerAttribute(Path<? extends CustomerAttribute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerAttribute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerAttribute(PathMetadata metadata, PathInits inits) {
        this(CustomerAttribute.class, metadata, inits);
    }

    public QCustomerAttribute(Class<? extends CustomerAttribute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.customerOption = inits.isInitialized("customerOption") ? new QCustomerOption(forProperty("customerOption"), inits.get("customerOption")) : null;
        this.customerOptionValue = inits.isInitialized("customerOptionValue") ? new QCustomerOptionValue(forProperty("customerOptionValue"), inits.get("customerOptionValue")) : null;
    }

}

