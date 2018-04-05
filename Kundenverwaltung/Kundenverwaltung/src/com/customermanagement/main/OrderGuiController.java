package com.customermanagement.main;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Calculator;
import com.customermanagement.helpers.GuiState;
import com.customermanagement.helpers.Logger_Init;
import com.customermanagement.inputchecks.InputChecks;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class OrderGuiController {

	Stage orderStage = new Stage();
	private boolean dataBaseResult; 										   // DB Contact Result
	Obj_Order objOrder = new Obj_Order("",null,null,null,0,0.0,0.0,0.0,"");    // Object Order
	GuiState guiState = new GuiState();								       // Change GuiStateas and Styles
    Calculator calculate = new Calculator();								   // Calculate the Rates
    InputChecks checkInput = new InputChecks();      						   // Check all Input from User
    SQL_Statements dataBaseRequest = new SQL_Statements();	     		       // all SQL - Lite statements
    private static final Logger logger = Logger_Init.getInstance();			   // Logger
    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    MainGuiController mainGui; 											
    Stage mainStage = new Stage();
    int orderFlag = 0;											               // Order Flag 0 = new Order 1 = Update Order
	
	
    @FXML
    private TextField txtOrderDate;

    @FXML
    private Label lblFirstRate;

    @FXML
    private Label lblRate;

    @FXML
    private TextField txtAlreadyPaid;

    @FXML
    private Label lblPayStart;

    @FXML
    private Button btnOrderSave;

    @FXML
    private TextField txtPayStart;

    @FXML
    private TextField txtStillToPay;

    @FXML
    private TextField txtOrderSummary;

    @FXML
    private Label lblOrderCustNo;
   
    @FXML
    private Label lblOrderNo;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblStillToPay;

    @FXML
    private Label lblPayEnd;

    @FXML
    private TextField txtRate;

    @FXML
    private Label lblOrderSummary;

    @FXML
    private TextField txtOrderCustNo;
       
    @FXML
    private TextField txtOrderNo;

    @FXML
    private Label lblAlreadyPaid;

    @FXML
    private Button btnOrderCancel;

    @FXML
    private TextField txtPayEnd;

    @FXML
    private TextField txtRateCount;

    @FXML
    private TextField txtFirstRate;

    @FXML
    private Button btnOrderDelete;

    @FXML
    void orderSaveAction(ActionEvent event) {

    	if(orderFlag == 0) {	             	
     	   saveOrderData();
     		            	
     	   if(userDialog("Eine weitere Bestellung ?","Frage","Möchten Sie eine weitere Bestellung erfassen ?")) {
     		 // this.setOrderCustNo(mainGui.getTxtCustomerNo());
     		  guiState.newFXMLOrder(this);	
     	   } else {
     		 
     		  orderStage.close();
     		  mainStage.show();
     	   }
     	} else {
     		
     		String dialogContent = "Möchten Sie die Änderung wirklich speichern" + "\nBestellnummer: " + this.getOrderNo();
     		
     		if(userDialog("Änderung Speichern ?","Frage",dialogContent)) {
     		   saveOrderData();
     		} else {
     		   
         	   orderStage.close();
         	   mainStage.show();	
     		}
     	}
        	
    }

    @FXML
    void orderCancelAction(ActionEvent event) {
        
    	orderStage.close();
	}

    @FXML
    void orderDeleteAction(ActionEvent event) {
    	    String orderNo = this.getOrderNo();
            // Message - Really Delete the Order ?
            Alert customer_Confirmation = new Alert(AlertType.CONFIRMATION);
            	   customer_Confirmation.setTitle("Bestellung loeschen ?");
            	   customer_Confirmation.setHeaderText("Bestellung wirklich loeschen ?");
            	   customer_Confirmation.setContentText("Soll die Bestellung Nr.: " + orderNo + "\n wirklich geloescht werden?" );
                  Optional<ButtonType> result = customer_Confirmation.showAndWait();
                  // Delete Order if OK
                  if(result.get() == ButtonType.OK) {															
                      
                      dataBaseResult = dataBaseRequest.deleteOrder(mainGui.getTxtCurrentDatabase(), mainGui.getTxtCustomerNo(), orderNo,logger);
                      orderStage.close();
                      
                      // Remove OrderNO from orderlist
                      
                      //+ Message Deleted
                      logger.info("Order deleted");
                  } else {
                      // MSG - Class Impl
                  }
    }
    
    // Visibility off Buttons
    public void setBtnDelete(boolean onOff)    { this.btnOrderDelete.setVisible(onOff);     }
    public void setBtnSave(boolean onOff)      { this.btnOrderSave.setVisible(onOff);       }
	public void setBtnCancel(boolean onOff)    { this.btnOrderCancel.setVisible(onOff);     } 
	
	// Setter
	public void setOrderFlag(int orderFlag)        { this.orderFlag = orderFlag;                             }
	public void setOrderCustNo(String orderCustNo) { this.txtOrderCustNo.setText(orderCustNo);               }
	public void setMainGui(MainGuiController mainGuiController)       { this.mainGui = mainGuiController;    }
	public void setOrderNo(String orderNo)		   { this.txtOrderNo.setText(orderNo);                       }
	public void setMainStage(Stage mainStage)      { this.mainStage = mainStage;                             } 
	public void setOrderSummary(Double summary)    { this.txtOrderSummary.setText(summary.toString());       }
	public void setFirstRate(Double firstRate)     { this.txtFirstRate.setText(firstRate.toString());        }                  
	public void setRate(Double rate)               { this.txtRate.setText(rate.toString());                  }
	public void setRateCount(int rateCount)        { this.txtRateCount.setText(Integer.toString(rateCount)); }
	public void setAlreadyPaid(Double alreadyPaid) { this.txtAlreadyPaid.setText(alreadyPaid.toString());    }
	public void setStillToPay(Double stillToPay)   { this.txtStillToPay.setText(stillToPay.toString());      }
	public void setOrderDate(String orderDate)     { this.txtOrderDate.setText(orderDate);                   }
	public void setPayStart(String payStart)       { this.txtPayStart.setText(payStart);                     }
	public void setPayEnd(String payEnd)		   { this.txtPayEnd.setText(payEnd);                         }
	
	// Getter
	public String getOrderNo()     { return txtOrderNo.getText();     }
	public String getOrderCustNo() { return txtOrderCustNo.getText(); }
	public String getFirstRate()   { return txtFirstRate.getText();   }
	public String getRate()        { return txtRate.getText();        }
	public String getRateCount()   { return txtRateCount.getText();   }
	public String getOrderDate()   { return txtOrderDate.getText();   }
	public String getPayStart()    { return txtPayStart.getText();    }
	
	// Change Style
	public void setEditOrderNo(String color)   { this.txtOrderNo.setStyle("-fx-control-inner-background: " + color + ";");  }
	public void setEditOrderDate(String color) { this.txtOrderDate.setStyle("-fx-control-inner-background: " + color + ";");}
	public void setEditPayStart(String color)  { this.txtPayStart.setStyle("-fx-control-inner-background: " + color + ";"); }
	public void setEditFirstRate(String color) { this.txtFirstRate.setStyle("-fx-control-inner-background: " + color + ";");}
	public void setEditRate(String color)      { this.txtRate.setStyle("-fx-control-inner-background: " + color + ";");		}
	public void setEditRateCount(String color) { this.txtRateCount.setStyle("-fx-control-inner-background: " + color + ";");}
	
	
	/*
    // Order Fields Design and activation
    public void setEditOrderCustNr(boolean on_off, String color) { this.txt_Order_Cust_Nr.setEditable(on_off);      
	 this.txt_Order_Cust_Nr.setStyle("-fx-control-inner-background: " + color + ";");}
        
    */
    private void saveOrderData(){ 
	
    	checkInput.checkAllDates(this, logger,objOrder);
    	checkInput.checkAllIntegers(this, logger, objOrder,calculate);
    	checkInput.checkAllDouble(this, logger,objOrder);
	
	    logger.debug("All input Values checked" + "\n");
		
	    
	    logger.debug("All input Values checked" + "\n");

		// Fill Order values into Order Object                    
		objOrder.setOrderNo(this.getOrderNo());
		objOrder.setCustNo(this.getOrderCustNo()); 			// transfered from Customer Object no need to Check

		logger.debug("OrderNr + CustNo set into Order Object" + objOrder.getOrderNo() + " "  + objOrder.getCustNo() + "\n");

        if(checkInput.getCheck_ok()) { 
       	   logger.debug("com.customermanagement.listeners saveOrderData - OrderListeners - check OK"); 
           	                    
           if(orderFlag == 0) {
              dataBaseResult = dataBaseRequest.new_Order(objOrder, mainGui.getTxtCurrentDatabase(),logger);
           } else {
              dataBaseResult = dataBaseRequest.updateOrder(objOrder, mainGui.getTxtCurrentDatabase(),logger);
           }
                        
           if(dataBaseResult) {
              this.setBtnCancel(false);
              this.setBtnSave(false);
              orderStage.close();
            	
              logger.debug("com.customermanagement.listeners saveOrderData - OrderListeners - Order saved/Flag: " + orderFlag );
                        	
            } else {
              // Buttons visible and Error Message
              logger.error("com.customermanagement.listeners saveOrderData - OrderListeners - Order Not saved/Flag: " + orderFlag);
            }
                       
        } else {
          logger.error("com.customermanagement.listeners saveOrderData - OrderListeners - Check Input from Customer"); 
        }
	    
	    
	    
	    
    }
    
private boolean userDialog(String title, String header, String content) {
		
		boolean decision = false;
		
		Alert moreOrders = new Alert(AlertType.CONFIRMATION);
			  moreOrders.setTitle(title);
			  moreOrders.setHeaderText(header);
			  moreOrders.setContentText(content);
			  
			  ButtonType buttonYes = new ButtonType("Ja");
			  ButtonType buttonNo = new ButtonType("Nein");
			  
			  moreOrders.getButtonTypes().setAll(buttonYes, buttonNo);
			  
			  Optional<ButtonType> buttonResult = moreOrders.showAndWait();
			  
			  if(buttonResult.get()== buttonYes) {
				 decision = true; 
			  
			  } else if ( buttonResult.get() == buttonNo) {
				  decision = false;
			  } else {
				  decision = false;
			  }
			  
	return decision;		  
    }
    
    
    
    public void startFXMLOrderGui(Stage primaryStage, Obj_Order objOrder) {
    	
    	try {
    	
    		URL fileLocation = getClass().getResource("OrderGui.fxml");
    	    FXMLLoader fxmlLoader = new FXMLLoader();
    	    fxmlLoader.setController(this);
    	
    	    Parent orderGuiFXML = fxmlLoader.load(fileLocation.openStream());
			
			Scene scene = new Scene(orderGuiFXML,700, 650);  
								    
		    this.orderStage = primaryStage;
		    
		    if(orderFlag == 0) {
		    	primaryStage.setTitle("Neue Bestellung");
		    			    	
		    } else {
		       	primaryStage.setTitle("Bestellung ändern");
		       	guiState.changeFXMLOrder(this, objOrder, dateFormat);
		    }
		    
		    
		    primaryStage.setScene(scene);
		    primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
    }

}

