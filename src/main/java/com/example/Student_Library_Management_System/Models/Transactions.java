package com.example.Student_Library_Management_System.Models;


import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private Date transactionDate;

    private String transactionId = UUID.randomUUID().toString();

    private int fine;

    private boolean isIssued;


    //now mapping

    //Transaction to Book
    @ManyToOne
    @JoinColumn
    private Book book;

    //Transaction to Card
    @ManyToOne
    @JoinColumn
    private Card card;


    public Transactions() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public boolean getIsIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
