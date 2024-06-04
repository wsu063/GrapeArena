package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderGoods is a Querydsl query type for OrderGoods
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderGoods extends EntityPathBase<OrderGoods> {

    private static final long serialVersionUID = -20116587L;

    public static final QOrderGoods orderGoods = new QOrderGoods("orderGoods");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QOrderGoods(String variable) {
        super(OrderGoods.class, forVariable(variable));
    }

    public QOrderGoods(Path<? extends OrderGoods> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderGoods(PathMetadata metadata) {
        super(OrderGoods.class, metadata);
    }

}

