
public class Gui_States {

	
	    boolean on = true;
	    boolean off = false;
	    String activ = "green";
	    String passive = "white";
	    
	    public void gui_State_Start(Cust_Gui obj_Cust_Gui) {
	      // A little Change for commit
	      obj_Cust_Gui.setEditKdNr(on, activ);
	      obj_Cust_Gui.setKdnr("");
	      obj_Cust_Gui.setEditName(off,passive);
	      obj_Cust_Gui.setName("");
	      obj_Cust_Gui.setEditVorname(off,passive);
	      obj_Cust_Gui.setVorname("");
	      obj_Cust_Gui.setEditStrasse(off,passive);
	      obj_Cust_Gui.setStrasse("");
	      obj_Cust_Gui.setEditHausnr(off,passive);
	      obj_Cust_Gui.setHausnr("");
	      obj_Cust_Gui.setEditPlz(off,passive);
	      obj_Cust_Gui.setPlz("");
	      obj_Cust_Gui.setEditOrt(off,passive);  
	      obj_Cust_Gui.setOrt("");
	      obj_Cust_Gui.setGesamtUmsatz("");
	           
	      obj_Cust_Gui.setSpeichernKunde(off);
	      obj_Cust_Gui.setAbbruchSpeichernKunde(off);  
	      
	      obj_Cust_Gui.setEditBestellkdnr(off,passive);
	      obj_Cust_Gui.setEditBestellnummer(off,passive);
	      obj_Cust_Gui.setBestellnummer("");
	      obj_Cust_Gui.setEditBestelldatum(off,passive);
	      obj_Cust_Gui.setBestellDatum("");
	      obj_Cust_Gui.setEditZahlungsart(off,passive);
	      obj_Cust_Gui.setZahlungsstart("");
	      obj_Cust_Gui.setZahlungsende("");
	      obj_Cust_Gui.setEditRatenzahl(off,passive);
	      obj_Cust_Gui.setRatenzahl("");
	      obj_Cust_Gui.setEditErsterate(off,passive);
	      obj_Cust_Gui.setErsteRate("");
	      obj_Cust_Gui.setEditFolgerate(off,passive);
	      obj_Cust_Gui.setFolgeRate("");
	      obj_Cust_Gui.setBestellsumme("");
	      
	      obj_Cust_Gui.setSpeichernBestellung(off);
	      obj_Cust_Gui.setAbbruchBestellung(off);
	    }


	    public void neuenKundenAnlegen(Kundenverwaltung objKdverw) {
	        
	      objKdverw.setEditKdNr(on, activ);
	      objKdverw.setEditName(on, activ);
	      objKdverw.setEditVorname(on, activ);
	      objKdverw.setEditStrasse(on, activ);
	      objKdverw.setEditHausnr(on, activ);
	      objKdverw.setEditPlz(on, activ);
	      objKdverw.setEditOrt(on, activ);
	       
	      // Feldfarbe ändern
	      
	      objKdverw.setAbbruchSpeichernKunde(ein);
	      objKdverw.setSpeichernKunde(ein);
	        
	    }
	    
	    public void neuenKundenAnlegenErfolg() {
	        
	        
	        
	    }
	    
	   public void neueBestellungen(Kundenverwaltung objKdverw) {
	        objKdverw.setEditBestellnummer(on, activ);
	        objKdverw.setEditBestelldatum(on, activ);
	        objKdverw.setEditZahlungsart(on, activ);
	        objKdverw.setEditErsterate(on, activ);
	        objKdverw.setEditFolgerate(on, activ);
	        objKdverw.setEditRatenzahl(on, activ);
	        
	        objKdverw.setSpeichernBestellung(on);
	        objKdverw.setAbbruchBestellung(on);
	    }
	   public void abbruchBestellung(Kundenverwaltung objKdverw) {
	      objKdverw.setBestellkdnr("");
	      objKdverw.setEditBestellnummer(off,passive);
	      objKdverw.setBestellnummer("");
	      objKdverw.setEditBestelldatum(off,passive);
	      objKdverw.setBestellDatum("");
	      objKdverw.setEditZahlungsart(off,passive);
	      objKdverw.setZahlungsstart("");
	      objKdverw.setZahlungsende("");
	      objKdverw.setEditRatenzahl(off,passive);
	      objKdverw.setRatenzahl("");
	      objKdverw.setEditErsterate(off,passive);
	      objKdverw.setErsteRate("");
	      objKdverw.setEditFolgerate(off,passive);
	      objKdverw.setFolgeRate("");
	      objKdverw.setBestellsumme("");
	      
	      objKdverw.setSpeichernBestellung(aus);
	      objKdverw.setAbbruchBestellung(aus);
	   } 
	   
	   public void bestellungAendern(Kundenverwaltung objKdverw) {
	      objKdverw.setEditBestellnummer(off,passive);
	      objKdverw.setEditKdNr(off,passive);
	      
	      objKdverw.setEditBestelldatum(on, activ);
	      objKdverw.setEditZahlungsart(on, activ);
	      objKdverw.setEditRatenzahl(on, activ);
	      objKdverw.setEditErsterate(on, activ);
	      objKdverw.setEditFolgerate(on, activ);
	     
	      objKdverw.setSpeichernBestellung(on);
	      objKdverw.setAbbruchBestellung(on);
	   }
	
	
}
