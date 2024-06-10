package com.podoarena.repository;

import com.podoarena.entity.PlaceConcert;
import com.podoarena.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByDateIdAndSeatRowAndSeatLine(Long DateId, int seatRow, int seatLine);
    List<Seat> findByDateIdOrderByIdAsc(Long DateId);
}
