package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomDataResponse<T> {
    private T content;
    private Integer numberOfElements;
}
