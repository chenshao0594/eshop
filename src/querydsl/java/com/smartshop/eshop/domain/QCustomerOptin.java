package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerOptin is a Querydsl query type for CustomerOptin
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerOptin extends EntityPathBase<CustomerOptin> {

    private static final long serialVersionUID = 258792607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerOptin customerOptin = new QCustomerOptin("customerOptin");

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final QOptin optin;

    public final DatePath<java.time.LocalDate> optinDate = createDate("optinDate", java.time.LocalDate.class);

    public final StringPath value = createString("value");

    public QCustomerOptin(String variable) {
        this(CustomerOptin.class, forVariable(variable), INITS);
    }

    public QCustomerOptin(Path<? extends CustomerOptin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerOptin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerOptin(PathMetadata metadata, PathInits inits) {
        this(CustomerOptin.class, metadata, inits);
    }

    public QCustomerOptin(Class<? extends CustomerOptin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.optin = inits.isInitialized("optin") ? new QOptin(forProperty("optin"), inits.get("optin")) : null;
    }

}

