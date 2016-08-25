import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQL_Statements {

	
    private boolean result = false;
    private boolean db_Reached;
    private final SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
    private ArrayList<String>kundennummern = new ArrayList<String>();    
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
                                 
            sqlStatement = con.createStatement();
           
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
        
          dbSpeicher save_Used_DBInfo = new dbSpeicher();				// Save used DB
          			 save_Used_DBInfo.dbInfoSpeichern(str_DBname);
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
            sqlStatement = con.createStatement();
              
            sql = "INSERT INTO KUNDE(Kundennummer,Name,Vorname,Strasse,Hausnummer,Postleitzahl,Ort)" +
                  "VALUES (?,?,?,?,?,?,?)"; 
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setString(1, obj_Customer.getkdNummer());
                              statement.setString(2, obj_Customer.getName());
                              statement.setString(3, obj_Customer.getVorname());
                              statement.setString(4, obj_Customer.getStrasse());
                              statement.setInt(5, obj_Customer.getHausnr());
                              statement.setInt(6, obj_Customer.getPlz());
                              statement.setString(7, obj_Customer.getOrt());
                              
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
            java.util.Date parsed = df.parse(obj_Order.getBestelldatum());
            java.sql.Date bestellDate = new java.sql.Date(parsed.getTime());
            parsed = df.parse(obj_Order.getZahlungsstart());
            java.sql.Date zahlStartDate = new java.sql.Date(parsed.getTime());
            parsed = df.parse(obj_Order.getZahlungsende());
            java.sql.Date zahlEndeDate = new java.sql.Date(parsed.getTime());
            logger.info("Bestelldatum : " + bestellDate + " Zahlungsstart : " + zahlStartDate + " Zahlungsende : " + zahlEndeDate + "\n\n");
            open_DB_con("new_Order");
            sqlStatement = con.createStatement();
                     
            sql = "INSERT INTO BESTELLUNGEN(Bestellnummer,Bestelldatum,Zahlungsstart,Zahlungsende,Ratenanzahl,Ersterate,Folgerate,Bestellsumme,Kundennummer)" +
                  "VALUES (?,?,?,?,?,?,?,?,?)"; // Insert Befehl fuer neue Bestellung
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setString(1, obj_Order.getBestellnummer());
                              statement.setDate(2, bestellDate);
                              statement.setDate(3, zahlStartDate);
                              statement.setDate(4, zahlEndeDate);
                              statement.setInt(5, obj_Order.getRatenanzahl());
                              statement.setDouble(6, obj_Order.getErsteRate());
                              statement.setDouble(7, obj_Order.getFolgeRate());
                              statement.setDouble(8, obj_Order.getBestellsumme());
                              statement.setDouble(9, obj_Order.getKundennummer());
            
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
 
 public boolean doppelteKundennr(Kunde objDaten, String kdNr,String databaseName) {
   boolean doppelterKunde = false;  
   Kunde obj_dop_Kunde = new Kunde();
         obj_dop_Kunde = this.getData(objDaten, kdNr, databaseName);
    
     if(obj_dop_Kunde.getkdNummer() != null) {
             doppelterKunde = true;
        } else {
             doppelterKunde = false;
        }
 return doppelterKunde;
 }
 
 public Obj_Customer getOrders_From_Customer(Obj_Customer obj_Customer, String str_cust_Nr,String str_DBname)  {        // All Orders from one Customer
         
            this.url = "jdbc:sqlite:" + str_DBname;
            ObservableList<String> lst_Orders = FXCollections.observableArrayList();
            double cust_Total = 0.0;
             
        try {
        	open_DB_con("Orders_From_Customer");
            sqlStatement = con.createStatement();
       
            ResultSet result = sqlStatement.executeQuery("Select * FROM KUNDE WHERE Kundennummer =" + str_cust_Nr);
                  
            obj_Customer.setkdNummer(result.getString("Kundennummer")); 
            obj_Customer.setName(result.getString("Name"));
            obj_Customer.setVorname(result.getString("Vorname"));
            obj_Customer.setStrasse(result.getString("Strasse"));
            obj_Customer.setHausnr(result.getInt("Hausnummer"));
            obj_Customer.setPlz(result.getInt("Postleitzahl"));
            obj_Customer.setOrt(result.getString("Ort"));
            
            result = sqlStatement.executeQuery("Select Bestellnummer FROM Bestellungen WHERE Kundennummer =" + str_cust_Nr);
            
            while(result.next()) {
            	obj_Customer.add(result.getString("Bestellnummer"));
            }
            
            obj_Customer.setBestellNummern(lst_Orders);
            close_DB_con("Orders_From_Customer"); 
            getRestSumme(str_cust_Nr,str_DBname);   
            cust_Total = getGesamtUmsatz(str_cust_Nr,str_DBname);
            
            obj_Customer.setGesamtumsatz(cust_Total);
             
        } catch (SQLException ex) {
            logger.error("get Data _ SQL Error : " + ex.getLocalizedMessage());
        }
 return obj_Customer;    
 } 
 
public boolean delete_Customer(String str_DBname,String str_cust_Nr){				// Delete one Customer
        this.url = "jdbc:sqlite:" + str_DBname;
    try {
    	    open_DB_con("delete_Customer");
            sqlStatement = con.createStatement();
            String sql ="Delete from Kunde WHERE Kundennummer=" + str_cust_Nr;
            sqlStatement.executeUpdate(sql);
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
            sqlStatement = con.createStatement();
            ResultSet result = sqlStatement.executeQuery("Select Bestellsumme FROM Bestellungen WHERE Kundennummer =" + str_cust_Nr);
                       
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
            sqlStatement = con.createStatement();
            ResultSet result = sqlStatement.executeQuery("Select Zahlungsstart FROM Bestellungen WHERE Kundennummer =" + kdNr);
                       
            while(result.next()) {
                  startDatum = result.getDate("Zahlungsstart");
            }
        } catch (SQLException ex) {
            logger.error("get RestSumme _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
        close_DB_con("getRestSumme");
    return restSumme;
 }
 
 public Bestellung getOne_Order(Obj_Order order_Data, String str_Order_Nr, String str_cust_Nr,String str_DBname)  {     					// Call for One Order
        
        this.url = "jdbc:sqlite:" + str_DBname;
        try {
            dbVerbindungOeffnen("getOne_Order");
            sqlStatement = con.createStatement();
            ResultSet bd_result = sqlStatement.executeQuery("Select * FROM Bestellungen WHERE Bestellnummer =" + bestellnr );//+ "AND Kundennummer=" + kdNr);
                     BestellDaten.setBestellnummer(bd_result.getString("Bestellnummer"));
                     BestellDaten.setBestelldatum(df.format(bd_result.getDate("Bestelldatum")));
                     BestellDaten.setBestellsumme(bd_result.getDouble("Bestellsumme"));
                     BestellDaten.setErsteRate(bd_result.getDouble("Ersterate"));
                     BestellDaten.setFolgeRate(bd_result.getDouble("Folgerate"));
                     BestellDaten.setKundennummer(bd_result.getDouble("Kundennummer"));
                     BestellDaten.setRatenanzahl(bd_result.getInt("Ratenanzahl"));
                     BestellDaten.setZahlungsende(df.format(bd_result.getDate("Zahlungsende")));
                     BestellDaten.setZahlungsstart(df.format(bd_result.getDate("Zahlungsstart")));
            dbVerbindungSchliessen("getOneOrder");
        } catch (SQLException ex) {
           logger.error("get Bestell Data _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
 return BestellDaten;    
 } 
 
  public ArrayList<String> alleKundennummern(String databaseName)  {
            // Abfrage der Bestellung mithilfe der Bestellnummer zum Uebertrag in die Bestellfelder
            this.url = "jdbc:sqlite:" + databaseName;
        try {
            dbVerbindungOeffnen("alleKundennummern");
            sqlStatement = con.createStatement();
            ResultSet bd_result = sqlStatement.executeQuery("Select Kundennummer FROM Kunde");//+ "AND Kundennummer=" + kdNr);
            
                while(bd_result.next()) {   // alle werte des Resultsets aufnehmen
                      kundennummern.add(bd_result.getString("Kundennummer"));
                     
                }
        dbVerbindungSchliessen("alleKundennummern");
        } catch (SQLException ex) {
           logger.error("get Bestell Data _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
 return kundennummern ;    
 }
  
 public boolean bestellungLoeschen(String databaseName,String kdNr, String bestellNr) {
        
    this.url = "jdbc:sqlite:" + databaseName;
    try {
        dbVerbindungOeffnen("bestellungLoeschen");
            sqlStatement = con.createStatement();
            String sql ="Delete from BESTELLUNGEN WHERE Bestellnummer =" + bestellNr +" AND Kundennummer=" + kdNr;
            sqlStatement.executeUpdate(sql);
        dbVerbindungSchliessen("bestellungLoeschen");
            logger.info("Deleteorder successful");
        } catch (SQLException ex) {
            logger.error("bestellung Loeschen _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
        dbKontakt = true;
    return dbKontakt;
 }

  public boolean bestellungAendern(Bestellung objBestellung, String databaseName){
     
        this.url = "jdbc:sqlite:" + databaseName;
        
        try {
            java.util.Date parsed = df.parse(objBestellung.getBestelldatum());
            java.sql.Date bestellDate = new java.sql.Date(parsed.getTime());
            parsed = df.parse(objBestellung.getZahlungsstart());
            java.sql.Date zahlStartDate = new java.sql.Date(parsed.getTime());
            parsed = df.parse(objBestellung.getZahlungsende());
            java.sql.Date zahlEndeDate = new java.sql.Date(parsed.getTime());
            
            logger.info("Bestelldatum : " + bestellDate + " Zahlungsstart : " + zahlStartDate + " Zahlungsende : " + zahlEndeDate + "\n\n");
            
            dbVerbindungOeffnen("bestellungAendern");
            sqlStatement = con.createStatement();

            sql = "UPDATE Bestellungen SET Ratenanzahl = ?,Bestelldatum = ?,"  + 
                  " Zahlungsstart = ?, Zahlungsende = ?, Ersterate = ?, Folgerate = ?,Bestellsumme = ?" + 
                  " WHERE Bestellnummer = " + objBestellung.getBestellnummer() + " AND Kundennummer = " + objBestellung.getKundennummer();
            PreparedStatement statement = con.prepareStatement(sql);
                              statement.setInt(1, objBestellung.getRatenanzahl());
                              statement.setDate(2, bestellDate);
                              statement.setDate(3, zahlStartDate);
                              statement.setDate(4, zahlEndeDate);
                              statement.setDouble(5, objBestellung.getErsteRate());
                              statement.setDouble(6, objBestellung.getFolgeRate());
                              statement.setDouble(7, objBestellung.getBestellsumme());
                              
            
            statement.executeUpdate();
            statement.close();
         
          logger.info("sql statement executen : => " + sql);
          dbVerbindungSchliessen("bestellungAendern");
                        
          dbKontakt = true;
        } catch (SQLException ex) {
            logger.error("bestellungAendern _ SQL Fehler : " + ex.getLocalizedMessage());
            dbKontakt = false;
        } catch (ParseException ex) {
            logger.error("bestellungAendern _ Parser Fehler : " + ex.getLocalizedMessage());
        }
return dbKontakt;   
}
 
 public void close_DB_con(String str_Who)  {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            logger.error("db Verbindung Schliessen _ SQL FEHLER : " + ex.getLocalizedMessage());
        }
        logger.info("DB Con closed " + str_Who);
 }
 
 public void open_DB_con(String str_Who){
       
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            con.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException ex) {
                logger.error("db Verbindung Oeffnen _ SQL FEHLER : " + ex.getLocalizedMessage());                
        }
        logger.info("DB Con open " + str_Who);
  } 
	
	
	
}
