import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class Obj_Customer {          							// Customer Object Stores Customer Information - Getter and Setter

	    private String cust_Nr;
	    private String cust_Lastname;
	    private String cust_Name;
	    private String cust_Street;
	    private int cust_Hnr;
	    private int cust_Pc;
	    private String cust_Res;
	    private int order_Count;
	    private double cust_Total;
	    private double cust_Balance;
	    private ObservableList<String> bestellItems = FXCollections.observableArrayList();
	    
    
	    public String getCustNr(){return cust_Nr;}
	    public void setCustNr(String cust_Nr) {this.cust_Nr = cust_Nr;}
	    
	    public String getLastname() {return cust_Lastname; }
	    public void setLastname(String cust_Lastname) { this.cust_Lastname = cust_Lastname; }
	    
	    public String getCustName() { return cust_Name; }
	    public void setCustName(String cust_Name) { this.cust_Name = cust_Name;}
	    
	    public String getCustStreet() { return cust_Street; }
	    public void setCustStreet(String cust_Street ) { this.cust_Street = cust_Street; }
	    
	    public int getCustHnr() { return cust_Hnr; }
	    public void setCustHnr(int cust_Hnr) { this.cust_Hnr = cust_Hnr; }
	    
	    public int getCustPc() { return cust_Pc; }
	    public void setCustPc(int cust_Pc) { this.cust_Pc = cust_Pc; }
	    
	    public String getCustRes() { return cust_Res; }
	    public void setCustRes(String cust_Res) { this.cust_Res = cust_Res; }
	    
	    public int getOrderCount() { return order_Count; }
	    public void setOrderCount(int order_Count) {this.order_Count = order_Count;}
	    
	    public double getCustTotal() { return cust_Total; }
	    public void setCustTotal(double cust_Total) { this.cust_Total = cust_Total; }
	    
	    public double getCustBalance() { return cust_Balance; }
	    public void setCustBalance( double cust_Balance ) { this.cust_Balance = cust_Balance;}
	    
	    public ObservableList getBestellNummern() { return bestellItems; }
	    public void setBestellNummern(ObservableList bestellNummern) { this.bestellItems = bestellNummern; }
	
}
