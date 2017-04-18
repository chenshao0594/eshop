package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProductDownload is a Querydsl query type for OrderProductDownload
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderProductDownload extends EntityPathBase<OrderProductDownload> {

    private static final long serialVersionUID = -998762748L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProductDownload orderProductDownload = new QOrderProductDownload("orderProductDownload");

    public final NumberPath<Integer> downloadCount = createNumber("downloadCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxdays = createNumber("maxdays", Integer.class);

    public final QOrderProduct orderProduct;

    public final StringPath orderProductFilename = createString("orderProductFilename");

    public QOrderProductDownload(String variable) {
        this(OrderProductDownload.class, forVariable(variable), INITS);
    }

    public QOrderProductDownload(Path<? extends OrderProductDownload> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProductDownload(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProductDownload(PathMetadata metadata, PathInits inits) {
        this(OrderProductDownload.class, metadata, inits);
    }

    public QOrderProductDownload(Class<? extends OrderProductDownload> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderProduct = inits.isInitialized("orderProduct") ? new QOrderProduct(forProperty("orderProduct"), inits.get("orderProduct")) : null;
    }

}

