package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsDtoResponse {

    private String description;
    private String image;
    private List<String> tags;
    private String title;
}
