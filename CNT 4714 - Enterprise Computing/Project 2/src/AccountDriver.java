/**
 *  Name: Robert Arango
 *  Course: CNT 4714 Fall 2015
 *  Assignment Title: Project 2 – Synchronized, Cooperating Threads Under Locking
 *  Due Date: September 23, 2015
 */

// Based my code off of the sample program, it was really instrumental in helping me
// understand the locking and thread synchronization. The Deposit threads run randomly
// between 0 and 5 seconds. The Withdraw threads run randomly every 0 to 1 second.

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AccountDriver
{
	public static void main( String[] args )
	{
		// Creates 2 Thread Pools; 5 for withdraws; 3 for deposits
		ExecutorService depositThreads  = Executors.newFixedThreadPool( 3 );
		ExecutorService withdrawThreads = Executors.newFixedThreadPool( 5 );

		// Setup the shared account balance object
		BalanceBuffer accountBalance = new BalanceSynced();

		// Print out the statement header
		System.out.println("                              |                              |                              ");
		System.out.println(" Deposit Threads              | Withdrawal Threads           | Balance                      ");
		System.out.println("------------------------------|------------------------------|------------------------------");

		// Begin executing the threads
		try
		{
			depositThreads.execute( new Deposit( "Thread 1", accountBalance ) );
			depositThreads.execute( new Deposit( "Thread 2", accountBalance ) );
			depositThreads.execute( new Deposit( "Thread 3", accountBalance ) );

			// Pause the execution of the withdraws, to give the deposits a chance to give some money.
			TimeUnit.SECONDS.sleep(1);

			withdrawThreads.execute( new Withdraw( "Thread 4", accountBalance ) );
			withdrawThreads.execute( new Withdraw( "Thread 5", accountBalance ) );
			withdrawThreads.execute( new Withdraw( "Thread 6", accountBalance ) );
			withdrawThreads.execute( new Withdraw( "Thread 7", accountBalance ) );
			withdrawThreads.execute( new Withdraw( "Thread 8", accountBalance ) );
		}

		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
