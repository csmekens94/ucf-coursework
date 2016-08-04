/**
 *  Name: Robert Arango
 *  Course: CNT 4714 Fall 2015
 *  Assignment Title: Project 2 – Synchronized, Cooperating Threads Under Locking
 *  Due Date: September 23, 2015
 */

public interface BalanceBuffer
{
	/**
	 * Accesses the BalanceSynced methods
	 * @param value
	 */
	public void set( int value );

	/**
	 * Accesses the BalanceSynced methods
	 * @return
	 */
	public int get();
}
