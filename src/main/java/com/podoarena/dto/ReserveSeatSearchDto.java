package com.podoarena.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveSeatSearchDto {
    // 회원 ID(Email)로만 검색 가능
    private String searchQuery = "";
}
