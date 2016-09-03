import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.log4j.Logger;
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
    private static final Logger logger = AnwendungsLogger.getInstance();
    
  public void build_Database(String str_DBname)  {   					// create empty Database
      
        this.url = "jdbc:sqlite:" + str_DBname;     // datebase name durch aktuellen pfad ersetzen
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
                                 
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
        
          Save_Database_Information save_Used_DBInfo = new Save_Database_Information();				// Save used DB
          			 				save_Used_DBInfo.save_Database_Info(str_DBname,logger);
          close_DB_con("build_Database"); 
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("Build Database" + ex.getLocalizedMessage());
       }
    }
  
  public boolean DatabaseConnection (String str_DBname) {
        
      this.url = "jdbc:sqlite:" + str_DBname;  // datebase name durch aktuellen pfad ersetzen
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
      close_DB_con("DatabaseConnection");
    logger.info("Datenverbindung erfolgreich");
   return result;   
  }  

 public boolean new_Customer(Obj_Customer obj_Customer,String str_DBname) {				// Create new Customer and save to DB
          
        this.url = "jdbc:sqlite:" + str_DBname;
            
        try {
            open_DB_con("new_Customer");
            sql_Statement = con.createStatement();
              
            sql = "INSERT INTO KUNDE(Kundennummer,Name,Vorname,Strasse,Hausnummer,Postleitzahl,Ort)" +
                  "VALUES (?,?,?,?,?,?,?)"; 
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setString(1, obj_Customer.getCustNr());
                              statement.setString(2, obj_Customer.getLastname());
                              statement.setString(3, obj_Customer.getCustName());
                              statement.setString(4, obj_Customer.getCustStreet());
                              statement.setInt(5, obj_Customer.getCustHnr());
                              statement.setInt(6, obj_Customer.getCustPc());
                              statement.setString(7, obj_Customer.getCustRes());
                              
          logger.info("Customer created");
            
          statement.executeUpdate();
          statement.close();
          close_DB_con("new_Customer"); 
          
          db_Reached = true;
        } catch (SQLException ex) {
               logger.error("new Customer : " + ex.getLocalizedMessage());
               db_Reached = false;
        }
 return db_Reached;    
 }
  
 public boolean new_Order(Obj_Order obj_Order, String str_DBname){					// Create new Order and save to DB
     
        this.url = "jdbc:sqlite:" + str_DBname;
        
        try {
            java.util.Date parsed = date_Formatter.parse(obj_Order.getOrderDate());
            java.sql.Date bestellDate = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayStart());
            java.sql.Date zahlStartDate = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayEnd());
            java.sql.Date zahlEndeDate = new java.sql.Date(parsed.getTime());
            logger.info("Bestelldatum : " + bestellDate + " Zahlungsstart : " + zahlStartDate + " Zahlungsende : " + zahlEndeDate + "\n\n");
            open_DB_con("new_Order");
            sql_Statement = con.createStatement();
                     
            sql = "INSERT INTO BESTELLUNGEN(Bestellnummer,Bestelldatum,Zahlungsstart,Zahlungsende,Ratenanzahl,Ersterate,Folgerate,Bestellsumme,Kundennummer)" +
                  "VALUES (?,?,?,?,?,?,?,?,?)"; // Insert Befehl fuer neue Bestellung
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setString(1, obj_Order.getOrderNr());
                              statement.setDate(2, bestellDate);
                              statement.setDate(3, zahlStartDate);
                              statement.setDate(4, zahlEndeDate);
                              statement.setInt(5, obj_Order.getRateCount());
                              statement.setDouble(6, obj_Order.getFirstRate());
                              statement.setDouble(7, obj_Order.getRate());
                              statement.setDouble(8, obj_Order.getOrderSummary());
                              statement.setDouble(9, obj_Order.getCustNr());
            
          statement.executeUpdate();
          statement.close();
          close_DB_con("new_Order");
                        
          db_Reached = true;
        } catch (SQLException ex) {
            logger.error("neueBestellung _ SQL Fehler : " + ex.getLocalizedMessage());
            db_Reached = false;
        } catch (ParseException ex) {
            logger.error("neueBestellung _ Parser Fehler : " + ex.getLocalizedMessage());
        }
return db_Reached;   
}
 
public boolean doubled_Customer_Check(Obj_Customer obj_Customer, String str_cust_Nr,String str_DBname,Logger logger) {
   boolean customer_Doubled = false;  
         Obj_Customer obj_doub_Cust = new Obj_Customer();
         obj_doub_Cust = this.getCustomer_AND_Orders(obj_Customer, str_cust_Nr, str_DBname,logger);
    
     if( obj_doub_Cust.getCustNr() != null) {
    	    customer_Doubled = true;
        } else {
        	customer_Doubled = false;
        }
 return customer_Doubled;
 }
 
 public Obj_Customer getCustomer_AND_Orders(Obj_Customer obj_Customer, String str_cust_Nr,String str_DBname, Logger logger)  {        // All Orders from one Customer
         
            this.url = "jdbc:sqlite:" + str_DBname;
            ObservableList<String> lst_Orders = FXCollections.observableArrayList();
            double cust_Total = 0.0;
             
        try {
        	open_DB_con("Orders_From_Customer");
            sql_Statement = con.createStatement();
       
            ResultSet result = sql_Statement.executeQuery("Select * FROM KUNDE WHERE Kundennummer =" + str_cust_Nr);
                  
            obj_Customer.setCustNr(result.getString("Kundennummer")); 
            obj_Customer.setLastname(result.getString("Name"));
            obj_Customer.setCustName(result.getString("Vorname"));
            obj_Customer.setCustStreet(result.getString("Strasse"));
            obj_Customer.setCustHnr(result.getInt("Hausnummer"));
            obj_Customer.setCustPc(result.getInt("Postleitzahl"));
            obj_Customer.setCustRes(result.getString("Ort"));
            
            result = sql_Statement.executeQuery("Select Bestellnummer FROM Bestellungen WHERE Kundennummer =" + str_cust_Nr);
            
            while(result.next()) {
            	lst_Orders.add(result.getString("Bestellnummer"));
            }
            
            obj_Customer.setBestellNummern(lst_Orders);
            close_DB_con("Orders_From_Customer"); 
            getRestSumme(str_cust_Nr,str_DBname);   
            cust_Total = getTotal_Sales(str_cust_Nr,str_DBname);
            
            obj_Customer.setCustTotal(cust_Total);
             
        } catch (SQLException ex) {
            logger.error("get Data _ SQL Error : " + ex.getLocalizedMessage());
        }
 return obj_Customer;    
 } 
 
public boolean delete_Customer(String str_DBname,String str_cust_Nr){				// Delete one Customer
        this.url = "jdbc:sqlite:" + str_DBname;
    try {
    	    open_DB_con("delete_Customer");
            sql_Statement = con.createStatement();
            String sql ="Delete from Kunde WHERE Kundennummer=" + str_cust_Nr;
            sql_Statement.executeUpdate(sql);
            close_DB_con("delete_Customer");
            logger.info("Delete Customer success");
        } catch (SQLException ex) {
            logger.error("Delete Customer _ SQL Error : " + ex.getLocalizedMessage());
        }
    	db_Reached = true;
    return db_Reached;
 }
  
 public Double getTotal_Sales(String str_cust_Nr,String str_DBname) {							            // Calculate Total Sales for one Customer
    double total_Sales = 0.0;
   
    this.url = "jdbc:sqlite:" + str_DBname;
    try {
    		open_DB_con("cust_Total");
            sql_Statement = con.createStatement();
            ResultSet result = sql_Statement.executeQuery("Select Bestellsumme FROM Bestellungen WHERE Kundennummer =" + str_cust_Nr);
                       
            while(result.next()) {
                  total_Sales = total_Sales + result.getDouble("Bestellsumme");
            }
        } catch (SQLException ex) {
            logger.error("get Total Sales _ SQL Error : " + ex.getLocalizedMessage());
        }
        close_DB_con("cust_Total");
   return total_Sales;  
 }
 
 
 public Double getRestSumme(String kdNr,String databaseName) {
     double restSumme = 0.0;
     Date startDatum = null;
     
     this.url = "jdbc:sqlite:" + databaseName;
     try {
    	 	open_DB_con("getRestSumme");
            sql_Statement = con.createStatement();
            ResultSet result = sql_Statement.executeQuery("Select Zahlungsstart FROM Bestellungen WHERE Kundennummer =" + kdNr);
                       
            while(result.next()) {
                  startDatum = result.getDate("Zahlungsstart");
            }
        } catch (SQLException ex) {
            logger.error("get RestSumme _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
        close_DB_con("getRestSumme");
    return restSumme;
 }
 
 public Obj_Order getOne_Order(Obj_Order obj_Order, String str_Order_Nr, String str_cust_Nr,String str_DBname)  {     					// Statement for One Order
        
        this.url = "jdbc:sqlite:" + str_DBname;
        try {
        	open_DB_con("getOne_Order");
            sql_Statement = con.createStatement();
            ResultSet bd_result = sql_Statement.executeQuery("Select * FROM Bestellungen WHERE Bestellnummer =" + str_Order_Nr );       //+ "AND Kundennummer=" + kdNr);
            		  obj_Order.setOrderNr(bd_result.getString("Bestellnummer"));
            		  obj_Order.setOrderDate(date_Formatter.format(bd_result.getDate("Bestelldatum")));
            		 obj_Order.setOrderSummary(bd_result.getDouble("Bestellsumme"));
            		 obj_Order.setFirstRate(bd_result.getDouble("Ersterate"));
            		 obj_Order.setRate(bd_result.getDouble("Folgerate"));
            		 obj_Order.setCustNr(bd_result.getDouble("Kundennummer"));
            		 obj_Order.setRateCount(bd_result.getInt("Ratenanzahl"));
            		 obj_Order.setPayEnd(date_Formatter.format(bd_result.getDate("Zahlungsende")));
            		 obj_Order.setPayStart(date_Formatter.format(bd_result.getDate("Zahlungsstart")));
           close_DB_con("getOneOrder");
        } catch (SQLException ex) {
           logger.error("get One order _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
 return obj_Order;    
 } 
 
  public ArrayList<String> getAll_Cust_Nr(String str_DBname)  { 										 // List all Customernumbers from Database
            
            this.url = "jdbc:sqlite:" + str_DBname;
        try {
        	open_DB_con("all Cust Numbers");
            sql_Statement = con.createStatement();
            ResultSet bd_result = sql_Statement.executeQuery("Select Kundennummer FROM Kunde");               //+ "AND Kundennummer=" + kdNr);
            
                while(bd_result.next()) {   
                	  lst_Cust_Nr.add(bd_result.getString("Kundennummer"));
                }
        close_DB_con("all Cust Numbers");
        } catch (SQLException ex) {
           logger.error("all Cust Nr _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
 return lst_Cust_Nr ;    
 }
  
 public boolean delete_Order(String str_DBname,String str_cust_Nr, String str_Order_Nr) {					// Delete one Order
        
    this.url = "jdbc:sqlite:" + str_DBname;
    try {
    	    open_DB_con("delete Order");
            sql_Statement = con.createStatement();
            String sql ="Delete from BESTELLUNGEN WHERE Bestellnummer =" + str_Order_Nr +" AND Kundennummer=" + str_cust_Nr;
            sql_Statement.executeUpdate(sql);
            close_DB_con("bestellungLoeschen");
            logger.info("Deleteorder successful");
        } catch (SQLException ex) {
            logger.error("delete Order _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
    	db_Reached = true;
    return db_Reached;
 }

  public boolean change_Order(Obj_Order obj_Order, String str_DBname){										// Update the selected Order
     
        this.url = "jdbc:sqlite:" + str_DBname;
        
        try {
            java.util.Date parsed = date_Formatter.parse(obj_Order.getOrderDate());
            java.sql.Date bestellDate = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayStart());
            java.sql.Date zahlStartDate = new java.sql.Date(parsed.getTime());
            parsed = date_Formatter.parse(obj_Order.getPayEnd());
            java.sql.Date zahlEndeDate = new java.sql.Date(parsed.getTime());
            
            logger.info("Bestelldatum : " + bestellDate + " Zahlungsstart : " + zahlStartDate + " Zahlungsende : " + zahlEndeDate + "\n\n");
            
            open_DB_con("change Order");
            sql_Statement = con.createStatement();

            sql = "UPDATE Bestellungen SET Ratenanzahl = ?,Bestelldatum = ?,"  + 
                  " Zahlungsstart = ?, Zahlungsende = ?, Ersterate = ?, Folgerate = ?,Bestellsumme = ?" + 
                  " WHERE Bestellnummer = " + obj_Order.getOrderNr() + " AND Kundennummer = " + obj_Order.getCustNr();
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setInt(1, obj_Order.getRateCount());
                              statement.setDate(2, bestellDate);
                              statement.setDate(3, zahlStartDate);
                              statement.setDate(4, zahlEndeDate);
                              statement.setDouble(5, obj_Order.getFirstRate());
                              statement.setDouble(6, obj_Order.getRate());
                              statement.setDouble(7, obj_Order.getOrderSummary());
                              
            
            statement.executeUpdate();
            statement.close();
         
            logger.info("sql statement executen : => " + sql);
            close_DB_con("change Order");
                        
            db_Reached = true;
        } catch (SQLException ex) {
            logger.error("bestellungAendern _ SQL Fehler : " + ex.getLocalizedMessage());
            db_Reached = false;
        } catch (ParseException ex) {
            logger.error("bestellungAendern _ Parser Fehler : " + ex.getLocalizedMessage());
        }
return db_Reached;   
}
 
 public void close_DB_con(String str_Who)  {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            logger.error("DB Close Con _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
        logger.info("DB Con closed " + str_Who);
 }
 
 public void open_DB_con(String str_Who){
       
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            con.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException ex) {
                logger.error("DB Open Con _ SQL FEHLER : " + ex.getLocalizedMessage());                
        }
        logger.info("DB Con open " + str_Who);
  } 
	
	
	
}
