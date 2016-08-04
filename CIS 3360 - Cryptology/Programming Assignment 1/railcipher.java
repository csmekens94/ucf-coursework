/**
 * @author 		Robert Arango
 * @date 		10/19/2014
 * @course		CIS 3360
 * @assignment	Rail Fence Cipher
 */

import java.lang.String;
import java.io.*;

public class railcipher {
	
	public static void main(String[] args) throws IOException
	{
		if (args.length < 2)
		{
			System.out.println("  > You didn't pass enough parameters, now exiting...\n\n");
		}
		
		else if (args.length > 2)
		{
			System.out.println("  > You passed too many parameters, now exiting...\n\n");
		}
		
		else
		{
			String fileFenceDepth = args[0];
			String filePlainText = args[1];
			
			int fenceDepth = getFenceDepth(fileFenceDepth);
			int[] plainText = new int[10000];
			int[] encryptedText = new int[10000];
					
			plainText = getPlainText(filePlainText);
			encryptedText = encryptPlainText(fenceDepth, plainText);
			
			printRailFenceCipher(fenceDepth, plainText, encryptedText);
		}
		
		System.out.println("Plaintext has been encrypted, now exiting...");
		System.exit(0);
	}
	
	
	public static int getFenceDepth(String file)
	{
		int fenceDepth = 0;
		
		try 
		{
			BufferedReader BR = new BufferedReader (new FileReader (file));
			String buffer = BR.readLine();
			BR.close();
			fenceDepth = Integer.parseInt(buffer);
		}
		
		catch (FileNotFoundException e)
		{ 
			e.printStackTrace();
			System.out.println("*** System exiting because of an error\n*** Cannot find file: " + file);
			System.exit(0);
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("*** System exiting because of an error\n*** Input exception error when reading file");
			System.exit(0);
		}
		
		return fenceDepth;
	}
	
	
	public static int[] getPlainText(String file)
	{
		int[] plainText = new int[10000];
		
		try 
		{
			BufferedReader BR = new BufferedReader (new FileReader (file));
			
			int c;
			int placeHolder = 0;
			
			while ((c = BR.read()) != -1)
			{
				if (c > 64 && c < 91)
				{
					c = c + 32;
				}
				
				if (c > 96 && c < 123)
				{
					plainText[placeHolder] = c;
					placeHolder++;
				}
			}
			
			BR.close();
		}
		
		catch (FileNotFoundException e)
		{ 
			e.printStackTrace();
			System.out.println("*** System exiting because of an error\n*** Cannot find file: " + file);
			System.exit(0);
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("*** System exiting because of an error\n*** Input exception error when reading file");
			System.exit(0);
		}
		
		return plainText;
	}
	
	
	public static int[] encryptPlainText(int fenceDepth, int[] plainText)
	{
		int[] encryptedText = new int[10000];
		int placeHolderEncrypted = 0;
		
		for (int i = 0; i < fenceDepth; i++)
		{
			int placeHolder = i;
			int stepOne = ((fenceDepth - (i + 1)) * 2);
			int stepTwo = i * 2;
			
			if (placeHolder != (fenceDepth - 1))
			{
				encryptedText[placeHolderEncrypted] = plainText[placeHolder];
				placeHolderEncrypted++;
			}
			
			while (plainText[placeHolder] != 0)
			{
				placeHolder = placeHolder + stepOne;
				if (plainText[placeHolder] != 0 && stepTwo > 0)
				{
					encryptedText[placeHolderEncrypted] = plainText[placeHolder];
					placeHolderEncrypted++;
				}
				
				placeHolder = placeHolder + stepTwo;
				if (plainText[placeHolder] != 0 && stepOne > 0)
				{	
					encryptedText[placeHolderEncrypted] = plainText[placeHolder];
					placeHolderEncrypted++;
				}
			}
		}
		
		return encryptedText;
	}
	
	
	public static void printRailFenceCipher(int fenceDepth, int[] plainText, int[] encryptedText)
	{
		int placeHolder = 0;
		System.out.println("\nRail Fence Depth: " + fenceDepth + "\n\n\nPlaintext:");
		
		while (plainText[placeHolder] != 0)
		{	
			if ((placeHolder % 80) == 0)
			{
				System.out.print("\n");
			}
				
			System.out.print((char)plainText[placeHolder]);
			placeHolder++;
		}
		
		placeHolder = 0;
		System.out.println("\n\n\nCiphertext:");
		
		while (encryptedText[placeHolder] != 0)
		{
			if ((placeHolder % 80) == 0)
			{
				System.out.print("\n");
			}
				
			System.out.print((char)encryptedText[placeHolder]);
			placeHolder++;
		}
		
		System.out.println("\n\n");
	}

}