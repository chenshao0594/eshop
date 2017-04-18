package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerOptionDescription is a Querydsl query type for CustomerOptionDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerOptionDescription extends EntityPathBase<CustomerOptionDescription> {

    private static final long serialVersionUID = 1138435918L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerOptionDescription customerOptionDescription = new QCustomerOptionDescription("customerOptionDescription");

    public final QCustomerOption customerOption;

    public final StringPath customerOptionComment = createString("customerOptionComment");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final StringPath title = createString("title");

    public QCustomerOptionDescription(String variable) {
        this(CustomerOptionDescription.class, forVariable(variable), INITS);
    }

    public QCustomerOptionDescription(Path<? extends CustomerOptionDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerOptionDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerOptionDescription(PathMetadata metadata, PathInits inits) {
        this(CustomerOptionDescription.class, metadata, inits);
    }

    public QCustomerOptionDescription(Class<? extends CustomerOptionDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerOption = inits.isInitialized("customerOption") ? new QCustomerOption(forProperty("customerOption"), inits.get("customerOption")) : null;
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

