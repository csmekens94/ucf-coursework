/**
 * Name: Robert Arango
 * Course: CNT 4714 - Fall 2015 - Project Four
 * Assignment title: Developing A Three-Tier Distributed Web-Based Application
 * Date: October 25, 2015
 */

import java.io.*;
import java.lang.ClassNotFoundException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

@SuppressWarnings("serial")
public class Project4 extends HttpServlet{

	static final String DATABASE = "jdbc:mysql://localhost:3306/project4";
    
    Connection connection;
    Statement statement;
    
    @SuppressWarnings("rawtypes")
	List supplierList = new ArrayList();
    
    ResultSet resultSet;
    ResultSetMetaData metaData;
    
    String query;
    int rowsEffected;
    
    public void init( ServletConfig config ) throws ServletException
    {
    	try { Class.forName( "com.mysql.jdbc.Driver" ); }
    	catch ( ClassNotFoundException e1 ) { e1.printStackTrace(); }
    	
    	try
    	{
    		connection = DriverManager.getConnection(DATABASE, "root", "root");
    		statement  = connection.createStatement();
    	}
    	catch ( SQLException e1 ) { e1.printStackTrace(); }
    }
    
    
    public void destroy()
    {
    	try { connection.close(); }
    	catch ( SQLException e1 ) { e1.printStackTrace(); }
    }

    
    @SuppressWarnings("unchecked")
	protected void doPost ( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
    	query = request.getParameter("sqlCommand");
    	if ( query == "" ) { query = "select * from suppliers"; }
    	
    	response.setContentType("text/html");
    	PrintWriter htmlWriter = response.getWriter();
    	
    	htmlWriter.println( getHtmlData( query ) );
    	htmlWriter.println( "<h2>Database Results:</h2>" );

    	try
    	{
    		if ( ( query.contains( "insert" ) || query.contains( "update" ) ) && query.contains( "shipments" ) )
			{
    			rowsEffected = statement.executeUpdate( query );
				resultSet    = statement.executeQuery( "select snum from shipments\n" + "where quantity >= 100 " );
				
				while ( resultSet.next() )
					if ( !supplierList.contains( resultSet.getObject( 1 ) ) )
						supplierList.add( resultSet.getObject( 1 ) );
				
				for ( int i = 0; i < supplierList.size(); i++ )
					statement.executeUpdate( "update suppliers\n" + "set status = status + 5\n" + "where snum = \'" + supplierList.get( i ) + "\'" );
				
				htmlWriter.println( "<table class=\"business-logic-success\"><tr><td>" );
				htmlWriter.println( "The statement executed successfully.<br/>" + rowsEffected + " row(s) effected.<br/><br/>" );
				
				if ( supplierList.size() > 0 )
				{
					htmlWriter.println( "<span style=\"color:#FFF;\">Business Logic Detected! - Updating Supplier Status<br/><br/>" );
					htmlWriter.println( "Business Logic updated " + supplierList.size() + " supplier status marks.<br/></span>" );
					htmlWriter.println( "</td></tr></table>" );
				}
				
				else
				{
					htmlWriter.println( "</td></tr></table>" );
				}
			}
    		
    		else if ( !query.contains( "shipments" ) && ( query.contains( "insert" ) || query.contains( "delete" ) ||
    				query.contains( "update" ) || query.contains( "create" ) || query.contains( "drop" ) ) )
    		{
    			rowsEffected = statement.executeUpdate( query );
    			
				htmlWriter.println( "<table class=\"business-logic-success\"><tr><td>" );
				htmlWriter.println( "The statement executed successfully.<br/>" + rowsEffected + " row(s) effected.<br/><br/>" );
				htmlWriter.println( "</td></tr></table>" );
    		}
    		
    		else
    		{
    			resultSet = statement.executeQuery( query );
    			metaData  = resultSet.getMetaData();

    			htmlWriter.println( "<table class=\"results\"><tr>" );
    			
    			int ColumnCount = metaData.getColumnCount();
    			
    			for ( int i = 0; i < ColumnCount; i++ )
    				htmlWriter.println( "<th>" + metaData.getColumnName( i + 1 ) + "</th>" );

    			htmlWriter.println( "</tr>" );
    			
    			while ( resultSet.next() )
    			{
    				htmlWriter.println( "<tr>" );
    				
    				for ( int i = 0; i < ColumnCount; i++ )
    					htmlWriter.println( "<td>" + resultSet.getString( i + 1 ) + "</td>" );
    				
    				htmlWriter.println( "</tr>" );
    			}
    			
    			htmlWriter.println( "</table>" );
    		}
    	}
    	catch ( SQLException e1 )
    	{
    		htmlWriter.println( "<table class=\"sql-error\"><tr><td>" );
    		htmlWriter.println( "<b>Error executing the SQL statement:</b><br>" + e1.getMessage() );
    		htmlWriter.println( "</td></tr></table>" );
    	}
    	
    	htmlWriter.println( "</body></html>" );
    	htmlWriter.close();
    }

    
    protected void doGet ( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
    	query = request.getParameter("sqlCommand");
    	response.setContentType("text/html");

    	PrintWriter htmlWriter = response.getWriter();
    	
    	htmlWriter.println( getHtmlData( "" ) );
    	htmlWriter.println( "</body>" );
    	htmlWriter.println( "</html>" );
		htmlWriter.close();
    }
    
    
    public String getHtmlData( String QueryData )
    {
    	String htmlData = "";
    	
    	htmlData += "<!-- Name: Robert Arango -->";
    	htmlData += "<!-- Course: CNT 4714 – Fall 2015 – Project Four -->";
    	htmlData += "<!-- Assignment title: Developing A Three-Tier Distributed Web-Based Application -->";
    	htmlData += "<!-- Date: November 1, 2015 -->";
    	htmlData += "<!DOCTYPE html>";
    	htmlData += "<html lang=\"en\">";
    	htmlData += "<head>";
    	htmlData += "<title>Project 4 - RMA</title>";
    	htmlData += "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">";
    	htmlData += "</head>";
    	htmlData += "<body>";
    	htmlData += "<h1>Welcome to the Project 4 Remote Database Management System</h1>";
    	htmlData += "<hr>";
    	htmlData += "<p>You are connected to the Project4 database.</p>";
    	htmlData += "<p>Please enter any valid SQL query or update statement.</p>";
    	htmlData += "<p>If no query/update command is given, the Execute button will display all supplier information in the database.</p>";
    	htmlData += "<p>All execution results will appear below.</p>";
    	htmlData += "<form id=\"sqlForm\" action=\"/Project4/Project4\" method=\"post\">";
    	htmlData += "<textarea id=\"sqlCommand\" name=\"sqlCommand\" rows=\"10\">" + QueryData + "</textarea>";
    	htmlData += "<br>";
    	htmlData += "<div id=\"formButtons\">";
    	htmlData += "<input class=\"formButton\" type=\"submit\" name=\"submit\" value=\"Execute Command\" />";
    	htmlData += "<button class=\"formButton\" type=\"submit\" formmethod=\"get\" formaction=\"/Project4/\">Clear</button>";
    	htmlData += "</div>";
    	htmlData += "</form>";
    	htmlData += "<hr>";
    	
    	return htmlData;
    }
}
