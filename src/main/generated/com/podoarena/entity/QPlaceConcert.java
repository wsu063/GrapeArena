package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceConcert is a Querydsl query type for PlaceConcert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceConcert extends EntityPathBase<PlaceConcert> {

    private static final long serialVersionUID = -359540916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceConcert placeConcert = new QPlaceConcert("placeConcert");

    public final QConcert concert;

    public final ListPath<Date, QDate> dateList = this.<Date, QDate>createList("dateList", Date.class, QDate.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPlace place;

    public final ListPath<ReserveSeat, QReserveSeat> reserveSeatList = this.<ReserveSeat, QReserveSeat>createList("reserveSeatList", ReserveSeat.class, QReserveSeat.class, PathInits.DIRECT2);

    public QPlaceConcert(String variable) {
        this(PlaceConcert.class, forVariable(variable), INITS);
    }

    public QPlaceConcert(Path<? extends PlaceConcert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceConcert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceConcert(PathMetadata metadata, PathInits inits) {
        this(PlaceConcert.class, metadata, inits);
    }

    public QPlaceConcert(Class<? extends PlaceConcert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.concert = inits.isInitialized("concert") ? new QConcert(forProperty("concert"), inits.get("concert")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

