package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConcert is a Querydsl query type for Concert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConcert extends EntityPathBase<Concert> {

    private static final long serialVersionUID = 1433968345L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConcert concert = new QConcert("concert");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<ConcertImg, QConcertImg> concertImgs = this.<ConcertImg, QConcertImg>createList("concertImgs", ConcertImg.class, QConcertImg.class, PathInits.DIRECT2);

    public final StringPath concertName = createString("concertName");

    public final StringPath concertSinger = createString("concertSinger");

    public final EnumPath<com.podoarena.constant.ConcertState> concertState = createEnum("concertState", com.podoarena.constant.ConcertState.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final QPlaceConcert placeConcert;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QConcert(String variable) {
        this(Concert.class, forVariable(variable), INITS);
    }

    public QConcert(Path<? extends Concert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConcert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConcert(PathMetadata metadata, PathInits inits) {
        this(Concert.class, metadata, inits);
    }

    public QConcert(Class<? extends Concert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.placeConcert = inits.isInitialized("placeConcert") ? new QPlaceConcert(forProperty("placeConcert"), inits.get("placeConcert")) : null;
    }

}

