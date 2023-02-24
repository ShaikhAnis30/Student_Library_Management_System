package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue-book")
    public String isIssued(@RequestBody IssueBookRequestDto issueBookRequestDto) {

        try{
            return transactionService.isIssued(issueBookRequestDto);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/get-first-transaction")
    public String getFirstTransactionId(@RequestParam("bookId") int bookId, @RequestParam("cardId") int cardId) {
        return transactionService.getTransactions(bookId, cardId);
    }

    @PostMapping("/return-book")
    public String returnBook(@RequestBody IssueBookRequestDto issueBookRequestDto) {
        try {
            return transactionService.returnBook(issueBookRequestDto);
        }catch (Exception e) {
            return e.getMessage();
        }
    }

}
