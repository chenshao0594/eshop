package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderAccount is a Querydsl query type for OrderAccount
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderAccount extends EntityPathBase<OrderAccount> {

    private static final long serialVersionUID = 899072922L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderAccount orderAccount = new QOrderAccount("orderAccount");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSalesOrder order;

    public final NumberPath<Integer> orderAccountBillDay = createNumber("orderAccountBillDay", Integer.class);

    public final DatePath<java.time.LocalDate> orderAccountEndDate = createDate("orderAccountEndDate", java.time.LocalDate.class);

    public final SetPath<OrderAccountProduct, QOrderAccountProduct> orderAccountProducts = this.<OrderAccountProduct, QOrderAccountProduct>createSet("orderAccountProducts", OrderAccountProduct.class, QOrderAccountProduct.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> orderAccountStartDate = createDate("orderAccountStartDate", java.time.LocalDate.class);

    public QOrderAccount(String variable) {
        this(OrderAccount.class, forVariable(variable), INITS);
    }

    public QOrderAccount(Path<? extends OrderAccount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderAccount(PathMetadata metadata, PathInits inits) {
        this(OrderAccount.class, metadata, inits);
    }

    public QOrderAccount(Class<? extends OrderAccount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QSalesOrder(forProperty("order"), inits.get("order")) : null;
    }

}

