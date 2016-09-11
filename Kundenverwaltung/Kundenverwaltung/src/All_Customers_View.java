import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class All_Customers_View {

    private TableView<Obj_Customer> tbl_Customer = new TableView<Obj_Customer>();				// vorher kundenTabelle
    private ObservableList<Obj_Customer> lst_Customer = FXCollections.observableArrayList();	// vorher customerList

    public TableView<Obj_Customer>create_All_Customers_Table() {
       
        TableColumn col_Cust_Nr   = new TableColumn("Kundennummer");									            	// create all Columns for Table
        			col_Cust_Nr.setMinWidth(100);																
        			col_Cust_Nr.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("Kundennummer"));		// Set value to TableField from Object
        			
        TableColumn col_Cust_Lastname  = new TableColumn("Nachname");
        			col_Cust_Lastname.setMinWidth(100);
        			col_Cust_Lastname.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("name"));
        			
        TableColumn col_Cust_Name  = new TableColumn("Vorname");
        			col_Cust_Name.setMinWidth(100);
        			col_Cust_Name.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("vorname"));
        			
        TableColumn col_Cust_Street  = new TableColumn("Strasse");
        			col_Cust_Street.setMinWidth(100);
        			col_Cust_Street.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("strasse"));
        			
        TableColumn col_Cust_HNr = new TableColumn("Haus-Nr.");
        			col_Cust_HNr.setMinWidth(100);
        			col_Cust_HNr.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("hausnr"));
        			
        TableColumn col_Cust_Pc = new TableColumn("Postleitzahl");    
        			col_Cust_Pc.setMinWidth(100);
        			col_Cust_Pc.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("plz"));
        			
        TableColumn col_Cust_Res = new TableColumn("Ort");
        			col_Cust_Res.setMinWidth(100);
        			col_Cust_Res.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("ort"));
        			
        TableColumn col_Cust_Total = new TableColumn("Gesamtumsatz");
        			col_Cust_Total.setMinWidth(100);
        			col_Cust_Total.setCellValueFactory(new PropertyValueFactory<Obj_Customer,String>("gesamtumsatz"));
                    
        TableColumn col_Cust_OrderCount = new TableColumn("Anzahl Bestellungen");
        			col_Cust_OrderCount.setMinWidth(50);
        			col_Cust_OrderCount.setCellValueFactory(new PropertyValueFactory<Obj_Customer,Integer>("anzBestell"));

        // add Columns to Table      
        tbl_Customer.getColumns().addAll(col_Cust_Nr,col_Cust_Lastname,col_Cust_Name,col_Cust_Street,col_Cust_HNr,col_Cust_Pc,col_Cust_Res,col_Cust_Total,col_Cust_OrderCount);
        tbl_Customer.setItems(lst_Customer);	// Add List with Customer Object to Table
        
        Stage stage = new Stage();				// create new Stage and Scene for extra window
        Scene scene = new Scene(new Group());
        stage.setTitle("Kundenübersicht");
        stage.setWidth(900);
        stage.setHeight(500);
        
        final Label label = new Label("Kundenübersicht");
        final VBox vbox = new VBox();
        		   vbox.setSpacing(5);
        		   vbox.setPadding(new Insets(10, 0, 0, 10));
        		   vbox.getChildren().addAll(label, tbl_Customer);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
return tbl_Customer;
}    

public void setLstCustomer(ObservableList<Obj_Customer> lst_Customer) { this.lst_Customer = lst_Customer; }    
	
	
}
