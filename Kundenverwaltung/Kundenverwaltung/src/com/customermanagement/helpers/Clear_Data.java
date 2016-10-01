package com.customermanagement.helpers;
import java.util.ArrayList;

import com.customermanagement.entities.Obj_Customer;
import com.customermanagement.entities.Obj_Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clear_Data {

	
	  private String str_empty = "";
	    private int int_empty;
	    private ObservableList<String> cleanlist = FXCollections.observableArrayList();
	    
	    public Obj_Customer cleanObjCustomer(Obj_Customer obj_Customer) {												// clear Customer Object
	           
	    	obj_Customer.setOrderlist(cleanlist);
	    	obj_Customer.setCustTotal(int_empty);
	    	obj_Customer.setHouseNo(int_empty);
	    	obj_Customer.setLastname(str_empty);
	        obj_Customer.setResidenz(str_empty);
	        obj_Customer.setPostcode(int_empty);
	        obj_Customer.setCustBalance(int_empty);
	        obj_Customer.setStreet(str_empty);
	        obj_Customer.setFirstname(str_empty);
	        obj_Customer.setOrderCount(int_empty);
	        obj_Customer.setCustNo(str_empty);
	     
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
