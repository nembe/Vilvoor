package nl.nanda.service.adapters;

import java.sql.Date;
import java.util.List;

import nl.nanda.jpa.transfer.Transfer;

/**
 * Creates and store the Transfer.
 * For searching an Transfer we look it up throw the Transfer Repo.
 * For saving we create a TransferCommand which handels the valdation and useful stuff before saving.
 * The invoker will do the actual saving for us.
 *
 */
public interface TransferAdapter {
	
	public List<Transfer> findAllTransfers();
	public Transfer findByDay(final Date day);
	public Transfer findByEntityId(final Integer id);
	public Integer saveTransfer(final Transfer transfer);	
	public Integer beginTransfer(final Transfer transfer);

}
