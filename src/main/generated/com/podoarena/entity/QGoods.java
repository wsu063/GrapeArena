package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoods is a Querydsl query type for Goods
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoods extends EntityPathBase<Goods> {

    private static final long serialVersionUID = -17159095L;

    public static final QGoods goods = new QGoods("goods");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final ListPath<GoodsCart, QGoodsCart> goodsCarts = this.<GoodsCart, QGoodsCart>createList("goodsCarts", GoodsCart.class, QGoodsCart.class, PathInits.DIRECT2);

    public final ListPath<GoodsImg, QGoodsImg> goodsImgs = this.<GoodsImg, QGoodsImg>createList("goodsImgs", GoodsImg.class, QGoodsImg.class, PathInits.DIRECT2);

    public final NumberPath<Integer> goodsMaxAmount = createNumber("goodsMaxAmount", Integer.class);

    public final StringPath goodsName = createString("goodsName");

    public final NumberPath<Integer> goodsPrice = createNumber("goodsPrice", Integer.class);

    public final NumberPath<Integer> goodsStock = createNumber("goodsStock", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<com.podoarena.constant.SellStatus> sellStatus = createEnum("sellStatus", com.podoarena.constant.SellStatus.class);

    public final EnumPath<com.podoarena.constant.Sort> sort = createEnum("sort", com.podoarena.constant.Sort.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QGoods(String variable) {
        super(Goods.class, forVariable(variable));
    }

    public QGoods(Path<? extends Goods> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoods(PathMetadata metadata) {
        super(Goods.class, metadata);
    }

}

