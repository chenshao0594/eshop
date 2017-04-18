package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerOptionValueDescription is a Querydsl query type for CustomerOptionValueDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerOptionValueDescription extends EntityPathBase<CustomerOptionValueDescription> {

    private static final long serialVersionUID = 1021155769L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerOptionValueDescription customerOptionValueDescription = new QCustomerOptionValueDescription("customerOptionValueDescription");

    public final QCustomerOptionValue customerOptionValue;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public QCustomerOptionValueDescription(String variable) {
        this(CustomerOptionValueDescription.class, forVariable(variable), INITS);
    }

    public QCustomerOptionValueDescription(Path<? extends CustomerOptionValueDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerOptionValueDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerOptionValueDescription(PathMetadata metadata, PathInits inits) {
        this(CustomerOptionValueDescription.class, metadata, inits);
    }

    public QCustomerOptionValueDescription(Class<? extends CustomerOptionValueDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerOptionValue = inits.isInitialized("customerOptionValue") ? new QCustomerOptionValue(forProperty("customerOptionValue"), inits.get("customerOptionValue")) : null;
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

