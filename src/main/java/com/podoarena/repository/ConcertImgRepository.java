package com.podoarena.repository;

import com.podoarena.entity.ConcertImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertImgRepository extends JpaRepository<ConcertImg, Long> {

    List<ConcertImg> findByConcertIdOrderByIdAsc(Long concertId);
}
