/**
 *  Name: Robert Arango
 *  Course: CNT 4714 Fall 2015
 *  Assignment Title: Project 2 – Synchronized, Cooperating Threads Under Locking
 *  Due Date: September 23, 2015
 */

import java.util.Random;

public class Deposit implements Runnable
{
	private static Random randDepositAmount = new Random();   // random integer object for money
	private static Random randSleepTime     = new Random();   // random sleep time object
	private BalanceBuffer sharedBalance;                      // reference to shared object
	private String threadName;                                // thread name variable

	/**
	 * Constructor for the Deposit thread objects
	 * @param name
	 * @param balance
	 */
	public Deposit( String name, BalanceBuffer balance )
	{
		threadName = name;
		sharedBalance = balance;
	}

	/**
	 * Thread Run method that executes continuously
	 */
	public void run()
	{
		// Always runs true
		while ( true )
		{
			// Sleeps the thread and then prepares and runs a deposit
			try
			{
				int depositAmount = getRandomEven( 1, 200 );   // Gets a random number between 1 and 200 that is even for the deposit
				Thread.sleep( randSleepTime.nextInt( 5000 ) ); // Puts thread to sleep randomly between 0 and 5 seconds
				sharedBalance.set( depositAmount );            // Puts the value in the buffer and then saves to the balance
				System.out.printf( getPrintedLine( depositAmount, sharedBalance.get(), threadName ) + "\n" );

			}

			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prints the line showing the results
	 * @param deposit
	 * @param balance
	 * @param name
	 * @return
	 */
	public static String getPrintedLine( int deposit, int balance , String name )
	{
		String printedDeposit = " " + name + " deposits $" + deposit;
		String printedBalance = " Balance is $" + balance;

		while ( printedDeposit.length() < 30 ) { printedDeposit += " "; }
		while ( printedBalance.length() < 30 ) { printedBalance += " "; }

		return printedDeposit + "|                              |" + printedBalance;
	}

	/**
	 * Returns an even number
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomEven( int min, int max )
	{
		int num = -1;
		while ( !isRandomEven( num ) )
			num = randDepositAmount.nextInt( max - min ) + min;
		return num;
	}

	/**
	 * Checks if the random number is even and return true/false
	 * @param number
	 * @return
	 */
	private static boolean isRandomEven( int number ) { return ( number % 2 ) == 0; }
}