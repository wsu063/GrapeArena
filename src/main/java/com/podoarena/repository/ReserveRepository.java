package com.podoarena.repository;

import com.podoarena.entity.Orders;
import com.podoarena.entity.Reserve;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

}
