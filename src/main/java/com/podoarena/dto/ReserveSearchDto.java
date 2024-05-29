package com.podoarena.dto;

import com.podoarena.constant.ConcertState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveSearchDto {
    // 회원 ID(Email)로만 검색 가능
    private String searchQuery = "";
}
