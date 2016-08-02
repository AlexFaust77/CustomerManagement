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
	    Button btn_Order_Del = new Button("Bestellung L�schen");
	    Button btn_Order_Change = new Button("Bestellung �ndern");
	    Button btn_Plan_Excel = new Button("Excel Export");
	
	    @Override
	    public void start(Stage primaryStage) {
	    	
	    }
	
	public static void main(String[] args) {
		launch(args);

	}

}
