package nl.nanda.transaction.dao;

import java.util.List;

import nl.nanda.transaction.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public Transaction findByEntityId(Long id);

    public Transaction findByAccount(Long id);

    public Transaction findByTransfer(Long id);

    @Override
    public List<Transaction> findAll();

    @Override
    public <S extends Transaction> S saveAndFlush(S arg0);
}
