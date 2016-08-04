/**
 *  Name: Robert Arango
 *  Course: CNT 4714 Fall 2015
 *  Assignment Title: Project 2 – Synchronized, Cooperating Threads Under Locking
 *  Due Date: September 23, 2015
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BalanceSynced implements BalanceBuffer
{
	private Lock accessLock     = new ReentrantLock();         // Sets up a lock object to lock all threads but 1 at a time
	private Condition canAccess = accessLock.newCondition();   // Create a condition that will signal the threads on when to access and wait
	private int balance         = 0;                           // Set the initial balance value at $0
	private boolean occupied    = false;                       // Simple boolean to tell conditions what threads can do

	/*
	 * Method to set the balance according to the value passed, deposit or withdraw
	 * @see BalanceBuffer#set(int)
	 */
	public void set( int value )
	{
		accessLock.lock(); // Locks the object from being written to

		// Attempts to update the balance on the account or tells the thread to wait
		try
		{
			while ( occupied ) { canAccess.await(); }
			balance   += value;
			occupied  = true;
			canAccess.signal();
		}

		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}

		//
		finally
		{
			accessLock.unlock();
			occupied = false;
		}
	}

	/**
	 *  Retrieves the balance and passes it along
	 */
	public int get()
	{
		return balance;
	}
}
