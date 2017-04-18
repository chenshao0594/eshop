package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMerchantLog is a Querydsl query type for MerchantLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMerchantLog extends EntityPathBase<MerchantLog> {

    private static final long serialVersionUID = 1028116577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMerchantLog merchantLog = new QMerchantLog("merchantLog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath log = createString("log");

    public final StringPath module = createString("module");

    public final QMerchantStore store;

    public QMerchantLog(String variable) {
        this(MerchantLog.class, forVariable(variable), INITS);
    }

    public QMerchantLog(Path<? extends MerchantLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMerchantLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMerchantLog(PathMetadata metadata, PathInits inits) {
        this(MerchantLog.class, metadata, inits);
    }

    public QMerchantLog(Class<? extends MerchantLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QMerchantStore(forProperty("store"), inits.get("store")) : null;
    }

}

