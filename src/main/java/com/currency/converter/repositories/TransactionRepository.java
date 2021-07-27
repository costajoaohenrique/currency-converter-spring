package com.currency.converter.repositories;

import com.currency.converter.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByIdUser(Long idUser);
}
