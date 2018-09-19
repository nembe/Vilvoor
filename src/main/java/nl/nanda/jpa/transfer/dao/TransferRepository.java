package nl.nanda.jpa.transfer.dao;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import nl.nanda.jpa.account.Amount;
import nl.nanda.jpa.transfer.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Uses Spring Data JPA to retrieved Transfers.
 */
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    /**
     * Find by entity id.
     *
     * @param id
     *            the id
     * @return the transfer
     */
    Transfer findByEntityId(Integer id);

    /**
     * Find all transfers by day.
     *
     * @param day
     *            the day
     * @return the transfer
     */
    Transfer findByDay(Date day);

    /**
     * Find all transfers the day after "the given day".
     *
     * @param day
     *            the day
     * @return the list
     */
    List<Transfer> findByDayAfter(Date day);

    /**
     * Find all Transfers where the amount is greater than.
     *
     * @param amount
     *            the amount
     * @return the list
     */
    List<Transfer> findByTotaalGreaterThan(Amount amount);

    /**
     * Find by credit UUID (The sending accounts).
     *
     * @param id
     *            the id
     * @return the list
     */
    @Query("select t from Transfer t where t.credit = :id")
    List<Transfer> findByCredit(@Param("id") UUID id);

    /**
     * Find by debet UUID (The receiving accounts).
     *
     * @param id
     *            the id
     * @return the list
     */
    @Query("select t from Transfer t where t.debet = :id")
    List<Transfer> findByDebet(@Param("id") UUID id);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
     */
    @Override
    List<Transfer> findAll();

}
