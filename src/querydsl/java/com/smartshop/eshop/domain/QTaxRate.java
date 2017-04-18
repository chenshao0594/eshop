package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaxRate is a Querydsl query type for TaxRate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxRate extends EntityPathBase<TaxRate> {

    private static final long serialVersionUID = -545414736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaxRate taxRate1 = new QTaxRate("taxRate1");

    public final StringPath code = createString("code");

    public final QCountry country;

    public final SetPath<TaxRateDescription, QTaxRateDescription> descriptions = this.<TaxRateDescription, QTaxRateDescription>createSet("descriptions", TaxRateDescription.class, QTaxRateDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMerchantStore merchantStore;

    public final QTaxRate parent;

    public final BooleanPath piggyback = createBoolean("piggyback");

    public final StringPath stateProvince = createString("stateProvince");

    public final QTaxClass taxClass;

    public final NumberPath<Integer> taxPriority = createNumber("taxPriority", Integer.class);

    public final NumberPath<java.math.BigDecimal> taxRate = createNumber("taxRate", java.math.BigDecimal.class);

    public final SetPath<TaxRate, QTaxRate> taxRates = this.<TaxRate, QTaxRate>createSet("taxRates", TaxRate.class, QTaxRate.class, PathInits.DIRECT2);

    public final QZone zone;

    public QTaxRate(String variable) {
        this(TaxRate.class, forVariable(variable), INITS);
    }

    public QTaxRate(Path<? extends TaxRate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaxRate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaxRate(PathMetadata metadata, PathInits inits) {
        this(TaxRate.class, metadata, inits);
    }

    public QTaxRate(Class<? extends TaxRate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country"), inits.get("country")) : null;
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
        this.parent = inits.isInitialized("parent") ? new QTaxRate(forProperty("parent"), inits.get("parent")) : null;
        this.taxClass = inits.isInitialized("taxClass") ? new QTaxClass(forProperty("taxClass"), inits.get("taxClass")) : null;
        this.zone = inits.isInitialized("zone") ? new QZone(forProperty("zone"), inits.get("zone")) : null;
    }

}

