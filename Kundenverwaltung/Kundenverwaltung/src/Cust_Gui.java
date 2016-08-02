import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cust_Gui extends Application {

	    Button btn_Cust_Save = new Button("Datensatz speichern");
	    Button btn_Cust_NoSave = new Button("Abbruch");
	    Button btn_Order_Save = new Button("Bestellung speichern");
	    Button btn_Order_NoSave = new Button("Abbruch");
	    Button btn_Cust_Search = new Button("Kunde suchen");
	    Button btn_Cust_New = new Button("Neuer Kunde");
	    Button btn_Order_New = new Button("Neue Bestellung");
	    Button btn_Cust_Del = new Button("Kunde Loeschen");
	    Button btn_Select_Db = new Button("Datenbankauswahl");
	    Button btn_New_Db = new Button("Neue Datenbank");
	    Button btn_Plan_Pdf = new Button("Ratenplan als PDF");
	    Button btn_Plan_Print = new Button("Ratenplan Drucken");
	    Button btn_Order_Del = new Button("Bestellung Löschen");
	    Button btn_Order_Change = new Button("Bestellung ändern");
	    Button btn_Plan_Excel = new Button("Excel Export");
	
	    TextField txt_Selected_Db = new TextField();
	    TextField txt_Cust_Nr = new TextField();
	    TextField txt_Cust_LastName = new TextField();
	    TextField txt_Cust_Name = new TextField();
	    TextField txt_Cust_Street = new TextField();
	    TextField txt_Cust_HNr = new TextField();
	    TextField txt_Cust_Pc = new TextField();
	    TextField txt_Cust_Res = new TextField();
	    
	    TextField txt_Order_Cust_Nr = new TextField();
	    TextField txt_Order_Nr = new TextField();
	    TextField txt_Order_Date = new TextField();
	    TextField txt_Pay_Start = new TextField();
	    TextField txt_Pay_End = new TextField();
	    TextField txt_Rate_Count = new TextField();
	    TextField txt_First_Rate = new TextField();
	    TextField txt_Rate = new TextField();
	    TextField txt_Order_Summary = new TextField();
	    TextField txt_Order_Count = new TextField();
	    TextField txt_Total = new TextField();
	    
	    ObservableList<DatenModellBest> tbl_Data_Records = FXCollections.observableArrayList();
	    ListView<String> lstv_Order_List = new ListView<String>();
	    ObservableList<String> items = FXCollections.observableArrayList();
	
	    TableView<DatenModellBest> fx_Table_View = new TableView<DatenModellBest>();
	    XYChart.Series daten;
	    ObservableList<XYChart.Series<String, Double>> obs_Lst_Data = FXCollections.observableArrayList();
	    Series<String, Double> series_Data = new Series<String, Double>();
	     
	    TabPane reg_Panel = new TabPane();
	    Tab tb_Reg = new Tab();
	    Tab tb_LineChart = new Tab();
	    
	    MenuBar mbar_Cust = new MenuBar();
	       
	    Menu m_File= new Menu("Datei");
	    MenuItem m_Cust_View = new MenuItem("Alle Kunden");
	    MenuItem m_Exit = new MenuItem("Beenden");
	    	    
	    CategoryAxis xAxis = new CategoryAxis();
	    NumberAxis yAxis = new NumberAxis();
	    LineChart<String,Number> linechart;
	    
	    private final int col_1 = 0;
	    private final int col_2 = 1;
	    private final int col_3 = 2;
	    private final int col_4 = 3;
	    private final int col_5 = 4;
	    private final int col_6 = 5;
	    private final int col_7 = 6;
	    private final int col_8 = 7;
	    private final int col_9 = 8;
	    private final int row_4 = 5;
	    private int order_Flag;
	    
	    @Override
	    public void start(Stage primaryStage) {
	    	
	    }
	    
	    
	    
	
	public static void main(String[] args) {
		launch(args);

	}

}
