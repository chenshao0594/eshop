package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaxClass is a Querydsl query type for TaxClass
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxClass extends EntityPathBase<TaxClass> {

    private static final long serialVersionUID = 258469544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaxClass taxClass = new QTaxClass("taxClass");

    public final StringPath code = createString("code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchantStore;

    public final SetPath<Product, QProduct> products = this.<Product, QProduct>createSet("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final SetPath<TaxRate, QTaxRate> taxRates = this.<TaxRate, QTaxRate>createSet("taxRates", TaxRate.class, QTaxRate.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QTaxClass(String variable) {
        this(TaxClass.class, forVariable(variable), INITS);
    }

    public QTaxClass(Path<? extends TaxClass> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaxClass(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaxClass(PathMetadata metadata, PathInits inits) {
        this(TaxClass.class, metadata, inits);
    }

    public QTaxClass(Class<? extends TaxClass> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

