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

    public static final QConcert concert = new QConcert("concert");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<ConcertImg, QConcertImg> concertImgs = this.<ConcertImg, QConcertImg>createList("concertImgs", ConcertImg.class, QConcertImg.class, PathInits.DIRECT2);

    public final StringPath concertName = createString("concertName");

    public final StringPath concertSinger = createString("concertSinger");

    public final EnumPath<com.podoarena.constant.ConcertState> concertState = createEnum("concertState", com.podoarena.constant.ConcertState.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final ListPath<Date, QDate> dateList = this.<Date, QDate>createList("dateList", Date.class, QDate.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QConcert(String variable) {
        super(Concert.class, forVariable(variable));
    }

    public QConcert(Path<? extends Concert> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConcert(PathMetadata metadata) {
        super(Concert.class, metadata);
    }

}

