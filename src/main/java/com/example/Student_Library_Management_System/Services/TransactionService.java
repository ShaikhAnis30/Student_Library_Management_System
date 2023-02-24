package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Transactions;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.Student_Library_Management_System.Enums.CardStatus.ACTIVATED;
import static com.example.Student_Library_Management_System.Enums.TransactionStatus.*;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CardRepository cardRepository;


    public String isIssued(IssueBookRequestDto issueBookRequestDto) throws Exception {

        //I will fetch the book and card object from their ids that we have
        int bookId = issueBookRequestDto.getBookId();
        Book book = bookRepository.findById(bookId).get();

        int cardId = issueBookRequestDto.getCardId();
        Card card = cardRepository.findById(cardId).get();


        //if already 10 books are issued on this card, then no more books can be issued
        if(card.getBooksIssued().size() >= 10)
            throw new Exception("Book cannot be issued on this card");


        Transactions transaction = new Transactions();
        //now I will set the attributes before saving it into DB
        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setIssued(true);//obviously this is A issue transaction so true

        //before any success or failure.... transaction will be pending
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        //now we will check for validations to make txn successful or not
        if(book == null || book.isIssued())  { // if book is already issued
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book is not available");
        }

        if(card == null || !card.getCardStatus().equals(ACTIVATED)) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Card is not Valid");
        }

        //now it is a valid transaction
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        ////// now set attributes of book \\\\\\
        book.setIssued(true); //book is issued now

        //between book and transaction
        List<Transactions> transactionsListOfBook = book.getTransactionsList();
        transactionsListOfBook.add(transaction);
        book.setTransactionsList(transactionsListOfBook);
        //my way
        //book.getTransactionsList().add(transaction);
        //book.setTransactionsList(book.getTransactionsList());



        ///// set attributes of card \\\\\\
        //between book and card
        List<Book> booksIssuedOnCard = card.getBooksIssued();
        booksIssuedOnCard.add(book);
        card.setBooksIssued(booksIssuedOnCard); //issued book on this card



        ///// set remaining attributes of transaction \\\\\
        //between card and transaction
        List<Transactions> transactionsListOfCard = card.getTransactionsList();
        transactionsListOfCard.add(transaction);
        card.setTransactionsList(transactionsListOfCard);


        //I will only save the card : by cascading effect book and transaction will be saved
        cardRepository.save(card);
        return "Book Issued Successfully";
    }


    public String getTransactions(int bookId, int cardId) {
        //I have the function which will give the list of transactions for given book and card ids
        List<Transactions> transactionsList = transactionRepository.listOfTransactionsForBookAndCard(bookId,cardId);

        //now I will return txn id of 1st txn
        String transactionId = transactionsList.get(0).getTransactionId();
        return transactionId;
    }


    public String returnBook(IssueBookRequestDto issueBookRequestDto) throws Exception {
        int bookId = issueBookRequestDto.getBookId();
        Book book = bookRepository.findById(bookId).get();

        int cardId = issueBookRequestDto.getCardId();
        Card card = cardRepository.findById(cardId).get();
        //fetched objects

        //validation
//        if(!book.isIssued()) {
//            throw new Exception("This Book is not issued");
//        }

        Transactions transaction = new Transactions();


        //Fine calculation
        List<Transactions> txnListForThisBookAndCard = transactionRepository.listOfTransactionsForBookAndCard(bookId, cardId);
        //I also want only successful transactions in which book is issued
        List<Transactions> successfulTransactions = new ArrayList<>();
        for (Transactions transactions : txnListForThisBookAndCard) {
            if(transactions.getTransactionStatus().equals(SUCCESS) && transactions.getIsIssued()) {
                successfulTransactions.add(transactions);
            }
        }

        if(successfulTransactions.size() == 0) {
            transaction.setTransactionStatus(FAILED);
            transactionRepository.save(transaction);
            throw new Exception("No book is Issued");
        }


        //taken last successful transaction
        Transactions lastTransaction = successfulTransactions.get(successfulTransactions.size() - 1);
        String issueDate = lastTransaction.getTransactionDate().toString(); //yyyy-mm-dd
        String todayDate = java.time.LocalDate.now().toString(); //gives today's date

        //calculated extra days for fine
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(issueDate.substring(0,10), formatter);
        LocalDate date2 = LocalDate.parse(todayDate.substring(0,10), formatter);
        int days = (int)ChronoUnit.DAYS.between(date1, date2);
        System.out.println(days);


        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setTransactionId(UUID.randomUUID().toString());
//        transaction.setIssued(false);//book is returned so issued will be false
        transaction.setTransactionStatus(PENDING);

        if(days > 15) {
            int extraDays = days - 15;
            transaction.setFine(extraDays * 10); //10rs per day
        }else {
            transaction.setFine(0);
        }

        //validations : No need coz we have only taken Issued transactions

        transaction.setIssued(false); //book is successfully returned
        book.setIssued(false);//imp - Also set book as not issued
        transaction.setTransactionStatus(SUCCESS);


        //between book and transactions
        List<Transactions> bookTransactions = book.getTransactionsList();
        bookTransactions.add(transaction);
        book.setTransactionsList(bookTransactions);

        //between book and card
        List<Book> booksIssuedOnCard = card.getBooksIssued();
        booksIssuedOnCard.remove(book);
        card.setBooksIssued(booksIssuedOnCard);//updated

        //between card and transactions
        List<Transactions> cardTransactions = card.getTransactionsList();
        cardTransactions.add(transaction);
        card.setTransactionsList(cardTransactions);

        cardRepository.save(card);//I will only save parent, by cascading effect book and transaction will be saved

        return "Book returned Successfully";
    }


}
