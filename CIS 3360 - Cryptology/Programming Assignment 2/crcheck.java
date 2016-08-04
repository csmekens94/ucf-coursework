/**
 * @author 		Robert Arango
 * @date 		11/24/2014
 * @course		CIS 3360
 * @assignment	CRC Codes
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;


public class crcheck {

	/**
	 * Static Variable for Polynomial
	 * POLYNOMIAL:	x^15 + x^13 + x^6 + x^4 + x^1 + 1
	 * BINARY:		1010 0000 0101 0011
	 * @param	CRC_POLYNOMIAL	Integer representation of polynomial
	 */
	public static int CRC_POLYNOMIAL = Integer.parseInt("1010000001010011",2);
	
	
	/**
	 * (REQUIRED) Main Method
	 * Main program method which gets everything started.
	 * @param	args[0]		1st is 'c' or 'v', nothing else is accepted
	 * @param	args[1]		2nd is the filename to be verified or calculated
	 */
	public static void main(String[] args)
	{	
		if (args.length == 2 && args[0].length() == 1)
		{
			int mode = (int)args[0].charAt(0);
			String fileName = args[1];
			String[] hexChecksum = new String[8];
			
			if (mode == 99 || mode == 118) // If parameter equals 'c' or 'v'
			{
				int[] inputArray = getInputAsArray(fileName);
				int count = getArrayCount(inputArray);
				
				if ((mode == 99 && count < 512) || (mode == 118 && count < 512))
				{
					if (mode == 118 && count < 512)
					{
						System.out.println("\nYou wanted to verify the Checksum, but a checksum does not exist.\nI'll try calculating the checksum instead.\n");
					}
					
					System.out.println("\nCRC16 Input text from file " + fileName + ":\n");
					printOriginalMessage(inputArray);
					
					System.out.println("\nCRC16 calculation progress:\n");
					
					hexChecksum[0] = calculateCRC(inputArray, 64);
					hexChecksum[1] = calculateCRC(inputArray, 128);
					hexChecksum[2] = calculateCRC(inputArray, 192);
					hexChecksum[3] = calculateCRC(inputArray, 256);
					hexChecksum[4] = calculateCRC(inputArray, 320);
					hexChecksum[5] = calculateCRC(inputArray, 384);
					hexChecksum[6] = calculateCRC(inputArray, 448);
					hexChecksum[7] = calculateCRC(inputArray, 504);
					
					printLinesWithChecksums(inputArray, hexChecksum, "calculate");
					writeChecksumToFile(inputArray, hexChecksum[7], fileName);
					
					System.out.println("CRC16 result : " + hexChecksum[7]);
					System.out.println(fileName + " resultant CRC " + hexChecksum[7] + " written to file.\n");
					
				}
				
				else if ((mode == 99 && count == 512) || (mode == 118 && count == 512))
				{
					if (mode == 99 && count == 512)
					{
						System.out.println("\nYou wanted to calculate the Checksum, but a checksum already exists.\nI'll try verifying the checksum instead.\n");
					}
					
					String originalCRC = "";
					
					for (int i = 504; i < 512; i++)
					{
						originalCRC += (char)inputArray[i];
					}
					
					System.out.println("\nCRC16 Input text from file " + fileName + ":\n");
					printOriginalMessage(inputArray);
					
					System.out.println("\nCRC16 calculation progress:\n");
					
					hexChecksum[0] = verifyCRC(inputArray, 64);
					hexChecksum[1] = verifyCRC(inputArray, 128);
					hexChecksum[2] = verifyCRC(inputArray, 192);
					hexChecksum[3] = verifyCRC(inputArray, 256);
					hexChecksum[4] = verifyCRC(inputArray, 320);
					hexChecksum[5] = verifyCRC(inputArray, 384);
					hexChecksum[6] = verifyCRC(inputArray, 448);
					hexChecksum[7] = verifyCRC(inputArray, 504);
					
					printLinesWithChecksums(inputArray, hexChecksum, "verify");
					
					System.out.println("CRC16 result : " + hexChecksum[7] + "\n");
					
					if (originalCRC.equals(hexChecksum[7]))
					{
						System.out.println("CRC16 verification passed.");
					}
					
					else
					{
						System.out.println("CRC16 verification failed.");
					}
				}
			}
			
			else
			{
				System.out.println("\nThe 1st argument can only be:" + "   1. 'c' for Calculate CRC or\n   2. 'v' for Verify CRC" +
						"\n\nPlease correct this issue and try again.\n\nNow exiting...\n");
			}
		}
		
		else
		{
			System.out.println("\nYou didn't pass the correct amount of parameters for the program to run properly.\n\nOnly 2 parameters are allowed:" +
					"\n   1. Either a 'c' for Calculate CRC or 'v' for Verify CRC\n   2. The filename for the file that is to be calculated or verified" + 
						"(eg. input1A.1)\n\nNow exiting...\n");
		}
		
		System.exit(0);
	}
	
	
	/**
	 * REQUIRED - File Reader Method
	 * Method that takes as input two 32 bit unsigned binary integers and returns the XOR result
	 * @param	file	the filename of the file to to verified or calculated
	 */
	public static int[] getInputAsArray(String file)
	{
		int[] inputArray = new int[512];
		
		try 
		{
			BufferedReader buffer = new BufferedReader (new FileReader (file));
			String input = buffer.readLine();
			
			for (int i = 0; i < (input.length()); i++)
			{
				inputArray[i] = (int)input.charAt(i);
			}
			
			buffer.close();
		}
		
		catch (FileNotFoundException e)
		{ 
			System.out.println("\nYour file named '" + file + "' was not able to be found.\nPlease" + 
					" check to ensure that the file exists.\n\nNow exiting...\n");
			System.exit(0);
		}
		
		catch (IOException e)
		{
			System.out.println("\nError in IO Exception occured, please ensure your file only contains ASCII characters.\n\nNow exiting...\n");
			System.exit(0);
		}
		
		return inputArray;
	}
	
	
	/**
	 * REQUIRED - XOR Method
	 * Takes as input, two 32 bit unsigned binary integers and returns the XOR result
	 * @param	firstInt	first integer in XOR calculation
	 * @param	secondInt	second integer in XOR calculation
	 */
	public static String getXOR(int input, int divisor)
	{
		int result = input ^ divisor;
		
		return Integer.toBinaryString(result);
	}
	
	
	/**
	 * REQUIRED - Calculate CRC Method
	 * @param	inputArray	The array that holds integers generated from the input file
	 */
	public static String calculateCRC(int[] inputArray, int numChars)
	{	
		String inputArrayAsBinary = "";
		String tempBinaryString = "";
		String hexChecksum = "";
		
		for (int i = 0; i < numChars; i++)
		{
			if (inputArray[i] == 0)
			{
				inputArray[i] = 46;
			}
			
			tempBinaryString = Integer.toBinaryString(inputArray[i]);
			
			while (tempBinaryString.length() < 8)
			{
				tempBinaryString = "0" + tempBinaryString; 
			}
			
			inputArrayAsBinary += tempBinaryString;
			tempBinaryString = "";
			
			if (i == (numChars - 1))
			{
				inputArrayAsBinary += "0000000000000000";  // Pad with 16 0's
			}
		}
		
		
		boolean crcCalculationComplete = false;
		String tempValue = "";
		int j = 0;
		
		while (!crcCalculationComplete)
		{
			if (j != inputArrayAsBinary.length())
			{
				if (tempValue.length() == 0)
				{
					tempValue += inputArrayAsBinary.charAt(j);
					j++;
				}
				
				else if ((tempValue.length() > 0) && (tempValue.length() < 16))
				{
					if ((int)tempValue.charAt(0) == 48)
					{
						tempValue = tempValue.substring(1);
					}
					
					else
					{
						if ((j + 1) != inputArrayAsBinary.length())
						{
							tempValue = tempValue + inputArrayAsBinary.charAt(j);
							j++;
						}
						
						else
						{
							j++;
						}
					}
				}
				
				else if (tempValue.length() == 16)
				{
					tempValue = getXOR(Integer.parseInt(tempValue,2),CRC_POLYNOMIAL);
				}
			}
			
			else
			{
				crcCalculationComplete = true;
				
				while (tempValue.length() != 16)
				{
					tempValue = "0" + tempValue;
				}
				
				hexChecksum = Integer.toHexString(Integer.parseInt(tempValue,2));
				
				while (hexChecksum.length() != 8)
				{
					hexChecksum = "0" + hexChecksum;
				}
			}
		}
		
		return hexChecksum;
	}
	
	
	/**
	 * REQUIRED - Verify CRC Method
	 * 
	 */
	public static String verifyCRC(int[] inputArray, int numChars)
	{	
		String inputArrayAsBinary = "";
		String tempBinaryString = "";
		String hexChecksum = "";
		
		for (int i = 0; i < numChars; i++)
		{
			if (inputArray[i] == 0)
			{
				inputArray[i] = 46;
			}
			
			tempBinaryString = Integer.toBinaryString(inputArray[i]);
			
			while (tempBinaryString.length() < 8)
			{
				tempBinaryString = "0" + tempBinaryString; 
			}
			
			inputArrayAsBinary += tempBinaryString;
			tempBinaryString = "";
			
			if (i == (numChars - 1))
			{
				inputArrayAsBinary += "0000000000000000";  // Pad with 16 0's
			}
		}
		
		
		boolean crcCalculationComplete = false;
		String tempValue = "";
		int j = 0;
		
		while (!crcCalculationComplete)
		{
			if (j != inputArrayAsBinary.length())
			{
				if (tempValue.length() == 0)
				{
					tempValue += inputArrayAsBinary.charAt(j);
					j++;
				}
				
				else if ((tempValue.length() > 0) && (tempValue.length() < 16))
				{
					if ((int)tempValue.charAt(0) == 48)
					{
						tempValue = tempValue.substring(1);
					}
					
					else
					{
						if ((j + 1) != inputArrayAsBinary.length())
						{
							tempValue = tempValue + inputArrayAsBinary.charAt(j);
							j++;
						}
						
						else
						{
							j++;
						}
					}
				}
				
				else if (tempValue.length() == 16)
				{
					tempValue = getXOR(Integer.parseInt(tempValue,2),CRC_POLYNOMIAL);
				}
			}
			
			else
			{
				crcCalculationComplete = true;
				
				while (tempValue.length() != 16)
				{
					tempValue = "0" + tempValue;
				}
				
				hexChecksum = Integer.toHexString(Integer.parseInt(tempValue,2));
				
				while (hexChecksum.length() != 8)
				{
					hexChecksum = "0" + hexChecksum;
				}
			}
		}
		
		return hexChecksum;
	}
	
	
	/**
	 * Optional - 
	 * 
	 */
	public static void printOriginalMessage(int[] inputArray)
	{
		for (int i = 0; i < 512; i++)
		{
			if ( ((i % 64) == 0) && (i != 0) )
			{
				System.out.print("\n");
			}
			
			if (inputArray[i] != 0)
			{
				System.out.print((char)inputArray[i]);
			}
			
			else
			{
				System.out.print(" ");
			}
			
			if (i == 511)
			{
				System.out.print("\n");
			}
		}
	}
	
	
	
	/**
	 * Optional - Print Lines With Checksums
	 */
	public static void printLinesWithChecksums(int[] inputArray, String[] hexChecksum, String mode)
	{
		int j = 0;
		
		for (int i = 0; i < 505; i++)
		{
			if ( ((i % 64) == 0) && (i != 0) )
			{
				System.out.print(" - " + hexChecksum[j] + "\n");
				j++;
			}
			
			if ( (504 == i) && (i != 0) && (mode.equals("calculate")))
			{
				System.out.print(hexChecksum[j] + " - " + hexChecksum[j] + "\n");
				j++;
			}
			
			if ( (504 == i) && (i != 0) && (mode.equals("verify")))
			{
				System.out.print(""+(char)inputArray[504] + (char)inputArray[505] + (char)inputArray[506] + (char)inputArray[507] + (char)inputArray[508] +
						(char)inputArray[509] + (char)inputArray[510] + (char)inputArray[511] + " - " + hexChecksum[j] + "\n");
				j++;
			}
			
			if ((inputArray[i] != 0) && (i != 504))
			{
				System.out.print((char)inputArray[i]);
			}
		}
		
		System.out.print("\n");
	}
	
	
	
	/**
	 * Optional - File Writer Method
	 */
	public static void writeChecksumToFile(int[] inputArray, String hexChecksum, String fileName)
	{
		String outputToBeWritten = "";
		
		for (int i = 0; i < 504; i++)
		{
			outputToBeWritten += (char)inputArray[i];
		}
		
		outputToBeWritten += hexChecksum;
		
		try 
		{
			String content = "This is the content to write into file";
			 
			File file = new File(fileName);
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(outputToBeWritten);
			bw.close();
		}
		
		catch (FileNotFoundException e)
		{ 
			System.out.println("\nYour file named '" + fileName + "' was not able to be found.\nPlease" + 
					" check to ensure that the file exists.\n\nNow exiting...\n");
			System.exit(0);
		}
		
		catch (IOException e)
		{
			System.out.println("\nError in IO Exception occured, please ensure your file only contains ASCII characters.\n\nNow exiting...\n");
			System.exit(0);
		}
	}
	
	
	/**
	 * Optional - Get Array Count Method
	 */
	public static int getArrayCount(int[] inputArray)
	{
		int c = 0;
		
		for (int i = 0; i < inputArray.length; i++)
		{
			if (inputArray[c] != 0)
			{
				c++;
			}
		}
		
		return c;
	}
	
}
