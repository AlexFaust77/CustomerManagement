import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calculator {

	    private ArrayList<String> lst_month = new ArrayList<String>();				 // vorher monat
	    private ArrayList<Obj_Order> obj_orderlist = new ArrayList<Obj_Order>();	 // obj Bestellliste
	    private ArrayList<Double> lst_rates = new ArrayList<Double>();               // li_ratesPerOrder
	    private Double[] ar_monthly_rates  = new Double[200];						 // monthlyRate
	    private Double[] ar_still_payed = new Double[200];							 // stillPayed
	    private ArrayList<String> str_monthly_rate = new ArrayList<String>();
	    
	    private ArrayList<String> monate = new ArrayList<String>();
	    private ArrayList<String> monatsliste = new ArrayList<String>();
	    private ArrayList<String> monatlicheRate = new ArrayList<String>();
	    
	    
	    private ArrayList<String> rateHashMap = new ArrayList<String>();
	    private ArrayList<String> monateHashMap = new ArrayList<String>();
	    private ArrayList<Integer> monatHashMap = new ArrayList<Integer>();
	    private ArrayList<Integer> jahrHashMap = new ArrayList<Integer>();
	    
	   
	    
	    /*
	     *   private ArrayList<String> monat = new ArrayList<String>();
    private ArrayList<String> monate = new ArrayList<String>();
    private ArrayList<String> monatsliste = new ArrayList<String>();
    private ArrayList<String> monatlicheRate = new ArrayList<String>();
    private ArrayList<Bestellung> obj_Bestellliste = new ArrayList<Bestellung>();
    
    private ArrayList<String> rateHashMap = new ArrayList<String>();
    private ArrayList<String> monateHashMap = new ArrayList<String>();
    private ArrayList<Integer> monatHashMap = new ArrayList<Integer>();
    private ArrayList<Integer> jahrHashMap = new ArrayList<Integer>();
    
    private Double[] monthlyRate  = new Double[200];
    private Double[] stillPayed = new Double[200];
    private ArrayList<String> str_monthlyRate = new ArrayList<String>();
    private ArrayList<Double> li_ratesPerOrder = new ArrayList<Double>();
	     * 
	     */
	    
	    
	    int aktTag;
	    int aktMonat;
	    int aktJahr;
	    
	    public void fill_month_lst(Cust_Gui Obj_Cust_Gui,						// fill Month list from now to x rates
	    						   SQL_Statements dataBase_Request ) {
	        
	       
	       Date date = new Date();							
	       int current_month;		// vorher monatZahl
	       int current_year;		// vorher jahreszahl
	        
	       DateFormat dfY = new SimpleDateFormat("yyyy"); 			           // Dateformat for each part of Date
	       DateFormat dfLM = new SimpleDateFormat("MMMM"); 
	       DateFormat dfkM = new SimpleDateFormat("MM"); 
	       
	       current_month = Integer.parseInt(dfkM.format(date));		       // parse int into Dateformat
	       current_year = Integer.parseInt(dfY.format(date));
     
	       for(int month_counter = 0; month_counter< 48;month_counter++) {
	             
	    	   	  lst_month.add(current_month + "/" + current_year);				
	              monatHashMap.add(current_month);  // TEST HASHMAP 25.04.2016
	              jahrHashMap.add(current_year);
	              
	           if(current_month == 12) {
	        	  current_month = 0;
	        	  current_year++;
	           } 
	           current_month++;
	       }
	         
	       
	    }

	    public String paymentEndDate(String paymentStart,int rateCount) {
	        String paymentEnds="";
	             
	        String arr_Date[] = paymentStart.split("-"); // Datum in 3 bestandteile splitten
	        
	        int day = Integer.parseInt(arr_Date[0]);     // Tage
	        int month = Integer.parseInt(arr_Date[1]);   // Monate
	        int year = Integer.parseInt(arr_Date[2]);    // Jahre
	        
	        String month_formatter ="0";
	        String day_formatter ="0";
	        for(int x = 0; x < rateCount;x++) {
	            if(month == 12) {    // Wenn Monate 12 dann wird
	              month = 1;        // das Jahr um 1 erhoeht und Monate
	              year++;           // wieder auf null gesetzt
	            } else {
	              month++;
	            }
	        }
	        if(month < 10) { 
	           month_formatter = month_formatter + month; 
	        } else {
	           month_formatter = ""+ month;
	        }
	        if(day < 10) {
	           day_formatter = day_formatter + day; 
	        } else {
	            day_formatter = "" + day;
	        }
	        
	        paymentEnds = day_formatter + "-" + month_formatter + "-" + year; // Enddatum zusammenbau
	        
	    return paymentEnds;    // und zurueckliefern
	    }
	    
	    public ArrayList<Obj_Order> getOrder_Objects(Cust_Gui obj_Cust_Gui,Obj_Order obj_Order, SQL_Statements dataBase_Request) {			
	    
	        List orderlist = obj_Cust_Gui.getBestellNummernListe();							    // get Ordernumbers from Gui
	                         
	            for(int objektzaehler = 0; objektzaehler < orderlist.size();objektzaehler++){	// convert into fully Order Object List with all ordervalues
	                               
	            	Obj_Order temp_order = new Obj_Order();
	            	obj_Order = dataBase_Request.getOne_Order(temp_order,orderlist.get(objektzaehler).toString(), 
	            											  obj_Cust_Gui.getCustNr(), obj_Cust_Gui.getActiveDB());
	           
	            	obj_orderlist .add(obj_Order);
	            }
	    return obj_orderlist ;       
	    }
	    
	    public void fillMonthlyList() {
	         for(int monats_zaehler = 0; monats_zaehler < monate.size();monats_zaehler++) {  
	             monatsliste.add(monate.get(monats_zaehler));
	         }
	    }
	 
	 public void getAll_Rates() {     
	   
	    LocalDate currentDate = LocalDate.now();
	    
	    for(int y = 0; y < ar_monthly_rates.length; y++) {                   							// fill Array with values 0
	    	ar_monthly_rates[y] = 0.0;
	    	ar_still_payed[y] = 0.0;
	    }
	  
	    for(int current_order = 0; current_order < obj_orderlist.size(); current_order++) {				// calculate each order
	
	        int int_month_to_Pay = 0 ;
	        int int_rate_count = obj_orderlist.get(current_order).getRateCount();						// number of rates
	        double first_rate = obj_orderlist.get(current_order).getFirstRate();						// first rate
	        double follow_rate = obj_orderlist.get(current_order).getRate();							// all other rates
	        
	        String[] split_end_date = obj_orderlist.get(current_order).getPayEnd().split("-");			// split the Date for Pay End
	        lst_rates.clear();
	        
	        for(int fillRates = 0; fillRates < int_rate_count; fillRates++) {  						    // fill rate list with values
	            if(fillRates == 0) {																	// first Rate only on the Beginning
	            	lst_rates.add(first_rate);
	            } else {
	            	lst_rates.add(follow_rate);
	            }   
	        }
	       	        
	        LocalDate payment_ends = LocalDate.of(Integer.parseInt(split_end_date[2]), Integer.parseInt(split_end_date[1]), 01);          // End Date Year and Month
	        
	        Period date_diff = Period.between(currentDate,payment_ends);                                                                  // make Date Diff
	        int diff_in_months = date_diff.getMonths();
	        int diff_in_years = date_diff.getYears();
	        int diff_in_days = date_diff.getDays();
        
	        if(diff_in_years > 0 ) { 																									  // calculate Years to Month
	        	int_month_to_Pay = diff_in_years * 12;
	        } 
	    
	        if(diff_in_months > 0) { 																									  // diff in month
	        	int_month_to_Pay = int_month_to_Pay + diff_in_months;  
	        }
	        
	        if(diff_in_days > 0 ) { 																									  // opened month  month + 1
	        	int_month_to_Pay = int_month_to_Pay + 1; 
	        }
	        
	         	         
	        if(int_month_to_Pay > int_rate_count ) {																				      // Payment start is in future ? Not startet yet ? 
	            
	            int count_diff = int_month_to_Pay - int_rate_count;																		  // diff to pay start
	           
	            for(int future_rate =0; future_rate < int_month_to_Pay;future_rate++) {													  
	                if(future_rate < count_diff) {
	                	ar_monthly_rates[future_rate] = 0.0;																			  // fill Array beginning with 0 values			
	                } else {																											  // extension needed for first Rate !!	
	                	ar_monthly_rates[future_rate] = lst_rates.get((future_rate - count_diff));										  // fill normal rates if start Date is reached
	                }
	           }
	            
	        } else { 																													  // Paymentstart reached
	            
	        																															
	            int int_backwards  = lst_rates.size() - 1;																				  // index adjustment
	            
	            for(int add_rate = int_month_to_Pay; add_rate > 0; add_rate--) {														  // add rates to Array beginning on the End of list
	                
	            	ar_monthly_rates[add_rate-1] =ar_monthly_rates[add_rate-1] + lst_rates.get(int_backwards);
	                int_backwards--;
	            } 
	            
	            
	            for(int add_still_payed = ((int_rate_count - int_month_to_Pay) - 1); add_still_payed >= 0 ;add_still_payed--) {		      // add rates already paid
	             	ar_still_payed[add_still_payed] = ar_still_payed[add_still_payed] + lst_rates.get(add_still_payed);
	            }
	        
	            
	        } 
	        
	           
       
	    double payed = 0;
	    double to_pay = 0;
	        
	    for(int x = 0; x < ar_monthly_rates.length; x++) { 																				 // add up rates to pay
	    	to_pay = to_pay + ar_monthly_rates[x];
	    }
	       
	    for(int x = 0; x < ar_still_payed.length; x++) { 																				 // add up rates still payed
	        payed = payed + ar_still_payed[x];
	    }
	        
	    System.out.println(" bereits gezahlt : " + payed + " noch zu zahlen : " + to_pay + "\n");    
	        
	    }
	   
	 convert_double_to_string(ar_monthly_rates);																						 // convert doubles to string
	 }  
	 
	 private void convert_double_to_string(Double[] monthly_rates) {
	     for(int rate_counter = 0; rate_counter < 72; rate_counter++) {
	         str_monthly_rate.add(monthly_rates[rate_counter].toString());
	    
	     }
	 }
	 
	    
	    public ArrayList<String> getLstMonth(){ return lst_month; }
	    public void setLstMonth(ArrayList<String> lst_month) { this.lst_month = lst_month; }
	    public ArrayList<String> getMonthlyRate() { return str_monthly_rate; }
	    
	    public ArrayList<String> getMonatsliste() { return monatsliste;}
	  
	   // public Double[] getStillPayed() { return stillPayed; }
	
	
	
	
	
	
}
