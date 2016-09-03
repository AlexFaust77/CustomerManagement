import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clear_Data {

	
	  private String str_empty = "";
	    private int int_empty;
	    private ObservableList<String> cleanlist = FXCollections.observableArrayList();
	    
	    public Obj_Customer cleanObjCustomer(Obj_Customer obj_Customer) {												// clear Customer Object
	           
	    	obj_Customer.setBestellNummern(cleanlist);
	    	obj_Customer.setCustTotal(int_empty);
	    	obj_Customer.setCustHnr(int_empty);
	    	obj_Customer.setLastname(str_empty);
	        obj_Customer.setCustRes(str_empty);
	        obj_Customer.setCustPc(int_empty);
	        obj_Customer.setCustBalance(int_empty);
	        obj_Customer.setCustStreet(str_empty);
	        obj_Customer.setCustName(str_empty);
	        obj_Customer.setOrderCount(int_empty);
	        obj_Customer.setCustNr(str_empty);
	     
	    return obj_Customer;
	    }
	    
	    public void cleanLists(ArrayList<String> month, ArrayList<String> abrechnungsmonate,						// Clear all Lists
	                           ArrayList<String> monatlicheRaten, ArrayList<Bestellung> orderlist,
	                           ObservableList<Kunde> liste_Obj_Kunde, ArrayList<String> liste_Kundennummern,
	                           ArrayList<String> monatsliste, ArrayList<String> monthly_Rate) {
	        
	    monate.clear();
	    abrechnungsmonate.clear();
	    monatlicheRaten.clear();
	    obj_Bestellliste.clear();
	    liste_Obj_Kunde.clear();
	    liste_Kundennummern.clear();
	    monatsliste.clear();
	    monatlicheRate.clear();
	        
	    }
	
	
	
}
