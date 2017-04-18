package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QIntegrationModule is a Querydsl query type for IntegrationModule
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QIntegrationModule extends EntityPathBase<IntegrationModule> {

    private static final long serialVersionUID = -155408987L;

    public static final QIntegrationModule integrationModule = new QIntegrationModule("integrationModule");

    public final StringPath code = createString("code");

    public final StringPath configDetails = createString("configDetails");

    public final StringPath configuration = createString("configuration");

    public final BooleanPath customModule = createBoolean("customModule");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath module = createString("module");

    public final StringPath regions = createString("regions");

    public final StringPath type = createString("type");

    public QIntegrationModule(String variable) {
        super(IntegrationModule.class, forVariable(variable));
    }

    public QIntegrationModule(Path<? extends IntegrationModule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIntegrationModule(PathMetadata metadata) {
        super(IntegrationModule.class, metadata);
    }

}

