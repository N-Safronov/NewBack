package com.example.demo.controller;

import com.example.demo.dto.request.PutUserDto;
import com.example.demo.dto.response.BaseSuccessResponse;
import com.example.demo.dto.response.CustomSuccessResponse;
import com.example.demo.dto.response.PublicUserViewResponse;
import com.example.demo.entity.News;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<CustomSuccessResponse<List<PublicUserViewResponse>>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<PublicUserViewResponse>> getUserById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<CustomSuccessResponse<PublicUserViewResponse>> replaceUser(@Valid @RequestBody PutUserDto putUserDto){
        return new ResponseEntity<>(userService.replaceUser(putUserDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseSuccessResponse> deleteUser(){
        return new ResponseEntity<>(userService.deleteUser(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<CustomSuccessResponse<PublicUserViewResponse>> getUserInfo(){
        return new ResponseEntity<>(userService.getUserInfo(), HttpStatus.OK);
    }

}
