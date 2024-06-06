package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = -8950278L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlace place = new QPlace("place");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath placeBatch = createString("placeBatch");

    public final ListPath<PlaceConcert, QPlaceConcert> placeConcertList = this.<PlaceConcert, QPlaceConcert>createList("placeConcertList", PlaceConcert.class, QPlaceConcert.class, PathInits.DIRECT2);

    public final QPlaceImg placeImg;

    public final StringPath placeLocation = createString("placeLocation");

    public final StringPath placeName = createString("placeName");

    public final ListPath<Seat, QSeat> seatList = this.<Seat, QSeat>createList("seatList", Seat.class, QSeat.class, PathInits.DIRECT2);

    public QPlace(String variable) {
        this(Place.class, forVariable(variable), INITS);
    }

    public QPlace(Path<? extends Place> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlace(PathMetadata metadata, PathInits inits) {
        this(Place.class, metadata, inits);
    }

    public QPlace(Class<? extends Place> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.placeImg = inits.isInitialized("placeImg") ? new QPlaceImg(forProperty("placeImg"), inits.get("placeImg")) : null;
    }

}

