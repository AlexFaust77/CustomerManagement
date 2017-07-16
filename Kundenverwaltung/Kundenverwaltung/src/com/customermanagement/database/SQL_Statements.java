package com.customermanagement.database;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.customermanagement.entities.Obj_Customer;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Logger_Init;
import com.customermanagement.helpers.Save_Database_Information;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQL_Statements {

	
    private boolean result = false;
    private boolean db_Reached;
    private final SimpleDateFormat date_Formatter = new SimpleDateFormat("dd-mm-yyyy");
    private ArrayList<String>lst_Cust_Nr = new ArrayList<String>();   
    private ObservableList<Obj_Order> orders = FXCollections.observableArrayList(); // new list added build new Gui
    Connection con = null;
    Statement sql_Statement = null;
    String url ="jdbc:sqlite:Bestelldb.accdb";
    String sql ="";
  
    
  public void build_Database(String str_DBname,Logger logger)  {   					// create empty Database
      
        this.url = "jdbc:sqlite:" + str_DBname;     
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            logger.debug("Connection URL " + url);                      
            sql_Statement = con.createStatement();
           
      
            sql = "CREATE TABLE KUNDE" +           						// Customer Table
              	    "(CustID         INTEGER          primary key  autoincrement    NOT NULL," +
                    " Kundennummer   CHAR(30)  	   		   NOT NULL," +
                    " Name           CHAR(30)              NOT NULL," +
                    " Vorname        CHAR(30)              NOT NULL," +
                    " Strasse        CHAR(30),                      " +
                    " Hausnummer     INT,                           " +
                    " Postleitzahl   INT,                           " +   
                    " Ort            CHAR(30)                       )"; 
                       
            
           //con.setAutoCommit(false); 
           sql_Statement.executeUpdate(sql);
           logger.info("Customer Table created");
           logger.debug("Customer Table : " + sql );
           
                      
           sql = "CREATE TABLE BESTELLUNGEN" +                          // Order Table
        		 "(OrderID		  INTEGER		primary key autoincrement NOT NULL," +			
                 " Bestellnummer  CHAR(30) 				NOT NULL," +
                 " Bestelldatum   DATE                  NOT NULL," + 
                 " Zahlungsstart  DATE                  NOT NULL," + 
                 " Zahlungsende   DATE                  NOT NULL," + 
                 " Ratenanzahl    INTEGER               NOT NULL," + 
                 " Ersterate      DOUBLE                NOT NULL," + 
                 " Folgerate      DOUBLE                NOT NULL," + 
                 " Bestellsumme   DOUBLE                NOT NULL," +
                 " Kundennummer   CHAR(30)              NOT NULL," +
                 " CustID		  INTEGER	REFERENCES KUNDE(CustID) ON UPDATE CASCADE)";		
            
          sql_Statement.executeUpdate(sql); 
          logger.info("Order Table created");
          logger.debug("Order Table : " + sql );      
                  
          Save_Database_Information save_Used_DBInfo = new Save_Database_Information();				// Save used DB
          			 				save_Used_DBInfo.save_Database_Info(str_DBname,logger);
          close_DB_con("build_Database",logger); 
         // System.exit(0);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("Build Database" + ex.getLocalizedMessage());
          //  System.exit(0);
       }
    }
  
  public boolean DatabaseConnection (String str_DBname,Logger logger) {
        
      this.url = "jdbc:sqlite:" + str_DBname;  
      try {
            Class.forName("org.sqlite.JDBC");
            result = true;
        } catch (ClassNotFoundException ex) {
            logger.error("DatabaseConnection" + ex.getLocalizedMessage());
            result = false;
        }
    
      try {
            con = DriverManager.getConnection(url);
            result = true;
        } catch (SQLException ex) {
            logger.error("DatabaseConnection_part_2 " + ex.getLocalizedMessage());
            result = false;
        }
      close_DB_con("DatabaseConnection",logger);
    logger.debug("Datenverbindung erfolgreich" + url + " " + result);
   return result;   
  }  
 //Create new Customer and save to DB
 public boolean new_Customer(Obj_Customer obj_Customer,String str_DBname,Logger logger) {				
          
        this.url = "jdbc:sqlite:" + str_DBname;
            
        try {
            open_DB_con("new_Customer",logger);
            sql_Statement = con.createStatement();
              
            sql = "INSERT INTO KUNDE(Kundennummer,Name,Vorname,Strasse,Hausnummer,Postleitzahl,Ort)" +
                  "VALUES (?,?,?,?,?,?,?)"; 
            PreparedStatement statement = con.prepareStatement(sql);
            				  statement.setString(1, obj_Customer.getCustNo());
                              statement.setString(2, obj_Customer.getLastname());
                              statement.setString(3, obj_Customer.getFirstname());
                              statement.setString(4, obj_Customer.getStreet());
                              statement.setInt(5, obj_Customer.getHouseNo());
                              statement.setInt(6, obj_Customer.getPostcode());
                              statement.setString(7, obj_Customer.getResidenz());
                              
          logger.info("Customer created");
          logger.debug("Executed SQL statement - New Customer : " + statement);
          
          statement.executeUpdate();
          statement.close();
          close_DB_con("new_Customer",logger); 
          
          db_Reached = true;
        } catch (SQLException ex) {
               logger.error("new Customer : " + ex.getLocalizedMessage());
               db_Reached = false;
        }
 return db_Reached;    
 }
 //Create new Order and save to DB 
 
 public boolean new_Order(Obj_Order objOrder, String str_DBname,Logger logger){					
     
        this.url = "jdbc:sqlite:" + str_DBname;
        
        try {
        	System.out.println("Not Parsed Dates :  == > Order Date : " + objOrder.getOrderDate() + " Payment starts : " + objOrder.getPayStart() + " Payment Ends : " + objOrder.getPayEnd()  + "\n\n");
            java.util.Date parsed = objOrder.getOrderDate();
            java.sql.Date orderDate = new java.sql.Date(parsed.getTime());
        
            java.util.Date payStartParsed = objOrder.getPayStart();
            java.sql.Date payStart = new java.sql.Date(payStartParsed.getTime());
                          
            java.util.Date payEndParsed = objOrder.getPayEnd();
            java.sql.Date payEnd = new java.sql.Date(payEndParsed.getTime());
                   
            System.out.println("Not parsed Dates :  == > Order Date : " + parsed + " Payment starts : " + payStartParsed + " Payment Ends : " + payEndParsed + "\n\n");
            logger.debug("Parsed Dates :  == > Order Date : " + orderDate + " Payment starts : " + payStart + " Payment Ends : " + payEnd + "\n\n");
            open_DB_con("new_Order",logger);
            sql_Statement = con.createStatement();
            
            // Insert Into for new Order
            sql = "INSERT INTO BESTELLUNGEN(Bestellnummer,Bestelldatum,Zahlungsstart,Zahlungsende,Ratenanzahl,Ersterate,Folgerate,Bestellsumme,Kundennummer)" +
                  "VALUES (?,?,?,?,?,?,?,?,?)"; 
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setString(1, objOrder.getOrderNo());
                              statement.setDate(2, orderDate);
                              statement.setDate(3, payStart);
                              statement.setDate(4, payEnd);
                              statement.setInt(5, objOrder.getRateCount());
                              statement.setDouble(6, objOrder.getFirstRate());
                              statement.setDouble(7, objOrder.getRate());
                              statement.setDouble(8, objOrder.getOrderSummary());
                              statement.setString(9, objOrder.getCustNo());
            
          statement.executeUpdate();
          statement.close();
          close_DB_con("new_Order",logger);
          logger.debug("Executed Statement : New Order : " + statement );              
          db_Reached = true;
        } catch (SQLException ex) {
        	System.out.println("Sql exception : " + ex.getLocalizedMessage());
            logger.error("New Order _ SQL Error : " + ex.getLocalizedMessage());
            db_Reached = false;
        }
return db_Reached;   
}

// Check for doubled Customers 
public boolean doubled_Customer_Check(Obj_Customer obj_Customer, String str_cust_Nr,String str_DBname,Logger logger) {
   boolean customer_Doubled = false;  
         Obj_Customer obj_doub_Cust = new Obj_Customer();
         obj_doub_Cust = this.getCustomer(obj_Customer, str_cust_Nr, str_DBname,logger);
    
     if( obj_doub_Cust.getCustNo() != null) {
    	    customer_Doubled = true;
        } else {
        	customer_Doubled = false;
        }
 return customer_Doubled;
 }
 //Customer Data
 public Obj_Customer getCustomer(Obj_Customer obj_Customer, String str_cust_Nr,String str_DBname, Logger logger)  {        
         
            this.url = "jdbc:sqlite:" + str_DBname;
            ObservableList<String> lst_Orders = FXCollections.observableArrayList();
            double cust_Total = 0.0;
             
        try {
        	open_DB_con("getCustomer",logger);
            sql_Statement = con.createStatement();
            // Request for Customer with customernumber
            ResultSet result = sql_Statement.executeQuery("Select * FROM KUNDE WHERE Kundennummer =" + str_cust_Nr);
            
            obj_Customer.setId(result.getInt(1));
            obj_Customer.setCustNo(result.getString(2)); 
            obj_Customer.setLastname(result.getString(3));
            obj_Customer.setFirstname(result.getString(4));
            obj_Customer.setStreet(result.getString(5));
            obj_Customer.setHouseNo(result.getInt(6));
            obj_Customer.setPostcode(result.getInt(7));
            obj_Customer.setResidenz(result.getString(8));
                        
            close_DB_con("getCustomer",logger);
            // Summary to Pay
            System.out.println("Bin hier");
            getRestSumme(str_cust_Nr,str_DBname,logger);   
            
            // Summary - Total Sales for customer
            cust_Total = getTotal_Sales(str_cust_Nr,str_DBname,logger);
            logger.debug("Customer Total Sales : " + str_cust_Nr + " Database Name " + str_DBname + "\nValue : " + cust_Total );
            obj_Customer.setCustTotal(cust_Total);
             
        } catch (SQLException ex) {
            logger.error("getCustomer SQL Error : " + ex.getLocalizedMessage());
            System.out.println(ex);
        }
        
 return obj_Customer;    
 } 
//Delete one Customer 
public boolean delete_Customer(String str_DBname,String str_cust_Nr,Logger logger){				
        this.url = "jdbc:sqlite:" + str_DBname;
    try {
    	    open_DB_con("delete_Customer",logger);
            sql_Statement = con.createStatement();
            String sql ="Delete from Kunde WHERE Kundennummer=" + str_cust_Nr;
            sql_Statement.executeUpdate(sql);
            logger.debug("Delete Customer - SQL Statement : " + sql);
            close_DB_con("delete_Customer",logger);
            logger.info("Delete Customer success");
        } catch (SQLException ex) {
            logger.error("Delete Customer _ SQL Error : " + ex.getLocalizedMessage());
        }
    	db_Reached = true;
    return db_Reached;
 }
 // Calculate Total Sales for one Customer  
 public Double getTotal_Sales(String str_cust_Nr,String str_DBname,Logger logger) {							           
    double total_Sales = 0.0;
   
    this.url = "jdbc:sqlite:" + str_DBname;
    try {
    		open_DB_con("cust_Total",logger);
            sql_Statement = con.createStatement();
            // Statement for "Bestellsumme" for all orders
            ResultSet result = sql_Statement.executeQuery("Select Bestellsumme FROM Bestellungen WHERE Kundennummer =" + str_cust_Nr);
            logger.debug("executed Query : " + sql_Statement + "\nResultset : " + result);
            
            while(result.next()) {
                  total_Sales = total_Sales + result.getDouble("Bestellsumme");
            }
            
            logger.debug("Total Sales : " + total_Sales);
        
        } catch (SQLException ex) {
            logger.error("get Total Sales _ SQL Error : " + ex.getLocalizedMessage());
        }
        close_DB_con("cust_Total",logger);
   return total_Sales;  
 }
 
 
 public Double getRestSumme(String kdNr,String databaseName,Logger logger) {
     double restSumme = 0.0;
     Date startDatum = null;
     
     this.url = "jdbc:sqlite:" + databaseName;
     try {
    	 	open_DB_con("getRestSumme",logger);
            sql_Statement = con.createStatement();
            
            ResultSet result = sql_Statement.executeQuery("Select Zahlungsstart FROM Bestellungen WHERE Kundennummer =" + kdNr);
                       
            while(result.next()) {
                  startDatum = result.getDate("Zahlungsstart");
            }
        } catch (SQLException ex) {
            logger.error("get RestSumme _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
        close_DB_con("getRestSumme",logger);
    return restSumme;
 }
 
 // Database request for one Order only needed by calculator ( get order Objects ) maybe there is a better change 16.01.2016

 public Obj_Order getOneOrderData(Obj_Order obj_Order, String str_Order_Nr, String str_cust_Nr,String str_DBname,Logger logger)  {     					
        
        this.url = "jdbc:sqlite:" + str_DBname;
        try {
        	open_DB_con("getOne_Order",logger);
            sql_Statement = con.createStatement();
            // Statement for One Order
            
            ResultSet bd_result = sql_Statement.executeQuery("Select * FROM Bestellungen WHERE Bestellnummer =" + str_Order_Nr);  
            
            //prüfen Statement korrigieren
            
            //ResultSet bd_result = sql_Statement.executeQuery("Select * FROM Bestellungen WHERE Bestellnummer =" + str_Order_Nr +
            //												 " AND WHERE Kundennummer=" + str_cust_Nr );      
            logger.debug("SQL Statement Result : " + bd_result);
            		  
            		  obj_Order.setOrderNo(bd_result.getString("Bestellnummer"));
            		  obj_Order.setOrderDate(bd_result.getDate("Bestelldatum"));
            		  obj_Order.setOrderSummary(bd_result.getDouble("Bestellsumme"));
            		  obj_Order.setFirstRate(bd_result.getDouble("Ersterate"));
            		  obj_Order.setRate(bd_result.getDouble("Folgerate"));
            		  obj_Order.setCustNo(bd_result.getString("Kundennummer"));
            		  obj_Order.setRateCount(bd_result.getInt("Ratenanzahl"));
            		  obj_Order.setPayEnd(bd_result.getDate("Zahlungsende"));
            		  obj_Order.setPayStart(bd_result.getDate("Zahlungsstart"));
           close_DB_con("getOneOrder",logger);
        } catch (SQLException ex) {
           logger.error("get One order _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
  return obj_Order;    
 } 

 // List all Customernumbers from Database
 public ArrayList<String> getAll_Cust_Nr(String str_DBname,Logger logger)  { 										     
            
        this.url = "jdbc:sqlite:" + str_DBname;
        
        try {
        	open_DB_con("all Cust Numbers",logger);
            sql_Statement = con.createStatement();
            // Statement for Customernumbers
            ResultSet bd_result = sql_Statement.executeQuery("Select Kundennummer FROM Kunde");               
            logger.debug("Resultset of Statement : " + bd_result); 
                while(bd_result.next()) {
                	  // add all Results to Customernumberlist
                	  lst_Cust_Nr.add(bd_result.getString("Kundennummer"));
                }
                logger.debug("List of Customernumbers : " + lst_Cust_Nr);
        close_DB_con("all Cust Numbers",logger);
        } catch (SQLException ex) {
           logger.error("all Cust Nr _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
 return lst_Cust_Nr ;    
 }
 //Delete one Order 
 public boolean deleteOrder(String str_DBname,String str_cust_Nr, String str_Order_Nr,Logger logger) {					
        
    this.url = "jdbc:sqlite:" + str_DBname;
    try {
    	    open_DB_con("delete Order",logger);
            sql_Statement = con.createStatement();
            // Sql Statement for Delete one order
            String sql ="Delete from BESTELLUNGEN WHERE Bestellnummer =" + str_Order_Nr +" AND Kundennummer=" + str_cust_Nr;
            logger.debug("Statement to Execute / Delete one Order : " + sql);
            sql_Statement.executeUpdate(sql);
            close_DB_con("bestellungLoeschen",logger);
            logger.info("com.customermanagement.database.SQL_Statements - Order deleted " + str_Order_Nr);
        } catch (SQLException ex) {
        	
            logger.error("com.customermanagement.database.SQL_Statements - delete Order : " + ex.getLocalizedMessage());
        }
    	db_Reached = true;
    return db_Reached;
 }
 // Update the selected Order
 public boolean updateOrder(Obj_Order objOrder, String str_DBname,Logger logger){										
     
        this.url = "jdbc:sqlite:" + str_DBname;
        
        try {
        	java.util.Date parsed = objOrder.getOrderDate();
            java.sql.Date orderDate = new java.sql.Date(parsed.getTime());
         
            java.util.Date payStartParsed = objOrder.getPayStart();
            java.sql.Date payStart = new java.sql.Date(payStartParsed.getTime());
                           
            java.util.Date payEndParsed = objOrder.getPayStart();
            java.sql.Date payEnd = new java.sql.Date(payEndParsed.getTime());
           
            logger.debug("com.customermanagment.database - SQL_Statements - updateOrder\n" 
            			 + "Orderdate : " + orderDate + "\nPayment start : " 
            		     + payStart + "\nPayment ends : " 
            			 + payEnd + "\n\n");
            
            open_DB_con("change Order",logger);
            sql_Statement = con.createStatement();

            sql = "UPDATE Bestellungen SET Ratenanzahl = ?,Bestelldatum = ?,"  + 
                  " Zahlungsstart = ?, Zahlungsende = ?, Ersterate = ?, Folgerate = ?,Bestellsumme = ?" + 
                  " WHERE Bestellnummer = " + objOrder.getOrderNo() + " AND Kundennummer = " + objOrder.getCustNo();
            
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setInt(1, objOrder.getRateCount());
                              statement.setDate(2, orderDate);
                              statement.setDate(3, payStart);
                              statement.setDate(4, payEnd);
                              statement.setDouble(5, objOrder.getFirstRate());
                              statement.setDouble(6, objOrder.getRate());
                              statement.setDouble(7, objOrder.getOrderSummary());
                              
            logger.debug("com.customermanagment.database - SQL_Statements - updateOrder" + 
            			 "Update Statement : " + statement);
            statement.executeUpdate();
            statement.close();
         
            logger.info("com.customermanagment.database - SQL_Statements - updateOrder" + 
            			"\nSQL Statement executed : => " + sql);
            close_DB_con("change Order",logger);
                        
            db_Reached = true;
        } catch (SQLException ex) {
            logger.error("com.customermanagment.database - SQL_Statements - updateOrder" + ex.getLocalizedMessage());
            db_Reached = false;
        }
return db_Reached;   
}
 
 // returns a List with all Order Objects from one Customer
 public ObservableList<Obj_Order> getAllOrderObjects(Obj_Customer obj_Customer, String strCustNo,String str_DBname, Logger logger)  {        
     
     this.url = "jdbc:sqlite:" + str_DBname;
     ObservableList<Obj_Order> orders = FXCollections.observableArrayList();
           
 try {
 	 open_DB_con("Orders_From_Customer",logger);
     sql_Statement = con.createStatement();
     
     System.out.println(" Übergebene Kundennummer : " + strCustNo);
     
    // Original ResultSet result = sql_Statement.executeQuery("Select * FROM Bestellungen WHERE Kundennummer =" + strCustNo);
    // ResultSet result = sql_Statement.executeQuery("Select * FROM BESTELLUNGEN WHERE Kundennummer =11111.0");
     ResultSet result = sql_Statement.executeQuery("Select * FROM BESTELLUNGEN");
    // ResultSet result = sql_Statement.executeQuery("Select * FROM Bestellungen WHERE Bestellnummer =12345" ); 
     
     logger.debug("Result Set from Orders : " + result.getFetchSize());
     
     while(result.next()) {
    	 
     	Obj_Order order = new Obj_Order("Bestellnummer",null,null,null,
    								     0,0.0,0.0,0.0,""); 
  	    order.setOrderNo(result.getString("Bestellnummer")); 
    	order.setOrderDate(result.getDate("Bestelldatum"));
    	order.setPayStart(result.getDate("Zahlungsstart")); 
    	order.setPayEnd(result.getDate("Zahlungsende"));
    	order.setRateCount(result.getInt("Ratenanzahl")); 
    	order.setFirstRate(result.getDouble("Ersterate"));
    	order.setRate(result.getDouble("Folgerate")); 
    	order.setOrderSummary(result.getDouble("Bestellsumme"));
    	order.setCustNo(result.getString("Kundennummer"));
    	 
    	orders.add(order);
     }
     obj_Customer.setLstAllOrders(orders);
     logger.debug("founded Order Objects : " + orders.size() );
          
     close_DB_con("Orders_From_Customer",logger);
          
 } catch (SQLException ex) {
     logger.error("getAllOrders _ SQL Error : " + ex.getLocalizedMessage());
 }
 logger.info("");
 
 System.out.println("SQL Statement - Anzahl order Objekte : " + orders.size() );
return orders;    
} 
 
 
 
 
 // Close DB Connection
 public void close_DB_con(String str_Who,Logger logger)  {
        try {
        	con.commit();
            con.close();
        } catch (SQLException ex) {
            logger.error("DB Close Con - SQL FEHLER : " + "Method Name : "  + str_Who + "\n" + ex.getLocalizedMessage() + "\n" + ex);
        }
        logger.debug("DB Con closed " + str_Who);
 }
 // Open DB Connection
 public void open_DB_con(String str_Who,Logger logger){
       
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            con.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException ex) {
                logger.error("DB Open Con _ SQL FEHLER : " + ex.getLocalizedMessage());                
        }
        logger.debug("DB Con open " + str_Who);
  } 

}
