package com.example.demo.service;

import com.example.demo.dto.request.PutUserDto;
import com.example.demo.dto.response.BaseSuccessResponse;
import com.example.demo.dto.response.CustomSuccessResponse;
import com.example.demo.dto.response.PublicUserViewResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomSuccessResponse<List<PublicUserViewResponse>> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<PublicUserViewResponse> userResponses = users.stream()
                .map(this::convertToPublicUserViewResponse)
                .collect(Collectors.toList());

        return CustomSuccessResponse.<List<PublicUserViewResponse>>builder()
                .data(userResponses)
                .statusCode(200)
                .success(true)
                .build();
    }

    public CustomSuccessResponse<PublicUserViewResponse> findById(Integer id) {
         User user = userRepository.findById(id).orElse(null);

         return CustomSuccessResponse.<PublicUserViewResponse>builder()
                 .data(convertToPublicUserViewResponse(user))
                 .statusCode(200)
                 .success(true)
                 .build();
    }

    public CustomSuccessResponse<PublicUserViewResponse> replaceUser(PutUserDto putUserDto){
        User user = getUserFromDatabase();

        user.setEmail(putUserDto.getEmail());
        user.setImageUrl(putUserDto.getAvatar());
        //user.setRole(putUserDto.getRole()); Написать метода для конвертации
        user.setFirstname(putUserDto.getName());


        return CustomSuccessResponse.<PublicUserViewResponse>builder()
                .data(convertToPublicUserViewResponse(user))
                .statusCode(200)
                .success(true)
                .build();
    }

    public BaseSuccessResponse deleteUser() {
        userRepository.deleteById(getUserFromDatabase().getId());
        return BaseSuccessResponse.builder()
                .statusCode(200)
                .success(true)
                .build();
    }

    public CustomSuccessResponse<PublicUserViewResponse> getUserInfo() {
        return CustomSuccessResponse.<PublicUserViewResponse>builder()
                .data(convertToPublicUserViewResponse(getUserFromDatabase()))
                .statusCode(200)
                .success(true)
                .build();
    }

    public User getUserFromDatabase() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getPrincipal().toString())
                .orElseThrow(
                        () -> new UsernameNotFoundException("")
                );
    }

    private PublicUserViewResponse convertToPublicUserViewResponse(User user) {
        return PublicUserViewResponse.builder()
                .avatar(user.getImageUrl())
                .email(user.getEmail())
                .id(String.valueOf(user.getId()))
                .name(user.getFirstname() + " " + user.getLastname())
                .role(user.getRole().toString())
                .build();
    }
}
