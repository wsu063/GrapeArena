package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderGoods is a Querydsl query type for OrderGoods
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderGoods extends EntityPathBase<OrderGoods> {

    private static final long serialVersionUID = -20116587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderGoods orderGoods = new QOrderGoods("orderGoods");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final QGoods goods;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final NumberPath<Integer> orderCount = createNumber("orderCount", Integer.class);

    public final NumberPath<Integer> orderPrice = createNumber("orderPrice", Integer.class);

    public final QOrders orders;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QOrderGoods(String variable) {
        this(OrderGoods.class, forVariable(variable), INITS);
    }

    public QOrderGoods(Path<? extends OrderGoods> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderGoods(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderGoods(PathMetadata metadata, PathInits inits) {
        this(OrderGoods.class, metadata, inits);
    }

    public QOrderGoods(Class<? extends OrderGoods> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goods = inits.isInitialized("goods") ? new QGoods(forProperty("goods")) : null;
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}

