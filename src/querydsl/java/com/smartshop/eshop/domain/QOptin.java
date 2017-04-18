package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptin is a Querydsl query type for Optin
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOptin extends EntityPathBase<Optin> {

    private static final long serialVersionUID = -4741411L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptin optin = new QOptin("optin");

    public final StringPath code = createString("code");

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchant;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QOptin(String variable) {
        this(Optin.class, forVariable(variable), INITS);
    }

    public QOptin(Path<? extends Optin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptin(PathMetadata metadata, PathInits inits) {
        this(Optin.class, metadata, inits);
    }

    public QOptin(Class<? extends Optin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchant = inits.isInitialized("merchant") ? new QMerchantStore(forProperty("merchant"), inits.get("merchant")) : null;
    }

}

