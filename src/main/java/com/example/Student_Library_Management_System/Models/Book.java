package com.example.Student_Library_Management_System.Models;


import com.example.Student_Library_Management_System.Enums.Genre;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int pages;

    private boolean issued; //book is issued or not

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    //now mapping
    @ManyToOne
    @JoinColumn
    private Author author;  //reference to parent


    //book is also child wrt card class
    @ManyToOne
    @JoinColumn
    private Card card;


    //from Book to Transactions
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Transactions> transactionsList;



    public Book() {
    }

    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public int getId() {
        return id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
