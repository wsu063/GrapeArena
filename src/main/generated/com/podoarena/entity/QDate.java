package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDate is a Querydsl query type for Date
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDate extends EntityPathBase<Date> {

    private static final long serialVersionUID = 969175131L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDate date = new QDate("date");

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPlaceConcert placeConcert;

    public QDate(String variable) {
        this(Date.class, forVariable(variable), INITS);
    }

    public QDate(Path<? extends Date> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDate(PathMetadata metadata, PathInits inits) {
        this(Date.class, metadata, inits);
    }

    public QDate(Class<? extends Date> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.placeConcert = inits.isInitialized("placeConcert") ? new QPlaceConcert(forProperty("placeConcert"), inits.get("placeConcert")) : null;
    }

}

