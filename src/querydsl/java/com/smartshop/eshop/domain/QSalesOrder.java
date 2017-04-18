package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalesOrder is a Querydsl query type for SalesOrder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSalesOrder extends EntityPathBase<SalesOrder> {

    private static final long serialVersionUID = 332848381L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalesOrder salesOrder = new QSalesOrder("salesOrder");

    public final com.smartshop.eshop.domain.common.QBilling billing;

    public final EnumPath<com.smartshop.eshop.domain.enumeration.OrderChannelEnum> channel = createEnum("channel", com.smartshop.eshop.domain.enumeration.OrderChannelEnum.class);

    public final BooleanPath confirmedAddress = createBoolean("confirmedAddress");

    public final QCurrency currency;

    public final NumberPath<java.math.BigDecimal> currencyValue = createNumber("currencyValue", java.math.BigDecimal.class);

    public final BooleanPath customerAgreement = createBoolean("customerAgreement");

    public final StringPath customerEmailAddress = createString("customerEmailAddress");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final DatePath<java.time.LocalDate> datePurchased = createDate("datePurchased", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final DatePath<java.time.LocalDate> lastModified = createDate("lastModified", java.time.LocalDate.class);

    public final StringPath locale = createString("locale");

    public final QMerchantStore merchant;

    public final DatePath<java.time.LocalDate> orderDateFinished = createDate("orderDateFinished", java.time.LocalDate.class);

    public final SetPath<OrderStatusHistory, QOrderStatusHistory> orderHistories = this.<OrderStatusHistory, QOrderStatusHistory>createSet("orderHistories", OrderStatusHistory.class, QOrderStatusHistory.class, PathInits.DIRECT2);

    public final SetPath<OrderProduct, QOrderProduct> orderProducts = this.<OrderProduct, QOrderProduct>createSet("orderProducts", OrderProduct.class, QOrderProduct.class, PathInits.DIRECT2);

    public final SetPath<OrderTotal, QOrderTotal> orderTotals = this.<OrderTotal, QOrderTotal>createSet("orderTotals", OrderTotal.class, QOrderTotal.class, PathInits.DIRECT2);

    public final EnumPath<com.smartshop.eshop.domain.enumeration.OrderEnum> orderType = createEnum("orderType", com.smartshop.eshop.domain.enumeration.OrderEnum.class);

    public final StringPath paymentModuleCode = createString("paymentModuleCode");

    public final EnumPath<com.smartshop.eshop.domain.enumeration.PaymentEnum> paymentType = createEnum("paymentType", com.smartshop.eshop.domain.enumeration.PaymentEnum.class);

    public final StringPath shippingModuleCode = createString("shippingModuleCode");

    public final EnumPath<com.smartshop.eshop.domain.enumeration.OrderStatusEnum> status = createEnum("status", com.smartshop.eshop.domain.enumeration.OrderStatusEnum.class);

    public final NumberPath<java.math.BigDecimal> total = createNumber("total", java.math.BigDecimal.class);

    public QSalesOrder(String variable) {
        this(SalesOrder.class, forVariable(variable), INITS);
    }

    public QSalesOrder(Path<? extends SalesOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalesOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalesOrder(PathMetadata metadata, PathInits inits) {
        this(SalesOrder.class, metadata, inits);
    }

    public QSalesOrder(Class<? extends SalesOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.billing = inits.isInitialized("billing") ? new com.smartshop.eshop.domain.common.QBilling(forProperty("billing"), inits.get("billing")) : null;
        this.currency = inits.isInitialized("currency") ? new QCurrency(forProperty("currency")) : null;
        this.merchant = inits.isInitialized("merchant") ? new QMerchantStore(forProperty("merchant"), inits.get("merchant")) : null;
    }

}

