package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoodsImg is a Querydsl query type for GoodsImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoodsImg extends EntityPathBase<GoodsImg> {

    private static final long serialVersionUID = -85417286L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoodsImg goodsImg = new QGoodsImg("goodsImg");

    public final QGoods goods;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath oriImgName = createString("oriImgName");

    public final EnumPath<com.podoarena.constant.RepImgYn> repImgYn = createEnum("repImgYn", com.podoarena.constant.RepImgYn.class);

    public QGoodsImg(String variable) {
        this(GoodsImg.class, forVariable(variable), INITS);
    }

    public QGoodsImg(Path<? extends GoodsImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoodsImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoodsImg(PathMetadata metadata, PathInits inits) {
        this(GoodsImg.class, metadata, inits);
    }

    public QGoodsImg(Class<? extends GoodsImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goods = inits.isInitialized("goods") ? new QGoods(forProperty("goods")) : null;
    }

}

