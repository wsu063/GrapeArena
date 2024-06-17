package com.podoarena.repository;

import com.podoarena.dto.ConcertSearchDto;
import com.podoarena.entity.Concert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConcertRepositoryCustom {

    List<Concert> getConcertList();

    Page<Concert> getAdminConcertPage(ConcertSearchDto concertSearchDto, Pageable pageable);
}
