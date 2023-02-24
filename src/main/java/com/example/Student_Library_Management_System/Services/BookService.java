package com.example.Student_Library_Management_System.Services;


import com.example.Student_Library_Management_System.DTOs.BookRequestDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository; //i will connect to authorRepository

//    public String addBook(Book book) {
//
//        //Purpose: i want to get the authorEntity
//
//        int authorId = book.getAuthor().getId();
//        //Now i get the author id, so i can easily get the author object
//
//        Author author = null;
//        try{
//            author = authorRepository.findById(authorId).get();
//        }
//        catch (NoSuchElementException e) {
//            System.out.println("Id not present");
//        }
//        //.get throws exception try handling it
//
//        // now i have the Author, so i will set the foreign key attribute
//        book.setAuthor(author);
//
//
//        //now this book will get added in the list of books written by this author
//        List<Book> booksWritten = author.getBooksWritten();
//        booksWritten.add(book);
////        author.setBooksWritten(booksWritten);
//
//        //now we have to save the book and also the author
//        //we have to update the author coz some attributes of author are changed
//        //we use save function, coz it does both operation save and also update
//        authorRepository.save(author);
//        //we save author coz it is parent and book will automatically be saved by cascading effect
//
//        return "Book added Successfully";
//
//    }

    public String addBook(BookRequestDto bookRequestDto) {

        int authorId = bookRequestDto.getAuthorId();
        //Now i get the author id, so i can easily get the author object

        Author author = null;
        try{
            author = authorRepository.findById(authorId).get();
        }
        catch (NoSuchElementException e) {
            System.out.println("Id not present");
        }

        //now I want get the Book entity so that i can easily save it
        Book book = new Book();
        //now I will set the attributes
        book.setName(bookRequestDto.getName());
        book.setPages(bookRequestDto.getPages());
        book.setGenre(bookRequestDto.getGenre());
        book.setIssued(false);

        // now i have the Author, so i will set the foreign key attribute
        book.setAuthor(author);


        //now this book will get added in the list of books written by this author
        List<Book> booksWritten = author.getBooksWritten();
        booksWritten.add(book);
//        author.setBooksWritten(booksWritten);

        //now we have to save the book and also the author
        //we have to update the author coz some attributes of author are changed
        //we use save function, coz it does both operation save and also update
        authorRepository.save(author);
        //we save author coz it is parent and book will automatically be saved by cascading effect

        return "Book added Successfully";

    }

    //Most Liked Book


}
