package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PublicUserViewResponse {

    private String avatar;
    private String email;
    private String id;
    private String name;
    private String role;
}
