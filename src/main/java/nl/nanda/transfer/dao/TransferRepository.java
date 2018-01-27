package nl.nanda.transfer.dao;

import java.sql.Date;
import java.util.List;

import nl.nanda.account.Amount;
import nl.nanda.transfer.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    public Transfer findByEntityId(Long id);

    public Transfer findByDay(Date day);

    public List<Transfer> findByDayAfter(Date day);

    public List<Transfer> findByTotaalGreaterThan(Amount amount);

    @Query("select t from Transfer t where t.credit = :id")
    public List<Transfer> findByCredit(@Param("id") Integer id);

    @Query("select t from Transfer t where t.debet = :id")
    public List<Transfer> findByDebet(@Param("id") Integer id);

    @Override
    public <S extends Transfer> S save(S arg0);
    // public <S extends Transfer> S saveAndFlush(S arg0);

}
