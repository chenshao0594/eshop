package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMerchantConfiguration is a Querydsl query type for MerchantConfiguration
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMerchantConfiguration extends EntityPathBase<MerchantConfiguration> {

    private static final long serialVersionUID = 1649254867L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMerchantConfiguration merchantConfiguration = new QMerchantConfiguration("merchantConfiguration");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath key = createString("key");

    public final EnumPath<com.smartshop.eshop.domain.enumeration.MerchantConfigurationEnum> merchantConfigurationType = createEnum("merchantConfigurationType", com.smartshop.eshop.domain.enumeration.MerchantConfigurationEnum.class);

    public final QMerchantStore merchantStore;

    public final StringPath value = createString("value");

    public QMerchantConfiguration(String variable) {
        this(MerchantConfiguration.class, forVariable(variable), INITS);
    }

    public QMerchantConfiguration(Path<? extends MerchantConfiguration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMerchantConfiguration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMerchantConfiguration(PathMetadata metadata, PathInits inits) {
        this(MerchantConfiguration.class, metadata, inits);
    }

    public QMerchantConfiguration(Class<? extends MerchantConfiguration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.merchantStore = inits.isInitialized("merchantStore") ? new QMerchantStore(forProperty("merchantStore"), inits.get("merchantStore")) : null;
    }

}

