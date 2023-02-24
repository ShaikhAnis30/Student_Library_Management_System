package com.example.Student_Library_Management_System.DTOs;

import com.example.Student_Library_Management_System.Models.Book;

import java.util.List;

public class AuthorResponseDto {

    private String name;
    private int age;
    private String country;

    private List<BookResponseDto> booksWritten;

    public AuthorResponseDto() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<BookResponseDto> getBooksWritten() {
        return booksWritten;
    }

    public void setBooksWritten(List<BookResponseDto> booksWritten) {
        this.booksWritten = booksWritten;
    }
}
