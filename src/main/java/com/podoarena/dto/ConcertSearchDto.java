package com.podoarena.dto;

import com.podoarena.constant.ConcertState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConcertSearchDto {
    private String searchDateType;
    private ConcertState searchTypeStatus;
    private String searchBy;
    private String searchQuery = "";
}
