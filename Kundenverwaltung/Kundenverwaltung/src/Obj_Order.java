
public class Obj_Order {				// Order Object one - Object for each Order

    private String str_Order_Nr;
    private String str_Order_Date;
    private String str_Pay_Start;
    private String str_Pay_End;
    private int rate_Count;
    private double first_Rate;
    private double rate;
    private double order_Summary;
    private double cust_Nr;
    
   
    public String getOrderNr(){return str_Order_Nr;}
    public void setOrderNr(String str_Order_Nr) {this.str_Order_Nr = str_Order_Nr;}

    public String getOrderDate(){return str_Order_Date;}
    public void setOrderDate(String str_Order_Date) {this.str_Order_Date = str_Order_Date;}

    public String getPayStart(){return str_Pay_Start;}
    public void setPayStart(String str_Pay_Start) {this.str_Pay_Start = str_Pay_Start;}

    public String getPayEnd(){return str_Pay_End;}
    public void setPayEnd(String str_Pay_End) {this.str_Pay_End = str_Pay_End;}

    public int getRateCount(){return rate_Count;}
    public void setRateCount(int rate_Count) {this.rate_Count = rate_Count;}
    
    public double getFirstRate(){return first_Rate;}
    public void setFirstRate(double first_Rate) {this.first_Rate = first_Rate;}
    
    public double getRate(){return rate;}
    public void setRate(double rate) {this.rate = rate;}
    
    public double getOrderSummary(){return order_Summary;}
    public void setOrderSummary(double order_Summary) {this.order_Summary = order_Summary;}
    
    public double getCustNr(){return cust_Nr;}
    public void setCustNr(double cust_Nr) {this.cust_Nr = cust_Nr;}
	
	
	
}
