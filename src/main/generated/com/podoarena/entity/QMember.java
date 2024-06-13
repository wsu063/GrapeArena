package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -369454073L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final ListPath<Cart, QCart> cart = this.<Cart, QCart>createList("cart", Cart.class, QCart.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath email = createString("email");

    public final StringPath extraAddress = createString("extraAddress");

    public final ListPath<GoodsCart, QGoodsCart> goodsCart = this.<GoodsCart, QGoodsCart>createList("goodsCart", GoodsCart.class, QGoodsCart.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath name = createString("name");

    public final ListPath<Orders, QOrders> ordersList = this.<Orders, QOrders>createList("ordersList", Orders.class, QOrders.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath postcode = createString("postcode");

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final QReserve reserve;

    public final EnumPath<com.podoarena.constant.Role> role = createEnum("role", com.podoarena.constant.Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reserve = inits.isInitialized("reserve") ? new QReserve(forProperty("reserve"), inits.get("reserve")) : null;
    }

}

