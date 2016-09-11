
public class Gui_States {

	
	    boolean on = true;
	    boolean off = false;
	    String activ = "green";
	    String passive = "white";
	    
	public void gui_State_Start(Cust_Gui obj_Cust_Gui) {
	     
	      obj_Cust_Gui.setEditCustNr(on, activ);  	 		// GUI Customer Textfields
	      obj_Cust_Gui.setCustNr("");
	      obj_Cust_Gui.setEditLastName(off,passive);
	      obj_Cust_Gui.setCustLastName("");
	      obj_Cust_Gui.setEditCustName(off,passive);
	      obj_Cust_Gui.setCustName("");
	      obj_Cust_Gui.setEditCustStreet(off,passive);
	      obj_Cust_Gui.setCustStreet("");
	      obj_Cust_Gui.setEditCustHNr(off,passive);
	      obj_Cust_Gui.setCustHNr("");
	      obj_Cust_Gui.setEditCustPc(off,passive);
	      obj_Cust_Gui.setCustPc("");
	      obj_Cust_Gui.setEditCustRes(off,passive);  
	      obj_Cust_Gui.setCustRes("");
	      obj_Cust_Gui.setTotal("");
	      
	      obj_Cust_Gui.setEditOrderCustNr(off,passive);        // GUI Order Textfields
	      obj_Cust_Gui.setEditOrderNr(off,passive);
	      obj_Cust_Gui.setOrderNr("");
	      obj_Cust_Gui.setEditOrderDate(off,passive);
	      obj_Cust_Gui.setOrderDate("");
	      obj_Cust_Gui.setEditPayStart(off,passive);
	      obj_Cust_Gui.setPayStart("");
	      obj_Cust_Gui.setPayEnd("");
	      obj_Cust_Gui.setEditRateCount(off,passive);
	      obj_Cust_Gui.setRateCount("");
	      obj_Cust_Gui.setEditFirstRate(off,passive);
	      obj_Cust_Gui.setFirstRate("");
	      obj_Cust_Gui.setEditRate(off,passive);
	      obj_Cust_Gui.setRate("");
	      obj_Cust_Gui.setOrderSummary("");
     
	      obj_Cust_Gui.setBtnCustSave(off);					  // GUI Buttons
	      obj_Cust_Gui.setBtnCustCancel(off);  
	      obj_Cust_Gui.setBtnOrderSave(off);
	      obj_Cust_Gui.setBtnOrderNoSave(off);
	    
	}


	    public void create_Customer(Cust_Gui obj_Cust_Gui) {   // Activate all Fields you need for new Customer
	        
	    	obj_Cust_Gui.setEditCustNr(on, activ);
	    	obj_Cust_Gui.setEditLastName(on, activ);
	    	obj_Cust_Gui.setEditCustName(on, activ);
	    	obj_Cust_Gui.setEditCustStreet(on, activ);
	    	obj_Cust_Gui.setEditCustHNr(on, activ);
	    	obj_Cust_Gui.setEditCustPc(on, activ);
	    	obj_Cust_Gui.setEditCustRes(on, activ);
	       
	       	obj_Cust_Gui.setBtnCustCancel(on);
	    	obj_Cust_Gui.setBtnCustSave(on);
	        
	    }
	    
	    public void neuenKundenAnlegenErfolg() {
	        
	        
	        
	    }
	    
	   public void create_Order(Cust_Gui obj_Cust_Gui) {    // Activate all Fields you need for new Order
		    obj_Cust_Gui.setEditOrderNr(on, activ);
		    obj_Cust_Gui.setEditOrderDate(on, activ);
		    obj_Cust_Gui.setEditPayStart(on, activ);
		    obj_Cust_Gui.setEditFirstRate(on, activ);
		    obj_Cust_Gui.setEditRate(on, activ);
		    obj_Cust_Gui.setEditRateCount(on, activ);
	        
		    obj_Cust_Gui.setBtnOrderSave(on);
		    obj_Cust_Gui.setBtnOrderNoSave(on);
	    }
	   public void cancel_Order(Cust_Gui obj_Cust_Gui) {
		   obj_Cust_Gui.setOrderCustNr("");
		   obj_Cust_Gui.setEditOrderNr(off,passive);
		   obj_Cust_Gui.setOrderNr("");
		   obj_Cust_Gui.setEditOrderDate(off,passive);
		   obj_Cust_Gui.setOrderDate("");
		   obj_Cust_Gui.setEditPayStart(off,passive);
		   obj_Cust_Gui.setPayStart("");
		   obj_Cust_Gui.setPayEnd("");
		   obj_Cust_Gui.setEditRateCount(off,passive);
		   obj_Cust_Gui.setRateCount("");
		   obj_Cust_Gui.setEditFirstRate(off,passive);
		   obj_Cust_Gui.setFirstRate("");
		   obj_Cust_Gui.setEditRate(off,passive);
		   obj_Cust_Gui.setRate("");
		   obj_Cust_Gui.setOrderSummary("");
	      
		   obj_Cust_Gui.setBtnOrderNoSave(off);
		   obj_Cust_Gui.setBtnOrderNoSave(off);
	   } 
	   
	   public void change_Order(Cust_Gui obj_Cust_Gui) {
		   obj_Cust_Gui.setEditOrderNr(off,passive);
		   obj_Cust_Gui.setEditCustNr(off,passive);
	      
		   obj_Cust_Gui.setEditOrderDate(on, activ);
		   obj_Cust_Gui.setEditPayStart(on, activ);
		   obj_Cust_Gui.setEditRateCount(on, activ);
		   obj_Cust_Gui.setEditFirstRate(on, activ);
		   obj_Cust_Gui.setEditRate(on, activ);
	     
		   obj_Cust_Gui.setBtnOrderSave(on);
		   obj_Cust_Gui.setBtnOrderNoSave(on);
	   }
	
	
}
