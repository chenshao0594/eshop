package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderAccountProduct is a Querydsl query type for OrderAccountProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderAccountProduct extends EntityPathBase<OrderAccountProduct> {

    private static final long serialVersionUID = -921485323L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderAccountProduct orderAccountProduct = new QOrderAccountProduct("orderAccountProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrderAccount orderAccount;

    public final DatePath<java.time.LocalDate> orderAccountProductAccountedDate = createDate("orderAccountProductAccountedDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> orderAccountProductEndDate = createDate("orderAccountProductEndDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> orderAccountProductEot = createDate("orderAccountProductEot", java.time.LocalDate.class);

    public final NumberPath<Long> orderAccountProductId = createNumber("orderAccountProductId", Long.class);

    public final DatePath<java.time.LocalDate> orderAccountProductLastStatusDate = createDate("orderAccountProductLastStatusDate", java.time.LocalDate.class);

    public final NumberPath<Integer> orderAccountProductLastTransactionStatus = createNumber("orderAccountProductLastTransactionStatus", Integer.class);

    public final NumberPath<Integer> orderAccountProductPaymentFrequencyType = createNumber("orderAccountProductPaymentFrequencyType", Integer.class);

    public final DatePath<java.time.LocalDate> orderAccountProductStartDate = createDate("orderAccountProductStartDate", java.time.LocalDate.class);

    public final NumberPath<Integer> orderAccountProductStatus = createNumber("orderAccountProductStatus", Integer.class);

    public final QOrderProduct orderProduct;

    public QOrderAccountProduct(String variable) {
        this(OrderAccountProduct.class, forVariable(variable), INITS);
    }

    public QOrderAccountProduct(Path<? extends OrderAccountProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderAccountProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderAccountProduct(PathMetadata metadata, PathInits inits) {
        this(OrderAccountProduct.class, metadata, inits);
    }

    public QOrderAccountProduct(Class<? extends OrderAccountProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderAccount = inits.isInitialized("orderAccount") ? new QOrderAccount(forProperty("orderAccount"), inits.get("orderAccount")) : null;
        this.orderProduct = inits.isInitialized("orderProduct") ? new QOrderProduct(forProperty("orderProduct"), inits.get("orderProduct")) : null;
    }

}

