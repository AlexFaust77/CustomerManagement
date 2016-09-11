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
	    
	    public void cleanLists(ArrayList<String> month, 						// Clear all Lists
	                            ArrayList<Obj_Order> lst_orders,
	                            ObservableList<Obj_Customer> lst_obj_customer, ArrayList<String> lst_customernr,
	                            ArrayList<String> lst_monthly_Rate) {
	        
	    month.clear();
	    lst_orders.clear();
	    lst_obj_customer.clear();
	    lst_customernr.clear();
	    lst_monthly_Rate.clear();
        
	    }
	
	// data_cleaner.cleanLists(lst_month,abrechnungsmonate,monatlicheRaten,orderlist,lst_Obj_Customer,lst_All_Customers,monatsliste,lst_monthly_Rate);
	
}
