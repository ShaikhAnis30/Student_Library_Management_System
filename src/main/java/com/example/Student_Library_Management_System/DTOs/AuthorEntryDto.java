package com.example.Student_Library_Management_System.DTOs;

public class AuthorEntryDto {

    //This is Just A Object that we will use to Take request form postman

    //Imp thing is it will contain only attributes that we have to pass from postman

    //Id we will not take coz we do not have to take it from postman

    private String name;

    private int age;

    private String country;

    private double rating;

    public AuthorEntryDto() {
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
