package com.example.Student_Library_Management_System.Repositories;

import com.example.Student_Library_Management_System.Models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    //I want to get list of transactions of given book and card Ids,
    //so I will write my own custom query which is called Native Query
    @Query(value = "select * from transactions where book_id=:bookId and card_id=:cardId and is_issued=true",
    nativeQuery = true)
    List<Transactions> listOfTransactionsForBookAndCard(int bookId, int cardId);


}
