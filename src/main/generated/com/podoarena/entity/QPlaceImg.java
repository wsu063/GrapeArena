package com.podoarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceImg is a Querydsl query type for PlaceImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceImg extends EntityPathBase<PlaceImg> {

    private static final long serialVersionUID = -349685911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceImg placeImg = new QPlaceImg("placeImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath oriImgName = createString("oriImgName");

    public final QPlace place;

    public final EnumPath<com.podoarena.constant.RepImgYn> repImgYn = createEnum("repImgYn", com.podoarena.constant.RepImgYn.class);

    public QPlaceImg(String variable) {
        this(PlaceImg.class, forVariable(variable), INITS);
    }

    public QPlaceImg(Path<? extends PlaceImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceImg(PathMetadata metadata, PathInits inits) {
        this(PlaceImg.class, metadata, inits);
    }

    public QPlaceImg(Class<? extends PlaceImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

