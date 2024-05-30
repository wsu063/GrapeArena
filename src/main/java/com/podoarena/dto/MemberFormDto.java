package com.podoarena.dto;

import com.podoarena.entity.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberFormDto {
    @NotBlank
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message =  "올바르지 않은 이메일 형식입니다.")
    private String email;

    @NotEmpty(message =  "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotEmpty(message = "번호는 필수 입력 값입니다.")
    private String phone;

    @NotEmpty(message = "우편번호는 필수 입력 값입니다.")
    private String postcode;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    private String detailAddress;

    private String extraAddress;

    private String password1;

    private String password2;

    private static ModelMapper modelMapper = new ModelMapper();

    //entitiy -> dto
    public static MemberFormDto of(Member member) {
        return modelMapper.map(member, MemberFormDto.class);
    }

}
