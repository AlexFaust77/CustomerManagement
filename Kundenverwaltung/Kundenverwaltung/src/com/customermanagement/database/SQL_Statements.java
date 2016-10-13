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
                  "(Kundennummer   CHAR(30)  PRIMARY KEY NOT NULL," +
                  " Name           CHAR(30)              NOT NULL," +
                  " Vorname        CHAR(30)              NOT NULL," +
                  " Strasse        CHAR(30),                      " +
                  " Hausnummer     INT,                           " +
                  " Postleitzahl   INT,                           " +   
                  " Ort            CHAR(30)                       )";
                        
           sql_Statement.executeUpdate(sql);
           logger.info("Customer Table created");
           logger.debug("Customer Table : " + sql );
           
           sql = "CREATE TABLE BESTELLUNGEN" +                          // Order Table
                 "(Bestellnummer  CHAR(30) PRIMARY KEY  NOT NULL," +
                 " Bestelldatum   DATE                  NOT NULL," + 
                 " Zahlungsstart  DATE                  NOT NULL," + 
                 " Zahlungsende   DATE                  NOT NULL," + 
                 " Ratenanzahl    INT                   NOT NULL," + 
                 " Ersterate      DOUBLE                NOT NULL," + 
                 " Folgerate      DOUBLE                NOT NULL," + 
                 " Bestellsumme   DOUBLE                NOT NULL," +
                 " Kundennummer   DOUBLE                NOT NULL," +
                 " FOREIGN KEY(Kundennummer) REFERENCES KUNDE(Kundennummer))"; 
           
          sql_Statement.executeUpdate(sql); 
          logger.info("Order Table created");
          logger.debug("Order Table : " + sql );      
         
        
          Save_Database_Information save_Used_DBInfo = new Save_Database_Information();				// Save used DB
          			 				save_Used_DBInfo.save_Database_Info(str_DBname,logger);
          close_DB_con("build_Database",logger); 
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("Build Database" + ex.getLocalizedMessage());
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
 public boolean new_Order(Obj_Order obj_Order, String str_DBname,Logger logger){					
     
        this.url = "jdbc:sqlite:" + str_DBname;
        
        try {
        	System.out.println("Not Parsed Dates :  == > Order Date : " + obj_Order.getOrderDate() + " Payment starts : " + obj_Order.getPayStart() + " Payment Ends : " + obj_Order.getPayEnd()  + "\n\n");
            java.util.Date parsed = date_Formatter.parse(obj_Order.getOrderDate());
            java.sql.Date orderDate = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayStart());
            java.sql.Date payStart = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayEnd());
            java.sql.Date payEnd = new java.sql.Date(parsed.getTime());
            
            System.out.println("Parsed Dates :  == > Order Date : " + orderDate + " Payment starts : " + payStart + " Payment Ends : " + payEnd + "\n\n");
            logger.debug("Parsed Dates :  == > Order Date : " + orderDate + " Payment starts : " + payStart + " Payment Ends : " + payEnd + "\n\n");
            open_DB_con("new_Order",logger);
            sql_Statement = con.createStatement();
            
            // Insert Into for new Order
            sql = "INSERT INTO BESTELLUNGEN(Bestellnummer,Bestelldatum,Zahlungsstart,Zahlungsende,Ratenanzahl,Ersterate,Folgerate,Bestellsumme,Kundennummer)" +
                  "VALUES (?,?,?,?,?,?,?,?,?)"; 
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setString(1, obj_Order.getOrderNo());
                              statement.setDate(2, orderDate);
                              statement.setDate(3, payStart);
                              statement.setDate(4, payEnd);
                              statement.setInt(5, obj_Order.getRateCount());
                              statement.setDouble(6, obj_Order.getFirstRate());
                              statement.setDouble(7, obj_Order.getRate());
                              statement.setDouble(8, obj_Order.getOrderSummary());
                              statement.setDouble(9, obj_Order.getCustNo());
            
          statement.executeUpdate();
          statement.close();
          close_DB_con("new_Order",logger);
          logger.debug("Executed Statement : New Order : " + statement );              
          db_Reached = true;
        } catch (SQLException ex) {
        	System.out.println("Sql exception : " + ex.getLocalizedMessage());
            logger.error("New Order _ SQL Error : " + ex.getLocalizedMessage());
            db_Reached = false;
        } catch (ParseException ex) {
        	System.out.println("Parse exception : " + ex.getLocalizedMessage());
            logger.error("New Order _ Parse Error : " + ex.getLocalizedMessage());
        }
return db_Reached;   
}
// Check for doubled Customers 
public boolean doubled_Customer_Check(Obj_Customer obj_Customer, String str_cust_Nr,String str_DBname,Logger logger) {
   boolean customer_Doubled = false;  
         Obj_Customer obj_doub_Cust = new Obj_Customer();
         obj_doub_Cust = this.getCustomer_AND_Orders(obj_Customer, str_cust_Nr, str_DBname,logger);
    
     if( obj_doub_Cust.getCustNo() != null) {
    	    customer_Doubled = true;
        } else {
        	customer_Doubled = false;
        }
 return customer_Doubled;
 }
 //All Orders from one Customer
 public Obj_Customer getCustomer_AND_Orders(Obj_Customer obj_Customer, String str_cust_Nr,String str_DBname, Logger logger)  {        
         
            this.url = "jdbc:sqlite:" + str_DBname;
            ObservableList<String> lst_Orders = FXCollections.observableArrayList();
            double cust_Total = 0.0;
             
        try {
        	open_DB_con("Orders_From_Customer",logger);
            sql_Statement = con.createStatement();
            // Request for Customer with customernumber
            ResultSet result = sql_Statement.executeQuery("Select * FROM KUNDE WHERE Kundennummer =" + str_cust_Nr);
                  
            obj_Customer.setCustNo(result.getString("Kundennummer")); 
            obj_Customer.setLastname(result.getString("Name"));
            obj_Customer.setFirstname(result.getString("Vorname"));
            obj_Customer.setStreet(result.getString("Strasse"));
            obj_Customer.setHouseNo(result.getInt("Hausnummer"));
            obj_Customer.setPostcode(result.getInt("Postleitzahl"));
            obj_Customer.setResidenz(result.getString("Ort"));
            // Request for all Orders from this Customer
            result = sql_Statement.executeQuery("Select Bestellnummer FROM Bestellungen WHERE Kundennummer =" + str_cust_Nr);
            logger.debug("Result Set from Orders : " + result);
            
            // Fill Orderlist with search results
            while(result.next()) {
            	lst_Orders.add(result.getString("Bestellnummer"));
            }
            logger.debug("Orderlist : " + lst_Orders);
            obj_Customer.setOrderlist(lst_Orders);
            
            close_DB_con("Orders_From_Customer",logger);
            // Summary to Pay
            getRestSumme(str_cust_Nr,str_DBname,logger);   
            
            // Summary - Total Sales for customer
            cust_Total = getTotal_Sales(str_cust_Nr,str_DBname,logger);
            logger.debug("Customer Total Sales : " + str_cust_Nr + " Database Name " + str_DBname + "\nValue : " + cust_Total );
            obj_Customer.setCustTotal(cust_Total);
             
        } catch (SQLException ex) {
            logger.error("get Data _ SQL Error : " + ex.getLocalizedMessage());
        }
        logger.info("");
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
 // Database request for one Order
 public Obj_Order getOne_Order(Obj_Order obj_Order, String str_Order_Nr, String str_cust_Nr,String str_DBname,Logger logger)  {     					
        
        this.url = "jdbc:sqlite:" + str_DBname;
        try {
        	open_DB_con("getOne_Order",logger);
            sql_Statement = con.createStatement();
            // Statement for One Order
            ResultSet bd_result = sql_Statement.executeQuery("Select * FROM Bestellungen WHERE Bestellnummer =" + str_Order_Nr );      
            logger.debug("SQL Statement Result : " + bd_result);
            		  obj_Order.setOrderNo(bd_result.getString("Bestellnummer"));
            		  obj_Order.setOrderDate(date_Formatter.format(bd_result.getDate("Bestelldatum")));
            		  obj_Order.setOrderSummary(bd_result.getDouble("Bestellsumme"));
            		  obj_Order.setFirstRate(bd_result.getDouble("Ersterate"));
            		  obj_Order.setRate(bd_result.getDouble("Folgerate"));
            		  obj_Order.setCustNo(bd_result.getDouble("Kundennummer"));
            		  obj_Order.setRateCount(bd_result.getInt("Ratenanzahl"));
            		  obj_Order.setPayEnd(date_Formatter.format(bd_result.getDate("Zahlungsende")));
            		  obj_Order.setPayStart(date_Formatter.format(bd_result.getDate("Zahlungsstart")));
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
 public boolean delete_Order(String str_DBname,String str_cust_Nr, String str_Order_Nr,Logger logger) {					
        
    this.url = "jdbc:sqlite:" + str_DBname;
    try {
    	    open_DB_con("delete Order",logger);
            sql_Statement = con.createStatement();
            // Sql Statement for Delete one order
            String sql ="Delete from BESTELLUNGEN WHERE Bestellnummer =" + str_Order_Nr +" AND Kundennummer=" + str_cust_Nr;
            logger.debug("Statement to Execute / Delete one Order : " + sql);
            sql_Statement.executeUpdate(sql);
            close_DB_con("bestellungLoeschen",logger);
            logger.info("Deleteorder successful");
        } catch (SQLException ex) {
            logger.error("delete Order _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
    	db_Reached = true;
    return db_Reached;
 }
 // Update the selected Order
 public boolean change_Order(Obj_Order obj_Order, String str_DBname,Logger logger){										
     
        this.url = "jdbc:sqlite:" + str_DBname;
        
        try {
            java.util.Date parsed = date_Formatter.parse(obj_Order.getOrderDate());
            java.sql.Date orderDate = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayStart());
            java.sql.Date payStart = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayEnd());
            java.sql.Date payEnd = new java.sql.Date(parsed.getTime());
            
            logger.debug("\nOrderdate : " + orderDate + "\nPayment start : " + payStart + "\nPayment ends : " + payEnd + "\n\n");
            
            open_DB_con("change Order",logger);
            sql_Statement = con.createStatement();

            sql = "UPDATE Bestellungen SET Ratenanzahl = ?,Bestelldatum = ?,"  + 
                  " Zahlungsstart = ?, Zahlungsende = ?, Ersterate = ?, Folgerate = ?,Bestellsumme = ?" + 
                  " WHERE Bestellnummer = " + obj_Order.getOrderNo() + " AND Kundennummer = " + obj_Order.getCustNo();
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setInt(1, obj_Order.getRateCount());
                              statement.setDate(2, orderDate);
                              statement.setDate(3, payStart);
                              statement.setDate(4, payEnd);
                              statement.setDouble(5, obj_Order.getFirstRate());
                              statement.setDouble(6, obj_Order.getRate());
                              statement.setDouble(7, obj_Order.getOrderSummary());
                              
            logger.debug("Update Statement : " + statement);
            statement.executeUpdate();
            statement.close();
         
            logger.info("sql statement executen : => " + sql);
            close_DB_con("change Order",logger);
                        
            db_Reached = true;
        } catch (SQLException ex) {
            logger.error("bestellungAendern _ SQL Fehler : " + ex.getLocalizedMessage());
            db_Reached = false;
        } catch (ParseException ex) {
            logger.error("bestellungAendern _ Parser Fehler : " + ex.getLocalizedMessage());
        }
return db_Reached;   
}
 // Close DB Connection
 public void close_DB_con(String str_Who,Logger logger)  {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            logger.error("DB Close Con _ SQL FEHLER : " + ex.getLocalizedMessage());
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
