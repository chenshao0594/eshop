package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMerchantStore is a Querydsl query type for MerchantStore
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMerchantStore extends EntityPathBase<MerchantStore> {

    private static final long serialVersionUID = 184177342L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMerchantStore merchantStore = new QMerchantStore("merchantStore");

    public final StringPath code = createString("code");

    public final StringPath continueshoppingurl = createString("continueshoppingurl");

    public final QCountry country;

    public final QCurrency currency;

    public final BooleanPath currencyFormatNational = createBoolean("currencyFormatNational");

    public final QLanguage defaultLanguage;

    public final StringPath domainName = createString("domainName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.util.Date> inBusinessSince = createDate("inBusinessSince", java.util.Date.class);

    public final StringPath invoiceTemplate = createString("invoiceTemplate");

    public final SetPath<Language, QLanguage> languages = this.<Language, QLanguage>createSet("languages", Language.class, QLanguage.class, PathInits.DIRECT2);

    public final StringPath seizeunitcode = createString("seizeunitcode");

    public final StringPath storeaddress = createString("storeaddress");

    public final StringPath storecity = createString("storecity");

    public final StringPath storeEmailAddress = createString("storeEmailAddress");

    public final StringPath storeLogo = createString("storeLogo");

    public final StringPath storename = createString("storename");

    public final StringPath storephone = createString("storephone");

    public final StringPath storepostalcode = createString("storepostalcode");

    public final StringPath storestateprovince = createString("storestateprovince");

    public final StringPath storeTemplate = createString("storeTemplate");

    public final BooleanPath useCache = createBoolean("useCache");

    public final StringPath weightunitcode = createString("weightunitcode");

    public final QZone zone;

    public QMerchantStore(String variable) {
        this(MerchantStore.class, forVariable(variable), INITS);
    }

    public QMerchantStore(Path<? extends MerchantStore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMerchantStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMerchantStore(PathMetadata metadata, PathInits inits) {
        this(MerchantStore.class, metadata, inits);
    }

    public QMerchantStore(Class<? extends MerchantStore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country"), inits.get("country")) : null;
        this.currency = inits.isInitialized("currency") ? new QCurrency(forProperty("currency")) : null;
        this.defaultLanguage = inits.isInitialized("defaultLanguage") ? new QLanguage(forProperty("defaultLanguage")) : null;
        this.zone = inits.isInitialized("zone") ? new QZone(forProperty("zone"), inits.get("zone")) : null;
    }

}

