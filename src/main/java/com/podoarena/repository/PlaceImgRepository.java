package com.podoarena.repository;

import com.podoarena.entity.ConcertImg;
import com.podoarena.entity.PlaceImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceImgRepository extends JpaRepository<PlaceImg, Long> {

    PlaceImg findByPlaceId(Long placeId);

}
