import java.util.ArrayList;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

public class Button_Listeners {

	 Obj_Customer obj_Customer =new Obj_Customer();						    // vorher objDaten
	 Obj_Order obj_Order = new Obj_Order();	         					    // vorher objBestellung
	 Clear_Data data_cleaner = new Clear_Data();							// vorher datenleeren
	 SQL_Statements dataBase_Request = new SQL_Statements();			    // vorher databasecon
	 
	 private ArrayList<String> month = new ArrayList<String>();			    // vorher monate
	 private ArrayList<String> monthly_Rate = new ArrayList<String>();      // vorher monatlicheRate
	 private ArrayList<Obj_Order> orderlist = new ArrayList<Obj_Order>();   // vorher obj_Bestellliste
	
	 Button_Listeners(Gui_States gui_State,Cust_Gui Obj_Cust_Gui,Logger logger) {
	
		 // 1 ***************** Finished !!!!! ********************************************************************************************************
		 Obj_Cust_Gui.btn_Cust_Search.setOnAction(new EventHandler<ActionEvent>() {                                     								    // Search one Customer with Customernumber
	            @Override
	            public void handle(ActionEvent e) {
	            	  obj_Customer = data_cleaner.cleanObjCustomer(obj_Customer);
	                  				 data_cleaner.cleanLists(month,abrechnungsmonate,monatlicheRaten,orderlist,liste_Obj_Kunde,liste_Kundennummern,monatsliste,monthly_Rate);
	                  obj_Customer = dataBase_Request.getCustomer_AND_Orders(obj_Customer,Obj_Cust_Gui.getCustNr(),Obj_Cust_Gui.getActiveDB(),logger);  	// Database request
	            
	                  Obj_Cust_Gui.setCustLastName(obj_Customer.getLastname());                															    // Filling the values into the Gui Fields
	                  Obj_Cust_Gui.setCustName(obj_Customer.getCustName());
	                  Obj_Cust_Gui.setCustStreet(obj_Customer.getCustStreet());
	                  Obj_Cust_Gui.setCustNr(Integer.toString(obj_Customer.getCustHnr()));
	                  Obj_Cust_Gui.setCustPc(Integer.toString(obj_Customer.getCustPc()));
	                  Obj_Cust_Gui.setCustRes(obj_Customer.getCustRes());
	                  Obj_Cust_Gui.setBestellNummernListe(obj_Customer.getBestellNummern());
	                  Obj_Cust_Gui.setOrderCount(obj_Customer.getOrderCount());
	                  Obj_Cust_Gui.setTotal(Double.toString(obj_Customer.getCustTotal()));
	                              
	                  rechnen.monateBerechnen(Obj_Cust_Gui,dataBase_Request);
	                  month = rechnen.getMonate();
	                  
	                  orderlist = rechnen.listeAllerBestellObjekte(Obj_Cust_Gui, obj_Order,obj_Customer,dataBase_Request);
	                              rechnen.setMonate(month);
	                              rechnen.rateMitHashMap();
	                              
	                  monthly_Rate = rechnen.getMonthlyRate();
	                                           
	               
	                  Chart_fx rate_Chart = new Chart_fx();																									// create chart for monthly Rates
	                           rate_Chart.setMonthlyRate(monthly_Rate);
	                           rate_Chart.setMonthslist(month);
	                           rate_Chart.createChartWithToolTips();
	                  Obj_Cust_Gui.setLineData(rate_Chart.getChartData()); 
	                  
	                  
	                
	                  TableView<Obj_Order> view_fx_Table = new TableView<Obj_Order>();																		//Table for Order Overview
	                  Table_fx table = new Table_fx();
	                           table.setOrderlist(orderlist);
	                           view_fx_Table = table.erstelleFXTableView();
	                           Obj_Cust_Gui.tb_Table.setContent(view_fx_Table);
	            }
	        });
	
		 // 2 ***************** FERTIG !!!!! ********************************************************************************************************
		 Obj_Cust_Gui.btn_neuerKunde.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	                   gui_State.neuenKundenAnlegen(Obj_Cust_Gui);																							// GUI State New Customer
	            }
	        });
	        
	        // 3
		 Obj_Cust_Gui.btn_neueBestellung.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	                // pruefen ob Kunde aktiv und Kundennummer vorhanden
	                if(Obj_Cust_Gui.getKdNr().length() > 0) {
	                    guiPos.neueBestellungen(kdverw_Obj);
	                    Obj_Cust_Gui.setBestellkdnr(kdverw_Obj.getKdNr());
	                } else {
	                    
	                    // msgfenster = Keine KD
	                }
	            }
	        });    
	        // 4
		 Obj_Cust_Gui.btn_kundeLoeschen.setOnAction(new EventHandler<ActionEvent>() {
	            
	            @Override
	            public void handle(ActionEvent e) {
	                Alert nutzerBestaetigung = new Alert(AlertType.CONFIRMATION);
	                      nutzerBestaetigung.setTitle("Kunde wirklich löschen ?");
	                      nutzerBestaetigung.setHeaderText("Kunde wirklich löschen ?\nEs werden alle Datensätze zu diesem Kunden gelöscht !");
	                      nutzerBestaetigung.setContentText("Soll der Kunde-Nr.: " + kdverw_Obj.getKdNr() + "\n wirklich gelöscht werden?" );
	                      Optional<ButtonType> result = nutzerBestaetigung.showAndWait(); 
	                
	                      if(result.get() == ButtonType.OK) {
	                           //löschen
	                           for(int jedeBestellung = 0; jedeBestellung < kdverw_Obj.getBestellNummernListe().size();jedeBestellung++) {
	                               String aktuelleBest = obj_Bestellliste.get(jedeBestellung).getBestellnummer();
	                               dBKontakt = dataBaseCon.bestellungLoeschen(kdverw_Obj.getAktiveDB(), kdverw_Obj.getKdNr(), aktuelleBest);
	                           }
	                           
	                           dBKontakt = dataBaseCon.kundeLoeschen(kdverw_Obj.getAktiveDB(), kdverw_Obj.getKdNr());
	                           
	                           System.out.println("Löschen erfolgreich ? " + dBKontakt);
	                           guiPos.startZustand(kdverw_Obj);
	                       } else {
	                           // nichts tun
	                       }
	            }
	        });
	        
	        
	            kdverw_Obj.btn_DatenbankAus.setOnAction(new EventHandler<ActionEvent>() {
	            
	            @Override
	            public void handle(ActionEvent e) {
	                                          
	                               chooseDB.setTitle("Bitte Datenbank auswählen !");
	                                               
	                               File datenbankAusw = chooseDB.showOpenDialog(primaryStage);
	                                    if(datenbankAusw != null) {
	                                                                
	                                                result = dataBaseCon.DatabaseConnection(datenbankAusw.getAbsolutePath());
	                                        
	                                                if(result) {
	                                                   kdverw_Obj.setDatenbankAus(datenbankAusw.getAbsolutePath());
	                                                   kdverw_Obj.setGoodResult();
	                                                }  else {
	                                                   kdverw_Obj.setDatenbankAus("Verbindungsaufbau fehlgeschlagen");
	                                                   kdverw_Obj.setBadResult();
	                                                }       
	                                                         
	                                        System.out.println(result + " ... " + datenbankAusw.getAbsolutePath());
	                                                                               
	                                    }
	            }
	        });
	        
	        // 6 ***************** FERTIG !!!!! ********************************************************************************************************
	        kdverw_Obj.btn_neueDatenbank.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	                chooseDB.setTitle("Neue Datenbank erstellen !");
	             
	                File datenbankName = chooseDB.showSaveDialog(primaryStage);
	                     if(datenbankName != null) {
	                         dataBaseCon.buildTheDatabase(datenbankName.getName() + ".db");
	                         kdverw_Obj.setAktiveDB(datenbankName.getAbsolutePath() + ".db");
	                         
	                         System.out.println("Eingabe : " + datenbankName.getName());
	                     } else {
	                         // Nichts tun
	                     }
	            }
	        });
	        // 7 ***************** FERTIG !!!!! ********************************************************************************************************
	        kdverw_Obj.btn_speichernKunde.setOnAction(new EventHandler<ActionEvent>() {
	            boolean doppelteKdnr = false;
	            
	            @Override
	            public void handle(ActionEvent e) {
	               objDaten.setkdNummer(kdverw_Obj.getKdNr());
	               objDaten.setName(kdverw_Obj.getName());
	               objDaten.setVorname(kdverw_Obj.getVorname());
	               objDaten.setStrasse(kdverw_Obj.getStrasse());
	               objDaten.setHausnr(Integer.parseInt(kdverw_Obj.getHausnr()));
	               objDaten.setPlz(Integer.parseInt(kdverw_Obj.getPlz()));
	               objDaten.setOrt(kdverw_Obj.getOrt());
	               // doppelteKdnr = dataBaseCon.doppelteKundennr(objDaten, kdverw_Obj.getKdNr(), kdverw_Obj.getAktiveDB());
	               // Fehler bei Kundennummer abfrage           
	               
	               //if(!doppelteKdnr) {
	                   dBKontakt = dataBaseCon.neuerKunde(objDaten, kdverw_Obj.getAktiveDB());
	                   kdverw_Obj.setAbbruchSpeichernKunde(false);
	                   kdverw_Obj.setSpeichernKunde(false);
	                   guiPos.startZustand(kdverw_Obj);
	               //} else {
	               //    JOptionPane.showMessageDialog(null, "Datensatz konnte nicht gespeichert werden !!!\n"
	               //                                      + "Kundennummer bereits vorhanden !!!", "Fehler beim anlegen des Kunden", JOptionPane.CANCEL_OPTION);
	               //}
	            }
	                   
	        });
	        // 8 ***************** FERTIG !!!!! ********************************************************************************************************
	        kdverw_Obj.btn_abbruchSpeichernKunde.setOnAction(new EventHandler<ActionEvent>() {   
	            @Override
	            public void handle(ActionEvent e) {
	            guiPos.startZustand(kdverw_Obj);
	            }
	        });
	        // 9
	        kdverw_Obj.btn_speichernBestellung.setOnAction(new EventHandler<ActionEvent>() {
	            
	            @Override
	            public void handle(ActionEvent e) {
	                    
	                    
	                
	                    
	                    objBestellung.setBestellnummer(kdverw_Obj.getBestellNummer());
	                    objBestellung.setBestelldatum(kdverw_Obj.getBestellDatum());
	                    objBestellung.setZahlungsstart(kdverw_Obj.getZahlungsstart());
	                    objBestellung.setZahlungsende(kdverw_Obj.getZahlungsende());
	                    objBestellung.setRatenanzahl(Integer.parseInt(kdverw_Obj.getRatenzahl()));
	                    objBestellung.setErsteRate(Double.parseDouble(kdverw_Obj.getErsteRate()));
	                    objBestellung.setFolgeRate(Double.parseDouble(kdverw_Obj.getFolgeRate()));
	                    objBestellung.setBestellsumme(Double.parseDouble(kdverw_Obj.getBestellsumme()));
	                    //objBestellung.setBestellsumme(summary);
	                    objBestellung.setKundennummer(Double.parseDouble(kdverw_Obj.getBestellkdnr()));
	                    
	                    if(kdverw_Obj.getBestellFlag() == 0) {
	                                     
	                        dBKontakt = dataBaseCon.neueBestellung(objBestellung, kdverw_Obj.getAktiveDB());
	               
	                        kdverw_Obj.setListBestellnummer(""+objBestellung.getBestellnummer());
	                        System.out.println("%f"+objBestellung.getBestellnummer());    
	               
	                            if(dBKontakt) {
	                                kdverw_Obj.setAbbruchBestellung(false);
	                                kdverw_Obj.setSpeichernBestellung(false);
	                                guiPos.abbruchBestellung(kdverw_Obj);
	                            } else {
	                                // Buttons sichtbar lassen + Fehlermeldung
	                            }
	                        
	                    } else {
	                        dBKontakt = dataBaseCon.bestellungAendern(objBestellung, kdverw_Obj.getAktiveDB());
	                                                
	                        if(dBKontakt) {
	                           kdverw_Obj.setAbbruchBestellung(false);
	                           kdverw_Obj.setSpeichernBestellung(false);
	                           kdverw_Obj.setBestellFlag(0);
	                        } else {
	                            // buttons sichtbar lassen + Fehlermeldung
	                        }
	                        
	                      
	                    }        
	                
	            }
	                   
	        });            
	        // 10 ***************** FERTIG !!!!! ********************************************************************************************************    
	        kdverw_Obj.btn_abbruchBestellung.setOnAction(new EventHandler<ActionEvent>() {            
	            @Override
	            public void handle(ActionEvent e) {
	                guiPos.abbruchBestellung(kdverw_Obj);
	            }
	        });
	        // 11
	        kdverw_Obj.bestellliste.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	           
	            @Override
	            public void changed(ObservableValue<? extends String> observable, String oldValue, String aktuelleBestNr) {
	              
	                System.out.println("Ausgewähltes Element : " + "Altes Element " + oldValue + " neues Element " + aktuelleBestNr);
	                
	                  // Datenbankabfrage gibt Datenobjekt zurueck
	                  objBestellung = dataBaseCon.getBestellData(objBestellung,aktuelleBestNr,kdverw_Obj.getKdNr(),kdverw_Obj.getAktiveDB());  
	            
	                  kdverw_Obj.setBestellkdnr(new Double(objBestellung.getKundennummer()).toString()); // Werte in GUI setzen
	                  kdverw_Obj.setBestellnummer(objBestellung.getBestellnummer());
	                  kdverw_Obj.setBestellDatum(objBestellung.getBestelldatum());
	                  kdverw_Obj.setBestellsumme(objBestellung.getBestellsumme());
	                  kdverw_Obj.setErsteRate(objBestellung.getErsteRate());
	                  kdverw_Obj.setFolgeRate(objBestellung.getFolgeRate());
	                  kdverw_Obj.setZahlungsstart(objBestellung.getZahlungsstart());
	                  kdverw_Obj.setZahlungsende(objBestellung.getZahlungsende());
	                  kdverw_Obj.setRatenzahl(objBestellung.getRatenanzahl());
	                           
	            }
	        });
	        //12
	         kdverw_Obj.btn_Ratenplan.setOnAction(new EventHandler<ActionEvent>() {
	            
	            @Override
	            public void handle(ActionEvent e) {
	            
	            obj_Bestellliste = rechnen.listeAllerBestellObjekte(kdverw_Obj, objBestellung,objDaten,dataBaseCon);
	                               rechnen.setMonate(monate);
	                               
	            /// Create PDF aufrufen  => Liste mit Monaten + Monatliche Raten und Kundenobjekt
	            PDFerstellen pdfAusgabe = new PDFerstellen();
	                         pdfAusgabe.setKunde(objDaten);
	                         pdfAusgabe.setMonatlicheRate(monatlicheRate);
	                         pdfAusgabe.setMonatsListe(monatsliste);
	                         pdfAusgabe.erstelleNeuesPDF();
	            }
	        });
	        
	       // 13
	         kdverw_Obj.btn_bestellungLoeschen.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 
	                 Alert nutzerBestaetigung = new Alert(AlertType.CONFIRMATION);
	                       nutzerBestaetigung.setTitle("Bestellung löschen ?");
	                       nutzerBestaetigung.setHeaderText("Bestellung wirklich löschen ?");
	                       nutzerBestaetigung.setContentText("Soll die Bestellung Nr.: " + kdverw_Obj.getBestellNummer() + "\n wirklich gelöscht werden?" );
	                       Optional<ButtonType> result = nutzerBestaetigung.showAndWait();
	                       
	                       if(result.get() == ButtonType.OK) {
	                           //löschen
	                           dBKontakt = dataBaseCon.bestellungLoeschen(kdverw_Obj.getAktiveDB(), kdverw_Obj.getKdNr(), kdverw_Obj.getBestellNummer());
	                           System.out.println("Löschen erfolgreich ? " + dBKontakt);
	                           guiPos.abbruchBestellung(kdverw_Obj);
	                       } else {
	                           // nichts tun
	                       }
	            }
	        });
	        
	         // 14 ***************** FERTIG !!!!! ********************************************************************************************************
	         kdverw_Obj.btn_bestellungAendern.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 
	                guiPos.bestellungAendern(kdverw_Obj); // GUI Zustand verändern
	                kdverw_Obj.setBestellFlag(1);         // Flag fuer Auswahl korekter Datenbankaktion
	                System.out.println("Bestell Marker : " + kdverw_Obj.getBestellFlag());
	            }
	        });
	         // 15
	        kdverw_Obj.menuItemUebersicht.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                liste_Kundennummern = dataBaseCon.alleKundennummern(kdverw_Obj.getAktiveDB());
	                         
	                for(int aktuelleKdNr = 0; aktuelleKdNr < liste_Kundennummern.size();aktuelleKdNr++) {
	                    // Datenbankabfrage gibt Datenobjekt zurueck - dieses Objekt kommt auf die Liste
	                    liste_Obj_Kunde.add(dataBaseCon.getData(new Kunde(),liste_Kundennummern.get(aktuelleKdNr),kdverw_Obj.getAktiveDB()));
	                }
	    
	                AlleKunden tabTest = new AlleKunden(); // Tabelle erzeugen 
	                           tabTest.setTabDaten(liste_Obj_Kunde);
	                           // tabTest.erstelleFXTabelle(); // normale Tabelle - Fehlerhaft 21.04.2016
	                           tabTest.erstelleKundenTabelle(); // ueber einen kleinen Umweg
	                          
	            }
	        });
	        // 16 ************************* FERTIG *********************************
	        kdverw_Obj.menuItemEnde.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                    System.exit(0);
	            }
	        });
	      
	        kdverw_Obj.btn_RatenplanExcelExport.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 try {
	                    String fileName = getFileName("Excel Export",".xls",kdverw_Obj,primaryStage);
	                    System.out.println("Dateiname : " + fileName);
	                    excelExp.setCustomer(objDaten);
	                    excelExp.setMonatlicheRate(monatlicheRate);
	                    excelExp.setMonatsListe(monatsliste);
	                    excelExp.setOutputFile(fileName);
	                    excelExp.write();
	                    
	                 } catch (IOException | WriteException ex) {
	                     java.util.logging.Logger.getLogger(btnHandler.class.getName()).log(Level.SEVERE, null, ex);
	                 }
	                   
	                  
	                   
	                   
	                   
	            }
	        });
	        
	        
	        
	    }
	   
	private String getFileName(String title, String fileExt,
	                        Kundenverwaltung kdverw_Obj,Stage primaryStage) {
	        String str_fileName ="";         // Dateiname und extender wird übergeben
	        chooseDB.setTitle(title);  
	           
	        File fileName = chooseDB.showSaveDialog(primaryStage);  // Save Dialog
	            if(fileName != null) {
	                str_fileName = fileName.getAbsolutePath() + fileExt;
	                //str_fileName = fileName.getName() + fileExt;
	             
	            } else {
	                str_fileName = "";
	            }
	    return str_fileName; // Rueckgabe des Dateinamens
	}   

	 public void setMonth(ArrayList<String> month ) { this.month = month; }
	
	}
	 
	 
	 
	 
	 
	 
	 
	
}
