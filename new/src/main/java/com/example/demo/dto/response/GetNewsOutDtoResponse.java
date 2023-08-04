package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetNewsOutDtoResponse {

    private String description;
    private Integer id;
    private String image;
    private List<TagResponse> tags;
    private String title;
    private String userId;
    private String username;
}
