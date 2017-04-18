package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderTotal is a Querydsl query type for OrderTotal
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderTotal extends EntityPathBase<OrderTotal> {

    private static final long serialVersionUID = -624718863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderTotal orderTotal = new QOrderTotal("orderTotal");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath module = createString("module");

    public final QSalesOrder order;

    public final StringPath orderTotalCode = createString("orderTotalCode");

    public final EnumPath<com.smartshop.eshop.domain.enumeration.OrderTotalEnum> orderTotalType = createEnum("orderTotalType", com.smartshop.eshop.domain.enumeration.OrderTotalEnum.class);

    public final EnumPath<com.smartshop.eshop.domain.enumeration.OrderValueEnum> orderValueType = createEnum("orderValueType", com.smartshop.eshop.domain.enumeration.OrderValueEnum.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final StringPath text = createString("text");

    public final StringPath title = createString("title");

    public final NumberPath<java.math.BigDecimal> value = createNumber("value", java.math.BigDecimal.class);

    public QOrderTotal(String variable) {
        this(OrderTotal.class, forVariable(variable), INITS);
    }

    public QOrderTotal(Path<? extends OrderTotal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderTotal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderTotal(PathMetadata metadata, PathInits inits) {
        this(OrderTotal.class, metadata, inits);
    }

    public QOrderTotal(Class<? extends OrderTotal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QSalesOrder(forProperty("order"), inits.get("order")) : null;
    }

}

