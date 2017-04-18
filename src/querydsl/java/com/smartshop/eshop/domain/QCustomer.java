package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = 1167876601L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomer customer = new QCustomer("customer");

    public final BooleanPath anonymous = createBoolean("anonymous");

    public final SetPath<CustomerAttribute, QCustomerAttribute> attributes = this.<CustomerAttribute, QCustomerAttribute>createSet("attributes", CustomerAttribute.class, QCustomerAttribute.class, PathInits.DIRECT2);

    public final com.smartshop.eshop.domain.common.QBilling billing;

    public final StringPath company = createString("company");

    public final DatePath<java.time.LocalDate> dateOfBirth = createDate("dateOfBirth", java.time.LocalDate.class);

    public final QLanguage defaultLanguage;

    public final com.smartshop.eshop.domain.common.QDelivery delivery;

    public final StringPath emailAddress = createString("emailAddress");

    public final EnumPath<com.smartshop.eshop.domain.enumeration.CustomerGender> gender = createEnum("gender", com.smartshop.eshop.domain.enumeration.CustomerGender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchantStore;

    public final StringPath nick = createString("nick");

    public final StringPath password = createString("password");

    public final SetPath<ProductReview, QProductReview> reviews = this.<ProductReview, QProductReview>createSet("reviews", ProductReview.class, QProductReview.class, PathInits.DIRECT2);

    public QCustomer(String variable) {
        this(Customer.class, forVariable(variable), INITS);
    }

    public QCustomer(Path<? extends Customer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomer(PathMetadata metadata, PathInits inits) {
        this(Customer.class, metadata, inits);
    }

    public QCustomer(Class<? extends Customer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.billing = inits.isInitialized("billing") ? new com.smartshop.eshop.domain.common.QBilling(forProperty("billing"), inits.get("billing")) : null;
        this.defaultLanguage = inits.isInitialized("defaultLanguage") ? new QLanguage(forProperty("defaultLanguage")) : null;
        this.delivery = inits.isInitialized("delivery") ? new com.smartshop.eshop.domain.common.QDelivery(forProperty("delivery"), inits.get("delivery")) : null;
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

