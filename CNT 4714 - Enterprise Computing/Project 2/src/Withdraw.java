/**
 *  Name: Robert Arango
 *  Course: CNT 4714 Fall 2015
 *  Assignment Title: Project 2 – Synchronized, Cooperating Threads Under Locking
 *  Due Date: September 23, 2015
 */

import java.util.Random;

public class Withdraw implements Runnable
{
	private static Random randWithdrawAmount = new Random();
	private static Random randSleepTime = new Random();
	private BalanceBuffer sharedBalance; // reference to shared object
	private String threadName;

	/**
	 *
	 * @param name
	 * @param balance
	 */
	public Withdraw( String name, BalanceBuffer balance )
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
			// Sleeps the thread and then checks the balance and either makes a withdraw or warns that your broke
			try
			{
				int withdrawAmount = getRandomEven( 1, 50 );    // Gets a random number between 1 and 50 that is even for the withdraw
				Thread.sleep( randSleepTime.nextInt( 1000 ) );  // Puts thread to sleep randomly between 0 and 1 second

				if ( sharedBalance.get() >= withdrawAmount )
				{
					sharedBalance.set( -1 * withdrawAmount );
					System.out.printf( getPrintedLine( withdrawAmount, sharedBalance.get(), threadName ) + "\n" );
				}

				else
					System.out.printf( getPrintedLine( withdrawAmount, 0, threadName ) + "\n" );

			}

			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prints the line showing the results
	 * @param withdraw
	 * @param balance
	 * @param name
	 * @return
	 */
	public static String getPrintedLine( int withdraw, int balance , String name )
	{
		String printedWithdraw = " " + name + " withdraws $" + withdraw;
		String printedBalance  = "";

		if ( balance == 0 ) { printedBalance  = " BLOCKED - Insufficient Funds"; }    //
		else { printedBalance  = " Balance is $" + balance; }                         //

		while ( printedWithdraw.length() < 30 ) { printedWithdraw += " "; }   //
		while ( printedBalance.length() < 30 ) { printedBalance += " "; }     //

		//
		return "                              |" + printedWithdraw + "|" + printedBalance;
	}

	/**
	 * Returns an even number
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomEven(int min, int max)
	{
		int num = -1;
		while ( !isRandomEven( num ) )
			num = randWithdrawAmount.nextInt( max - min ) + min;
		return num;
	}

	/**
	 * Checks if the random number is even and return true/false
	 * @param number
	 * @return
	 */
	private static boolean isRandomEven( int number ) { return ( number % 2 ) == 0; }
}
