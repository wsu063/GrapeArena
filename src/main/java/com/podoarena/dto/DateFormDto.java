package com.podoarena.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DateFormDto {
    private Long id;

    private LocalDateTime dateTime;
}
