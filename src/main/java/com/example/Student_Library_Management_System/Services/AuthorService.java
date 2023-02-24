package com.example.Student_Library_Management_System.Services;


import com.example.Student_Library_Management_System.DTOs.AuthorEntryDto;
import com.example.Student_Library_Management_System.DTOs.AuthorResponseDto;
import com.example.Student_Library_Management_System.DTOs.BookResponseDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

//    public String addAuthor(Author author) {
//        //we will set listOfBooksWritten when we will add book not now
//
//
//        authorRepository.save(author);
//        return "Author created Successfully";
//    }

    //this will take authorEntryDto
    public String addAuthor(AuthorEntryDto authorEntryDto) {
        //we will convert this AuthorEntryDto to Author Entity
        //coz Repository only understands Entity and not DTOs

        Author author = new Author();
        //now before saving author we will save its attributes
        author.setName(authorEntryDto.getName());
        author.setAge(authorEntryDto.getAge());
        author.setCountry(authorEntryDto.getCountry());
        author.setRating(authorEntryDto.getRating());

        authorRepository.save(author);
        return "Author created Successfully";
    }

    public AuthorResponseDto getAuthor(int authorId) {
        //first I will take author entity
        Author author = authorRepository.findById(authorId).get();
        //now form this Entity I will take value of all the basic attributes
        //to save the attributes of authorResponseDto
        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setName(author.getName());
        authorResponseDto.setAge(author.getAge());
        authorResponseDto.setCountry(author.getCountry());

        //now I will save all books
        List<BookResponseDto> bookList = new ArrayList<>();
        List<Book> booksWritten = author.getBooksWritten();
        for (Book book : booksWritten) {
            BookResponseDto bookResponseDto = new BookResponseDto();//jitne books hai utne bookDto create hoge
            bookResponseDto.setName(book.getName());
            bookResponseDto.setPages(book.getPages());
            bookResponseDto.setGenre(book.getGenre());

            bookList.add(bookResponseDto);
        }



        authorResponseDto.setBooksWritten(bookList);

        return authorResponseDto;
    }

}
