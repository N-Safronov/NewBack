package com.example.demo.controller;

import com.example.demo.dto.response.*;
import com.example.demo.entity.News;
import com.example.demo.service.NewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/news")
public class NewsController {

    private final NewService newService;

    @Autowired
    public NewsController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getTodoById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(newService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<CustomDataResponse<List<GetNewsOutDtoResponse>>>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage){

        return new ResponseEntity<>(
                newService.findAllPage(PageRequest.of(page, perPage)),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomSuccessResponse> createNews(@Valid @RequestBody News news) {
        return new ResponseEntity<>(newService.save(news), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> put(@PathVariable("id") Integer id, @Valid @RequestBody News news){
        newService.putById(id, news);
        return new ResponseEntity<>(new BaseSuccessResponse(0, true), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> deleteNews(@PathVariable("id") Integer id) {
        newService.deleteById(id);
        return new ResponseEntity<>(new BaseSuccessResponse(0, true), HttpStatus.OK);
    }

}
