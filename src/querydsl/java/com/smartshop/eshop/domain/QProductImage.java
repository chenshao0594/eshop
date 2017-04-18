package com.smartshop.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductImage is a Querydsl query type for ProductImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductImage extends EntityPathBase<ProductImage> {

    private static final long serialVersionUID = -2114582969L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductImage productImage1 = new QProductImage("productImage1");

    public final ArrayPath<byte[], Byte> content = createArray("content", byte[].class);

    public final StringPath contentType = createString("contentType");

    public final BooleanPath defaultImage = createBoolean("defaultImage");

    public final SetPath<ProductImageDescription, QProductImageDescription> descriptions = this.<ProductImageDescription, QProductImageDescription>createSet("descriptions", ProductImageDescription.class, QProductImageDescription.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath imageCrop = createBoolean("imageCrop");

    public final NumberPath<Integer> imageType = createNumber("imageType", Integer.class);

    public final QProduct product;

    public final StringPath productImage = createString("productImage");

    public final StringPath productImageUrl = createString("productImageUrl");

    public QProductImage(String variable) {
        this(ProductImage.class, forVariable(variable), INITS);
    }

    public QProductImage(Path<? extends ProductImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductImage(PathMetadata metadata, PathInits inits) {
        this(ProductImage.class, metadata, inits);
    }

    public QProductImage(Class<? extends ProductImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

