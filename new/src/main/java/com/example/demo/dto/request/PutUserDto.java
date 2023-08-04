package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PutUserDto {
    private String avatar;
    private String email;
    private String name;
    private String role;
}
