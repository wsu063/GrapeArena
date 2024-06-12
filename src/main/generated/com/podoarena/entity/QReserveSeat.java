package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserveSeat is a Querydsl query type for ReserveSeat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserveSeat extends EntityPathBase<ReserveSeat> {

    private static final long serialVersionUID = 828662676L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReserveSeat reserveSeat = new QReserveSeat("reserveSeat");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final QPlaceConcert placeConcert;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final QReserve reserve;

    public final QSeat seat;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QReserveSeat(String variable) {
        this(ReserveSeat.class, forVariable(variable), INITS);
    }

    public QReserveSeat(Path<? extends ReserveSeat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReserveSeat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReserveSeat(PathMetadata metadata, PathInits inits) {
        this(ReserveSeat.class, metadata, inits);
    }

    public QReserveSeat(Class<? extends ReserveSeat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.placeConcert = inits.isInitialized("placeConcert") ? new QPlaceConcert(forProperty("placeConcert"), inits.get("placeConcert")) : null;
        this.reserve = inits.isInitialized("reserve") ? new QReserve(forProperty("reserve"), inits.get("reserve")) : null;
        this.seat = inits.isInitialized("seat") ? new QSeat(forProperty("seat"), inits.get("seat")) : null;
    }

}

