package com.podoarena.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

    @Column(name = "member_id")
    @NotNull
    private Long id;

    private String password;
}
