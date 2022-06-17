package com.hashedIn.controllers;

import com.hashedIn.domains.NetflixRecord;
import com.hashedIn.io.CSVFileReaderImpl;
import com.hashedIn.services.NetflixCSVReaderServiceImpl;
import com.hashedIn.services.NetflixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDbController {

    @Autowired
    private NetflixService netflixService;

 //   @PostMapping("/save")
//    public StudentDTO insertIntoDynamoDB(@RequestBody StudentDTO dto) {
//        return  studentService.insertIntoDynamoDB(dto);
//    }
}