package nl.nanda.transaction.dao;

import nl.nanda.transaction.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public Transaction findByEntityId(Integer id);

    public Transaction findByAccount(Integer id);

    public Transaction findByTransfer(Integer id);

    @Override
    public <S extends Transaction> S saveAndFlush(S arg0);
}
