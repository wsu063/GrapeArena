package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoodsCart is a Querydsl query type for GoodsCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoodsCart extends EntityPathBase<GoodsCart> {

    private static final long serialVersionUID = 1646841609L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoodsCart goodsCart = new QGoodsCart("goodsCart");

    public final QCart cart;

    public final QGoods goods;

    public final NumberPath<Integer> goodsCount = createNumber("goodsCount", Integer.class);

    public final NumberPath<Integer> goodsmaxAmount = createNumber("goodsmaxAmount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QGoodsCart(String variable) {
        this(GoodsCart.class, forVariable(variable), INITS);
    }

    public QGoodsCart(Path<? extends GoodsCart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoodsCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoodsCart(PathMetadata metadata, PathInits inits) {
        this(GoodsCart.class, metadata, inits);
    }

    public QGoodsCart(Class<? extends GoodsCart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart"), inits.get("cart")) : null;
        this.goods = inits.isInitialized("goods") ? new QGoods(forProperty("goods")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

