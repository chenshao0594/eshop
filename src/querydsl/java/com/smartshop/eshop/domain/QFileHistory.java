package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileHistory is a Querydsl query type for FileHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFileHistory extends EntityPathBase<FileHistory> {

    private static final long serialVersionUID = -1015363715L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFileHistory fileHistory = new QFileHistory("fileHistory");

    public final DatePath<java.time.LocalDate> accountedDate = createDate("accountedDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> dateAdded = createDate("dateAdded", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> dateDeleted = createDate("dateDeleted", java.time.LocalDate.class);

    public final NumberPath<Integer> downloadCount = createNumber("downloadCount", Integer.class);

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public final NumberPath<Integer> filesize = createNumber("filesize", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore store;

    public QFileHistory(String variable) {
        this(FileHistory.class, forVariable(variable), INITS);
    }

    public QFileHistory(Path<? extends FileHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFileHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFileHistory(PathMetadata metadata, PathInits inits) {
        this(FileHistory.class, metadata, inits);
    }

    public QFileHistory(Class<? extends FileHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QMerchantStore(forProperty("store"), inits.get("store")) : null;
    }

}

