package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialUserConnection is a Querydsl query type for SocialUserConnection
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSocialUserConnection extends EntityPathBase<SocialUserConnection> {

    private static final long serialVersionUID = 109950833L;

    public static final QSocialUserConnection socialUserConnection = new QSocialUserConnection("socialUserConnection");

    public final StringPath accessToken = createString("accessToken");

    public final StringPath displayName = createString("displayName");

    public final NumberPath<Long> expireTime = createNumber("expireTime", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageURL = createString("imageURL");

    public final StringPath profileURL = createString("profileURL");

    public final StringPath providerId = createString("providerId");

    public final StringPath providerUserId = createString("providerUserId");

    public final NumberPath<Long> rank = createNumber("rank", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath secret = createString("secret");

    public final StringPath userId = createString("userId");

    public QSocialUserConnection(String variable) {
        super(SocialUserConnection.class, forVariable(variable));
    }

    public QSocialUserConnection(Path<? extends SocialUserConnection> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialUserConnection(PathMetadata metadata) {
        super(SocialUserConnection.class, metadata);
    }

}

