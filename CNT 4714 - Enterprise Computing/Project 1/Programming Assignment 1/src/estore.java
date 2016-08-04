/** 
 * Name: Robert Arango
 * Course: CNT 4714 – Fall 2015
 * Assignment Title: Program 1 – Event-driven Programming
 * Date: Sunday September 13, 2015
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class estore extends JFrame
{
	/**
	 * Variables
	 */
	static String[][] Inventory;
	static String[][] Transactions     = new String[100][8];
	static String[] CurrentTransaction = new String[8];
	static String InventoryFile        = "inventory.txt";

	static DecimalFormat Money        = new DecimalFormat("0.00");
	static SimpleDateFormat TimeStamp = new SimpleDateFormat ("YYMMDDHHMMSS");
	static SimpleDateFormat DateTime  = new SimpleDateFormat ("M/d/yyyy hh:mm:ss a zzz");
	
	static int TransactionId = 0;
	static int ItemNumber = 1;
	static int OrderSubtotalCount = 0;
	static double OrderSubtotal   = 0.00;
	
	
	/**
	 * Button Variables
	 */
	static JButton ButtonProcessItem = new JButton("Process Item #" + ItemNumber);
	static JButton ButtonConfirmItem = new JButton("Confirm Item #" + ItemNumber);
	static JButton ButtonViewOrder   = new JButton("View Order");
	static JButton ButtonFinishOrder = new JButton("Finish Order");
	static JButton ButtonNewOrder    = new JButton("New Order");
	static JButton ButtonExit        = new JButton("Exit");
	
	
	/**
	 * Text Field Variables
	 */
	static JTextField TextFieldNumberOfItems = new JTextField();
	static JTextField TextFieldBookId        = new JTextField();
	static JTextField TextFieldItemQuantity  = new JTextField();
	static JTextField TextFieldItemInfo      = new JTextField();
	static JTextField TextFieldOrderSubTotal = new JTextField();
	
	
	/**
	 * Label Variables
	 */
	static JLabel LabelNumberOfItems = new JLabel("Enter number of items in this order:", SwingConstants.RIGHT);
	static JLabel LabelBookId        = new JLabel("Enter Book ID for Item #" + ItemNumber + ":", SwingConstants.RIGHT);
	static JLabel LabelItemQuantity  = new JLabel("Enter quantity for item #" + ItemNumber + ":", SwingConstants.RIGHT);
	static JLabel LabelItemInfo      = new JLabel("Item #1 info:", SwingConstants.RIGHT);
	static JLabel LabelOrderSubTotal = new JLabel("Order subtotal for 0 item(s):", SwingConstants.RIGHT);
	
	
	/**
	 * UI Variables
	 */
	static JFrame Frame          = new JFrame();
	static JPanel PanelMain      = new JPanel();
	static JPanel PanelTextField = new JPanel();
	static JPanel PanelButton    = new JPanel();
	
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main( String[] args ) throws IOException
	{
		Inventory = getInventory(InventoryFile);
		
		setUserInterface();
		newOrder();
		attachEventListeners();
	}
	
	
	public static double getDiscount(double d)
	{
		if ( 0 < d && d < 5 )
			return 0.0;
		
		else if ( 4 < d && d < 10 )
			return 0.1;
		
		else if ( 9 < d && d < 15 )
			return 0.15;
		
		else
			return 0.2;
	}
	
	
	public static void processItem()
	{
		if ( TextFieldNumberOfItems.getText().equals("") || TextFieldBookId.getText().equals("") || TextFieldItemQuantity.getText().equals("") )
		{
			String message = "There were errors in for form:\n";
			
			if ( TextFieldNumberOfItems.getText().equals("") )
				message = message + "\nMissing number of items in the order";
			
			if ( TextFieldBookId.getText().equals("") )
				message = message + "\nMissing Book ID";
			
			if ( TextFieldItemQuantity.getText().equals("") )
				message = message + "\nMissing quantity of book order";
			
			JOptionPane.showMessageDialog(null, message);
		}
		
		else
		{
			boolean FlagOrderItemsIsInteger=false;
			try
			{
				Integer.parseInt(TextFieldNumberOfItems.getText());
				FlagOrderItemsIsInteger=true;
			}
			catch (NumberFormatException e)
			{
		        JOptionPane.showMessageDialog(null,TextFieldNumberOfItems.getText()+" is not a number.\nYou must enter a number for the Number of Items in this Order.");
		    }
	
			boolean FlagItemQuantityIsInteger=false;
			try
			{
				Integer.parseInt(TextFieldNumberOfItems.getText());
				FlagItemQuantityIsInteger=true;
			}
			catch (NumberFormatException e)
			{
		        JOptionPane.showMessageDialog(null,TextFieldNumberOfItems.getText()+" is not a number.\nYou must enter a number for the Quantity of Items you want to order.");
		    }
			
			if (FlagOrderItemsIsInteger && FlagItemQuantityIsInteger)
			{	
				boolean ValidBookId = false;
				for (int i=0; i < Inventory.length; i++)
				{
					if (TextFieldBookId.getText().equals(Inventory[i][0]))
					{
						ValidBookId = true;
						CurrentTransaction[1] = Inventory[i][0];
						CurrentTransaction[2] = Inventory[i][1];
						CurrentTransaction[3] = Inventory[i][2];
					}
				}
					
				if (ValidBookId)
				{					
					ButtonProcessItem.setEnabled(false);
					ButtonConfirmItem.setEnabled(true);
					
					TextFieldNumberOfItems.setEnabled(false);
					TextFieldNumberOfItems.setBackground(Color.LIGHT_GRAY);
					
					LabelItemInfo.setText("Item #" + ItemNumber + " info:");
					
					CurrentTransaction[4] = TextFieldItemQuantity.getText();
					CurrentTransaction[5] = Double.toString(getDiscount(Double.parseDouble(TextFieldItemQuantity.getText())));
					
					double DiscountAmount = (Double.parseDouble(TextFieldItemQuantity.getText()) * Double.parseDouble(CurrentTransaction[3])) * 
							getDiscount(Double.parseDouble(TextFieldItemQuantity.getText()));
					CurrentTransaction[6] = Double.toString((Double.parseDouble(TextFieldItemQuantity.getText()) * 
							Double.parseDouble(CurrentTransaction[3]) - DiscountAmount));
					
					String ID       = CurrentTransaction[1];
					String Name     = CurrentTransaction[2];
					String Price    = "$" + CurrentTransaction[3];
					String Quantity = CurrentTransaction[4];
					String Discount = (int)(100*Double.parseDouble(CurrentTransaction[5])) + "%";
					String Total    = "$" + Money.format(Double.parseDouble(CurrentTransaction[6]));
					
					TextFieldItemInfo.setText(ID + " " + Name + " " + Price + " " + Quantity + " " + Discount + " " + Total);
				}
				
				else
				{
					JOptionPane.showMessageDialog(null,"Book ID "+TextFieldBookId.getText()+" not in file");
				}
			}
		}
		
	}
	
	
	public static void confirmItem()
	{		
		JOptionPane.showMessageDialog(null,"Item #"+ ItemNumber + " accepted");
		ItemNumber++;
		
		ButtonProcessItem.setEnabled(true);
		ButtonProcessItem.setText("Process Item #" + ItemNumber);
		
		ButtonConfirmItem.setEnabled(false);
		ButtonConfirmItem.setText("Confirm Item #" + ItemNumber);
		
		ButtonViewOrder.setEnabled(true);
		ButtonFinishOrder.setEnabled(true);
		
		LabelBookId.setText("Enter Book ID for Item #" + ItemNumber + ":");
		TextFieldBookId.setText("");
		
		LabelItemQuantity.setText("Enter Quantity for Item #" + ItemNumber + ":");
		TextFieldItemQuantity.setText("");
		
		OrderSubtotal = OrderSubtotal + Double.parseDouble(CurrentTransaction[6]);
		
		LabelOrderSubTotal.setText("Order subtotal for " + (ItemNumber - 1) + " item(s):");
		TextFieldOrderSubTotal.setText('$' + Money.format(OrderSubtotal));
		
		Date Now = new Date();
		
		Transactions[TransactionId][0] = TimeStamp.format(Now);
		Transactions[TransactionId][1] = CurrentTransaction[1];
		Transactions[TransactionId][2] = CurrentTransaction[2];
		Transactions[TransactionId][3] = CurrentTransaction[3];
		Transactions[TransactionId][4] = CurrentTransaction[4];
		Transactions[TransactionId][5] = CurrentTransaction[5];
		Transactions[TransactionId][6] = CurrentTransaction[6];
		Transactions[TransactionId][7] = DateTime.format(Now);
		
		transactionsPrinter(TransactionId);
		
		TransactionId++;
		
		if ( (ItemNumber - 1) == Integer.parseInt(TextFieldNumberOfItems.getText()) )
		{
			ButtonProcessItem.setEnabled(false);
			ButtonProcessItem.setText("Process Item");
			
			ButtonConfirmItem.setEnabled(false);
			ButtonConfirmItem.setText("Confirm Item");
			
			LabelBookId.setText("");
			TextFieldBookId.setEnabled(false);
			TextFieldBookId.setText("");
			TextFieldBookId.setBackground(Color.LIGHT_GRAY);
			
			LabelItemQuantity.setText("");
			TextFieldItemQuantity.setEnabled(false);
			TextFieldItemQuantity.setText("");
			TextFieldItemQuantity.setBackground(Color.LIGHT_GRAY);
		}
	}
	
	
	public static void viewOrder()
	{
		int ArraySize = 0;
		for (int i = 0; i < Transactions.length; i++)
		{
			if (Transactions[i][0] != null)
			{
				ArraySize++;
			}
		}
		System.out.println(ArraySize);
		
		String Receipt = "";
		
		for (int i = 0; i < ArraySize; i++)
		{
			String ID       = Transactions[i][1];
			String Name     = Transactions[i][2];
			String Price    = "$" + Transactions[i][3];
			String Quantity = Transactions[i][4];
			String Discount = (int)(100*Double.parseDouble(Transactions[i][5])) + "%";
			String Total    = "$" + Money.format(Double.parseDouble(Transactions[i][6]));
			
			Receipt = Receipt + (i+1) + ". " + ID + " " + Name + " " + Price + " " + Quantity + " " + Discount + " " + Total + "\n";
		}
		
		JOptionPane.showMessageDialog(null, Receipt);
	}
	
	
	public static void finishOrder()
	{
		int ArraySize = 0;
		for (int i = 0; i < Transactions.length; i++)
		{
			if (Transactions[i][0] != null)
			{
				ArraySize++;
			}
		}
		
		Date Now = new Date();
		
		String Receipt = "";
		Receipt = Receipt + "Date: " + DateTime.format(Now) + "\n\nNumber of line items: " + ArraySize +
				"\n\nItem# / ID / Title / Price / Qty / Disc % / Subtotal:\n\n";
		
		for (int i = 0; i < ArraySize; i++)
		{
			String ID       = Transactions[i][1];
			String Name     = Transactions[i][2];
			String Price    = "$" + Transactions[i][3];
			String Quantity = Transactions[i][4];
			String Discount = (int)(100*Double.parseDouble(Transactions[i][5])) + "%";
			String Total    = "$" + Money.format(Double.parseDouble(Transactions[i][6]));
			
			Receipt = Receipt + (i+1) + ". " + ID + " " + Name + " " + Price + " " + Quantity + " " + Discount + " " + Total + "\n";
		}
		
		double TaxAmount = 0.06 * OrderSubtotal;
		double FinalTotal = TaxAmount + OrderSubtotal;
		
		Receipt = Receipt + "\nOrder subtotal: $" + Money.format(OrderSubtotal) + "\n\nTax Rate:   6%\n\nTax Amount:   $" + Money.format(TaxAmount) + "\n\n" +
				"Order total: $" + Money.format(FinalTotal) + "\n\nThanks for shopping at Mark's Ye Olde Book Shoppe!";
		
		JOptionPane.showMessageDialog(null, Receipt);
	}
	
	
	public static void newOrder()
	{
		ButtonProcessItem.setEnabled(true);
		ButtonConfirmItem.setEnabled(false);
		ButtonViewOrder.setEnabled(false);
		ButtonFinishOrder.setEnabled(false);
		
		TextFieldNumberOfItems.setEnabled(true);
		TextFieldNumberOfItems.setBackground(Color.WHITE);
		TextFieldNumberOfItems.setText("");
		
		TextFieldBookId.setEnabled(true); 
		TextFieldBookId.setBackground(Color.WHITE);
		TextFieldBookId.setText("");
		
		TextFieldItemQuantity.setEnabled(true); 
		TextFieldItemQuantity.setBackground(Color.WHITE);
		TextFieldItemQuantity.setText("");
		
		LabelItemInfo.setText("Item #1 info:");
		TextFieldItemInfo.setText("");
		TextFieldItemInfo.setBackground(Color.LIGHT_GRAY);
		TextFieldItemInfo.setEnabled(false);
		
		LabelOrderSubTotal.setText("Order subtotal for 0 item(s):");
		TextFieldOrderSubTotal.setText("");
		TextFieldOrderSubTotal.setBackground(Color.LIGHT_GRAY);
		TextFieldOrderSubTotal.setEnabled(false);
		
		for (int i = 0; i < Transactions.length; i++)
		{
			Transactions[i][0] = null;
			Transactions[i][1] = null;
			Transactions[i][2] = null;
			Transactions[i][3] = null;
			Transactions[i][4] = null;
			Transactions[i][5] = null;
			Transactions[i][6] = null;
			Transactions[i][7] = null;
		}
		
		for (int i = 0; i < CurrentTransaction.length; i++)
			CurrentTransaction[i] = null;
		
		TransactionId      = 0;
		ItemNumber         = 1;
		OrderSubtotalCount = 0;
		OrderSubtotal      = 0.00;
	}
	
	public static void transactionsPrinter(int Index)
	{	
		String PrintOut = "";
		PrintOut = PrintOut + Transactions[Index][0] + ", " + Transactions[Index][1] + ", " + Transactions[Index][2] + ", " + 
				Transactions[Index][3] + ", " + Transactions[Index][4] + ", " + Transactions[Index][5] + ", " + 
				Transactions[Index][6] + ", " + Transactions[Index][7] + "\n";
		
		
		Writer writer = null;
		try
		{
		    writer = new BufferedWriter(new FileWriter("transactions.txt", true));
		    writer.write(PrintOut);
		    writer.close();
		}
		catch (IOException ex)
		{
		  System.out.println("IO Exception Occurred");
		}
	}
	
	
	public static void attachEventListeners()
	{
		ButtonProcessItem.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				processItem();
			}
		});
		
		ButtonConfirmItem.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				confirmItem();
			}
		});
		
		ButtonViewOrder.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				viewOrder();
			}
		});
		
		ButtonFinishOrder.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				finishOrder();
			}
		});
		
		ButtonNewOrder.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				newOrder();
			}
		});
		
		ButtonExit.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				System.out.println("Exit Button Pressed");
			}
		});
	}
	
	
	/**
	 *  
	 */
	public static void setUserInterface()
	{
		PanelTextField.setLayout(new GridLayout(5,2));
		PanelTextField.add(LabelNumberOfItems);
		PanelTextField.add(TextFieldNumberOfItems);
		PanelTextField.add(LabelBookId);
		PanelTextField.add(TextFieldBookId);
		PanelTextField.add(LabelItemQuantity);
		PanelTextField.add(TextFieldItemQuantity);
		PanelTextField.add(LabelItemInfo);
		PanelTextField.add(TextFieldItemInfo);
		PanelTextField.add(LabelOrderSubTotal);
		PanelTextField.add(TextFieldOrderSubTotal);
		
		PanelButton.setLayout(new FlowLayout());
		PanelButton.add(ButtonProcessItem);
		PanelButton.add(ButtonConfirmItem);
		PanelButton.add(ButtonViewOrder);
		PanelButton.add(ButtonFinishOrder);
		PanelButton.add(ButtonNewOrder);
		PanelButton.add(ButtonExit);
		
		getButtonDimensions(ButtonProcessItem);
		getButtonDimensions(ButtonConfirmItem);
		getButtonDimensions(ButtonViewOrder);
		getButtonDimensions(ButtonFinishOrder);
		getButtonDimensions(ButtonNewOrder);
		getButtonDimensions(ButtonExit);
		
		PanelMain.setLayout(new BorderLayout());
		PanelMain.add(PanelTextField,BorderLayout.NORTH);
		PanelMain.add(PanelButton,BorderLayout.SOUTH);
		
		Frame.add(PanelMain);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setSize(800,250);
		Frame.setTitle("Assignment 1 - Book Store");
		Frame.setLocationRelativeTo(null);
		Frame.setVisible(true);
	}
	
	
	/**
	 * 
	 */
	public static void getButtonDimensions( JButton button )
	{
		Dimension dimensions = button.getPreferredSize();
		button.setPreferredSize(new Dimension((int)dimensions.getWidth(),50));
	}
	
	
	/**
	 * @throws IOException
	 */
	public static String[][] getInventory( String filename ) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
	    try
	    {
			int InventorySize = countLines(filename);
	    	int InventoryPlaceHolder = 0;
	    	String[][] TempInventory = new String[InventorySize][3];
			String splitBy = ", ";
		    String line;
		    
			while ( ( line = br.readLine() ) != null )
			{
		    	String[] data = line.split(splitBy);
		    	TempInventory[InventoryPlaceHolder][0] = data[0];
		    	TempInventory[InventoryPlaceHolder][1] = data[1];
		    	TempInventory[InventoryPlaceHolder][2] = data[2];
		    	InventoryPlaceHolder++;
		    }
			
		    return TempInventory;
	    }
	    finally { br.close(); }
	}
	
	
	/**
	 * @throws IOException
	 */	
	public static int countLines( String filename ) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try
	    {
			int count = 0;
		    while( ( br.readLine() ) != null )
		    	count++;
		    
	    	return count;
	    }
	    finally { br.close(); }
	}
}
