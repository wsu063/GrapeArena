package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConcertImg is a Querydsl query type for ConcertImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConcertImg extends EntityPathBase<ConcertImg> {

    private static final long serialVersionUID = 1606313514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConcertImg concertImg = new QConcertImg("concertImg");

    public final QConcert concert;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath oriImgName = createString("oriImgName");

    public final EnumPath<com.podoarena.constant.RepImgYn> repImgYn = createEnum("repImgYn", com.podoarena.constant.RepImgYn.class);

    public QConcertImg(String variable) {
        this(ConcertImg.class, forVariable(variable), INITS);
    }

    public QConcertImg(Path<? extends ConcertImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConcertImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConcertImg(PathMetadata metadata, PathInits inits) {
        this(ConcertImg.class, metadata, inits);
    }

    public QConcertImg(Class<? extends ConcertImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.concert = inits.isInitialized("concert") ? new QConcert(forProperty("concert"), inits.get("concert")) : null;
    }

}

