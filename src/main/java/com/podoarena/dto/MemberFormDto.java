package com.podoarena.dto;

import com.podoarena.entity.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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


    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotEmpty(message = "번호는 필수 입력 값입니다.")
    private String phone;

    @NotEmpty(message = "우편번호는 필수 입력 값입니다.")
    private String postcode;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    private String detailAddress;

    private String extraAddress;

    private static ModelMapper modelMapper = new ModelMapper();

    //entitiy -> dto
    public static MemberFormDto of(Member member) {
        return modelMapper.map(member, MemberFormDto.class);
    }

}
