package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaxRateDescription is a Querydsl query type for TaxRateDescription
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxRateDescription extends EntityPathBase<TaxRateDescription> {

    private static final long serialVersionUID = -2145084372L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaxRateDescription taxRateDescription = new QTaxRateDescription("taxRateDescription");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath name = createString("name");

    public final QTaxRate taxRate;

    public final StringPath title = createString("title");

    public QTaxRateDescription(String variable) {
        this(TaxRateDescription.class, forVariable(variable), INITS);
    }

    public QTaxRateDescription(Path<? extends TaxRateDescription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaxRateDescription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaxRateDescription(PathMetadata metadata, PathInits inits) {
        this(TaxRateDescription.class, metadata, inits);
    }

    public QTaxRateDescription(Class<? extends TaxRateDescription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
        this.taxRate = inits.isInitialized("taxRate") ? new QTaxRate(forProperty("taxRate"), inits.get("taxRate")) : null;
    }

}

