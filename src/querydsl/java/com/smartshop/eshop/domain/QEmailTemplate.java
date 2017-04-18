package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailTemplate is a Querydsl query type for EmailTemplate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEmailTemplate extends EntityPathBase<EmailTemplate> {

    private static final long serialVersionUID = -819636485L;

    public static final QEmailTemplate emailTemplate = new QEmailTemplate("emailTemplate");

    public final StringPath actionKey = createString("actionKey");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath subject = createString("subject");

    public QEmailTemplate(String variable) {
        super(EmailTemplate.class, forVariable(variable));
    }

    public QEmailTemplate(Path<? extends EmailTemplate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailTemplate(PathMetadata metadata) {
        super(EmailTemplate.class, metadata);
    }

}

