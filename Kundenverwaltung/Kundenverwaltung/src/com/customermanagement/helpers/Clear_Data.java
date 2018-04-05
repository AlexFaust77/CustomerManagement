package com.customermanagement.helpers;
import java.util.ArrayList;

import com.customermanagement.entities.Obj_Customer;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.main.MainGuiController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clear_Data {

	
	    private String strEmpty = "";
	    private int intEmpty;
	    private ObservableList<String> cleanlist = FXCollections.observableArrayList();
	    
	    public Obj_Customer cleanObjCustomer(Obj_Customer obj_Customer) {												// clear Customer Object
	           
	    	obj_Customer.setOrderlist(cleanlist);
	    	obj_Customer.setCustTotal(intEmpty);
	    	obj_Customer.setHouseNo(intEmpty);
	    	obj_Customer.setLastname(strEmpty);
	        obj_Customer.setResidenz(strEmpty);
	        obj_Customer.setPostcode(intEmpty);
	        obj_Customer.setCustBalance(intEmpty);
	        obj_Customer.setStreet(strEmpty);
	        obj_Customer.setFirstname(strEmpty);
	        obj_Customer.setOrderCount(intEmpty);
	        obj_Customer.setCustNo(strEmpty);
	     
	    return obj_Customer;
	    }
	    
	    public void cleanLists(ArrayList<String> month, ArrayList<String> rates, ObservableList<Obj_Order> allOrders) {
	        
	    	month.clear();
	    	rates.clear();
	    	allOrders.clear();
	            
	    }
	    
	    public void cleanMainGui(MainGuiController mainGuiController) {
	    	mainGuiController.setTxtCustomerID(strEmpty);
	    	mainGuiController.setTxtCustomerNo(strEmpty);
	    	mainGuiController.setTxtCustomerName(strEmpty);
	    	mainGuiController.setTxtCustomerSurname(strEmpty);
	    	mainGuiController.setTxtCustomerStreet(strEmpty);
	    	mainGuiController.setTxtCustomerNo(strEmpty);
	    	mainGuiController.setTxtCustomerPlc(strEmpty);
	    	mainGuiController.setTxtCustomerTown(strEmpty);
	    	
	    	mainGuiController.setTxtCustomerOrderAmount(intEmpty);
	    	mainGuiController.setTxtCustomerSummary(strEmpty);
	    	mainGuiController.setTxtCustomerOutstanding(strEmpty);
	    	//mainGuiController. --- leere Orderlist Items
	    	
	    	//mainGuiController. --- leere Tabelle
	    	//mainGuiController. --- leeres Diagramm
	    	
	    }
	    // CLEAN GUI - CLEAN CHART - CLEAN TABLE
	// data_cleaner.cleanLists(lst_month,abrechnungsmonate,monatlicheRaten,orderlist,lst_Obj_Customer,lst_All_Customers,monatsliste,lst_monthly_Rate);
	
}
