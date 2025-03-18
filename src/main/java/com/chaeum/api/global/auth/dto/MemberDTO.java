package com.chaeum.api.global.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String email;
    private String name;
    private String role;

    public MemberDTO(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public MemberDTO(String email, String name, String role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }
}
