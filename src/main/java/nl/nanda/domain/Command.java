package nl.nanda.domain;

/**
 * Command for inserting Entities the DB by the invoker.
 * @author Wroko
 *
 */
public interface Command {
	
	public void create();
	
}
