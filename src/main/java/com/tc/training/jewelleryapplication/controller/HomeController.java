package com.tc.training.jewelleryapplication.controller;

import com.tc.training.jewelleryapplication.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse> homeController(){

        ApiResponse res=new ApiResponse("Welcome To Jewellery Website", true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
