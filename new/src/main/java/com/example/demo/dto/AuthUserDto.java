package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto{

    private String avatar;
    private String email;
    private String id;
    private String name;
    private String role;

    @JsonProperty("access_token")
    private String token;

}

