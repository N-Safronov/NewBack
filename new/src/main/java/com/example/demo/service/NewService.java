package com.example.demo.service;

import com.example.demo.dto.response.*;
import com.example.demo.entity.News;
import com.example.demo.entity.Tags;
import com.example.demo.repository.NewRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewService {

    private final NewRepository newRepository;

    @Autowired
    public NewService(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    public CustomSuccessResponse<CustomDataResponse<List<GetNewsOutDtoResponse>>> findAllPage(Pageable pageable) {

        Page<News> savedToDo = newRepository.findAll(pageable);
        List<GetNewsOutDtoResponse> content = convertToDoToTaskData(savedToDo.getContent());

        CustomDataResponse data = new CustomDataResponse(content, content.size());

        return new CustomSuccessResponse(data, HttpStatus.OK.value(), true);
    }

    public News findById(Integer id) {
        return newRepository.findById(id).orElse(null);
    }

    public CustomSuccessResponse save(News news) {
        News savedToDo = newRepository.save(news);
        return new CustomSuccessResponse(savedToDo.getId(), HttpStatus.CREATED.value(), true);
    }

    public void putById(Integer id, News news) {
    }

    public void deleteById(Integer id) {
        newRepository.deleteById(id);
    }

    public List<GetNewsOutDtoResponse> convertToDoToTaskData(List<News> newsList) {
        return newsList.stream()
                .map(news -> new GetNewsOutDtoResponse(
                        news.getDescription(),
                        news.getId(),
                        news.getImageUrl(),
                        convertTagData(news.getTagsList()),
                        news.getTitle(),
                        news.get_user().getId().toString(),
                        news.get_user().getUsername()
                ))
                .collect(Collectors.toList());
    }

    public List<TagResponse> convertTagData(List<Tags> tagsList) {
        return tagsList.stream()
                .map(tags -> new TagResponse(tags.getId(), tags.getName()))
                .collect(Collectors.toList());
    }
}
