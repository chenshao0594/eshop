package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSystemConfiguration is a Querydsl query type for SystemConfiguration
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSystemConfiguration extends EntityPathBase<SystemConfiguration> {

    private static final long serialVersionUID = -1296579060L;

    public static final QSystemConfiguration systemConfiguration = new QSystemConfiguration("systemConfiguration");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath key = createString("key");

    public final StringPath value = createString("value");

    public QSystemConfiguration(String variable) {
        super(SystemConfiguration.class, forVariable(variable));
    }

    public QSystemConfiguration(Path<? extends SystemConfiguration> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSystemConfiguration(PathMetadata metadata) {
        super(SystemConfiguration.class, metadata);
    }

}

