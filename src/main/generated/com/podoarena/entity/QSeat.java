package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeat is a Querydsl query type for Seat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeat extends EntityPathBase<Seat> {

    private static final long serialVersionUID = 969625266L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeat seat = new QSeat("seat");

    public final QDate date;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPlaceConcert placeConcert;

    public final QReserveSeat reserveSeat;

    public final EnumPath<com.podoarena.constant.SeatGrade> seatGrade = createEnum("seatGrade", com.podoarena.constant.SeatGrade.class);

    public final NumberPath<Integer> seatLine = createNumber("seatLine", Integer.class);

    public final StringPath seatLocation = createString("seatLocation");

    public final StringPath seatName = createString("seatName");

    public final NumberPath<Integer> seatPrice = createNumber("seatPrice", Integer.class);

    public final NumberPath<Integer> seatRow = createNumber("seatRow", Integer.class);

    public final EnumPath<com.podoarena.constant.SeatStatus> seatStatus = createEnum("seatStatus", com.podoarena.constant.SeatStatus.class);

    public QSeat(String variable) {
        this(Seat.class, forVariable(variable), INITS);
    }

    public QSeat(Path<? extends Seat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeat(PathMetadata metadata, PathInits inits) {
        this(Seat.class, metadata, inits);
    }

    public QSeat(Class<? extends Seat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.date = inits.isInitialized("date") ? new QDate(forProperty("date"), inits.get("date")) : null;
        this.placeConcert = inits.isInitialized("placeConcert") ? new QPlaceConcert(forProperty("placeConcert"), inits.get("placeConcert")) : null;
        this.reserveSeat = inits.isInitialized("reserveSeat") ? new QReserveSeat(forProperty("reserveSeat"), inits.get("reserveSeat")) : null;
    }

}

