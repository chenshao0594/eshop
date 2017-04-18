package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLanguage is a Querydsl query type for Language
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLanguage extends EntityPathBase<Language> {

    private static final long serialVersionUID = -1051888269L;

    public static final QLanguage language = new QLanguage("language");

    public final StringPath code = createString("code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final SetPath<MerchantStore, QMerchantStore> stores = this.<MerchantStore, QMerchantStore>createSet("stores", MerchantStore.class, QMerchantStore.class, PathInits.DIRECT2);

    public final SetPath<MerchantStore, QMerchantStore> storesDefaultLanguages = this.<MerchantStore, QMerchantStore>createSet("storesDefaultLanguages", MerchantStore.class, QMerchantStore.class, PathInits.DIRECT2);

    public QLanguage(String variable) {
        super(Language.class, forVariable(variable));
    }

    public QLanguage(Path<? extends Language> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLanguage(PathMetadata metadata) {
        super(Language.class, metadata);
    }

}

