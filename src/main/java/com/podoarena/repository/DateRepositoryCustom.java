package com.podoarena.repository;

import com.podoarena.entity.Date;

import java.util.List;

public interface DateRepositoryCustom {
    List<Date> getDateByConcertIdOrderByDateAsc(Long concertId);
}
