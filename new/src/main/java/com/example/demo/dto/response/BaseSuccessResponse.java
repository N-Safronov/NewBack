package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseSuccessResponse {
    private Integer statusCode;
    private Boolean success;
}
