package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSystemNotification is a Querydsl query type for SystemNotification
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSystemNotification extends EntityPathBase<SystemNotification> {

    private static final long serialVersionUID = -63128299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSystemNotification systemNotification = new QSystemNotification("systemNotification");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath key = createString("key");

    public final QMerchantStore merchantStore;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final QUser user;

    public final StringPath value = createString("value");

    public QSystemNotification(String variable) {
        this(SystemNotification.class, forVariable(variable), INITS);
    }

    public QSystemNotification(Path<? extends SystemNotification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSystemNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSystemNotification(PathMetadata metadata, PathInits inits) {
        this(SystemNotification.class, metadata, inits);
    }

    public QSystemNotification(Class<? extends SystemNotification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

