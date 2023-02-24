package com.example.Student_Library_Management_System.Controllers;


import com.example.Student_Library_Management_System.DTOs.AuthorEntryDto;
import com.example.Student_Library_Management_System.DTOs.AuthorResponseDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

//    @PostMapping("/add")
//    public String addAuthor(@RequestBody Author author) {
//        return authorService.addAuthor(author);
//    }

    @PostMapping("/add")
    public String addAuthor(@RequestBody AuthorEntryDto authorEntryDto) {
        return authorService.addAuthor(authorEntryDto);
    }

    @GetMapping("/get-author")
    public AuthorResponseDto getAuthor(@RequestParam("id") int authorId) {
        return authorService.getAuthor(authorId);
    }
}
