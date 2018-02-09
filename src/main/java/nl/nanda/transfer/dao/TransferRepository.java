package nl.nanda.transfer.dao;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import nl.nanda.account.Amount;
import nl.nanda.transfer.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// TODO: Auto-generated Javadoc
/**
 * The Interface TransferRepository.
 */
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    /**
     * Find by entity id.
     *
     * @param id the id
     * @return the transfer
     */
    public Transfer findByEntityId(Integer id);

    /**
     * Find by day.
     *
     * @param day the day
     * @return the transfer
     */
    public Transfer findByDay(Date day);

    /**
     * Find by day after.
     *
     * @param day the day
     * @return the list
     */
    public List<Transfer> findByDayAfter(Date day);

    /**
     * Find by totaal greater than.
     *
     * @param amount the amount
     * @return the list
     */
    public List<Transfer> findByTotaalGreaterThan(Amount amount);

    /**
     * Find by credit.
     *
     * @param id the id
     * @return the list
     */
    @Query("select t from Transfer t where t.credit = :id")
    public List<Transfer> findByCredit(@Param("id") UUID id);

    /**
     * Find by debet.
     *
     * @param id the id
     * @return the list
     */
    @Query("select t from Transfer t where t.debet = :id")
    public List<Transfer> findByDebet(@Param("id") UUID id);

    /* (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
     */
    @Override
    public List<Transfer> findAll();

}
