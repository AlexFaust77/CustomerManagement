package com.customermanagement.listeners;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Calculator;
import com.customermanagement.helpers.GuiState;
import com.customermanagement.inputchecks.InputChecks;
import com.customermanagement.main.Cust_Gui;
import com.customermanagement.main.OrderGui;
import com.customermanagement.main.OrderGuiController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OrderListeners {
	
	private boolean dataBaseResult; 										   // DB Contact Result
	Obj_Order objOrder = new Obj_Order("",null,null,null,0,0.0,0.0,0.0,"");    // Object Order
	Stage orderStage = new Stage();											   // Stage for Orders
	
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	int orderFlag = 0;														   // Order Flag 0 = new Order 1 = Update Order
	
	public OrderListeners(GuiState guiState,Cust_Gui mainGui,Logger logger, Stage mainGuiStage, OrderGui orderGui, 
						  InputChecks checkInput, Calculator calculate, SQL_Statements dataBaseRequest, TableView<Obj_Order> fx_Table_View) {
		/*
		 mainGui.btn_New_Order.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	            	// create new order GUI
	            	orderFlag = 0;
	              	createNewOrderGui(orderGui,mainGui,guiState,logger,objOrder);
	              	//mainGuiStage.hide();
	            }
	        });  
		 */
		 /*
		 orderGui.btn_Order_Save.setOnAction(new EventHandler<ActionEvent>() {					 					
	            // New Order Dialog or Update Order Dialog
	            @Override
	            public void handle(ActionEvent e) {
	            	if(orderFlag == 0) {	             	
	            	   saveOrderData(orderGui,logger,objOrder,checkInput,calculate,dataBaseRequest,mainGui,guiState);
	            		            	
	            	   if(userDialog("Eine weitere Bestellung ?","Frage","Möchten Sie eine weitere Bestellung erfassen ?")) {
	            		  orderGui.setOrderCustNr(mainGui.getCustNr());
	            		  guiState.newOrder(orderGui);	
	            	   } else {
	            		  guiState.cancelOrder(orderGui); 
	            		  orderStage.close();
	            		  mainGuiStage.show();
	            	   }
	            	} else {
	            		
	            		String dialogContent = "Möchten Sie die Änderung wirklich speichern" + "\nBestellnummer: " + orderGui.getOrderNr();
	            		
	            		if(userDialog("Änderung Speichern ?","Frage",dialogContent)) {
	            		   saveOrderData(orderGui,logger,objOrder,checkInput,calculate,dataBaseRequest,mainGui,guiState);
	            		} else {
	            		   guiState.cancelOrder(orderGui); 
		            	   orderStage.close();
		            	   mainGuiStage.show();	
	            		}
	            	}
	            }
          });
		 */
		 /*
		 orderGui.btn_Order_Del.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String communicationContent = "Wollen Sie diese Bestellung wirklich löschen ?" + "\nBestellnummer: " + orderGui.getOrderNr();
								
				if(userDialog("Wirklich löschen ?","Frage",communicationContent)) {
					dataBaseRequest.deleteOrder(mainGui.getActiveDB(), orderGui.getOrderCustNr(), orderGui.getOrderNr(), logger);
					guiState.cancelOrder(orderGui);
					orderStage.close();
				} else {
					guiState.cancelOrder(orderGui);
					orderStage.close();
				}
			}
		 });
		 */
		 /*
		 orderGui.btn_Order_Cancel.setOnAction(new EventHandler<ActionEvent>() {  // not needed for FXML FOrm

			@Override
			public void handle(ActionEvent event) {
				guiState.cancelOrder(orderGui);
				orderStage.close();
			}
		 });
		 */
		/* 
		mainGui.orderList.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent click) {
				if(click.getClickCount() == 2) {
					String currentOrderNo = mainGui.orderList.getSelectionModel().getSelectedItem();
					orderFlag = 1;
					
					objOrder = dataBaseRequest.getOneOrderData(objOrder, currentOrderNo, mainGui.getCustNr(), mainGui.getActiveDB(), logger);
					logger.debug("com.customermanagement.listeners.OrderListeners - orderList" + objOrder.getOrderNo());
					
					createNewOrderGui(orderGui, mainGui, guiState, logger,objOrder);
					// Nicht vollständig
					System.out.println("Selected Orderno. " + currentOrderNo);
				}
				
			}
			
			
			
		}); 
	    */
		mainGui.orderPanel.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent mouseEvent) {
				System.out.println("Geklickt auf TabPane");
				
				int numberOfTabs = mainGui.orderPanel.getTabs().size();
		        int selectedIndex = mainGui.orderPanel.getSelectionModel().getSelectedIndex();
		  
		        Tab selectedTab = mainGui.orderPanel.getSelectionModel().getSelectedItem();
		        Node selectedContent = selectedTab.getContent();
		        @SuppressWarnings("rawtypes")
				TableView selectedTable = (TableView) selectedContent.lookup("CurrentOrders");
				// bis hier kein Fehler bei klick
		        System.out.println("Ende");
		        
		     //   TableViewSelectionModel selectionModel = tableview.getSelectionModel();
		     //   ObservableList selectedCells = selectionModel.getSelectedCells();
		     //   TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		     //   Object val = tablePosition.getTableColumn().getCellData(newValue);
		     //   System.out.println("Selected Value" + val);
		        
		        
		        
		       // Obj_Order order = (Obj_Order) selectedTable.getSelectionModel().getSelectedItem();
		       // System.out.println(order.getOrderNo());
		        
		        
				System.out.println("Number of Tabs " + numberOfTabs + "Selected Index " + selectedIndex);
						
			}
			
			
			
		}); 

	 
			 
}			 
			 
			 /*
			  * tabPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent> () {
    @Override
    public void handle(MouseEvent mouseEvent) {
        EventTarget eventTarget = mouseEvent.getTarget();
        if (eventTarget instanceof StackPane) {
            StackPane stackPane = (StackPane) eventTarget;
            ObservableList<String> styleClasses = stackPane.getStyleClass();
            for (String styleClass : styleClasses) {
                if ("arrow".equals(styleClass) == true) {
                    //TODO
                    System.out.println("arrowEvent");
			  * 
			  * 
			  * Tab selectedTab = tabPane.getSelectionModel().getSelectedItem()
Node selectedContent = selectedTab.getContent()
TableView selectedTable = selectedContent.lookup("CurrentOrders")
			  * table.setOnMousePressed(new EventHandler<MouseEvent>() {
    @Override 
    public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            System.out.println(table.getSelectionModel().getSelectedItem());                   
        }
    }
});
			  * 
			  */
			 

	
	
	private void saveOrderData(OrderGui orderGui, Logger logger, Obj_Order objOrder,InputChecks checkInput, 
			                   Calculator calculate, SQL_Statements dataBaseRequest, Cust_Gui mainGui, GuiState guiState) {
				
	 //	    checkInput.checkAllDates(orderGui, logger,objOrder);
	//		checkInput.checkAllIntegers(orderGui, logger, objOrder,calculate);
	//		checkInput.checkAllDouble(orderGui, logger,objOrder);
			
			logger.debug("All input Values checked" + "\n");

			// Fill Order values into Order Object                    
			objOrder.setOrderNo(orderGui.getOrderNr());
			objOrder.setCustNo(orderGui.getOrderCustNr()); 			// transfered from Customer Object no need to Check
    
			logger.debug("OrderNr + CustNo set into Order Object" + objOrder.getOrderNo() + " "  + objOrder.getCustNo() + "\n");
	
            if(checkInput.getCheck_ok()) { 
           	   logger.debug("com.customermanagement.listeners saveOrderData - OrderListeners - check OK"); 
               	                    
               if(orderFlag == 0) {
                  dataBaseResult = dataBaseRequest.new_Order(objOrder, mainGui.getActiveDB(),logger);
               } else {
                  dataBaseResult = dataBaseRequest.updateOrder(objOrder, mainGui.getActiveDB(),logger);
               }
                            
               if(dataBaseResult) {
                  orderGui.setBtnOrderNoSave(false);
                  orderGui.setBtnOrderSave(false);
                  guiState.cancelOrder(orderGui);
                	
                  logger.debug("com.customermanagement.listeners saveOrderData - OrderListeners - Order saved/Flag: " + orderFlag );
                            	
                } else {
                  // Buttons visible and Error Message
                  logger.error("com.customermanagement.listeners saveOrderData - OrderListeners - Order Not saved/Flag: " + orderFlag);
                }
                           
            } else {
              logger.error("com.customermanagement.listeners saveOrderData - OrderListeners - Check Input from Customer"); 
            }
	}
	
	
	private void createNewOrderGui(OrderGui orderGui, Cust_Gui mainGui, GuiState guiState, Logger logger, Obj_Order objOrder) {
    		
		 /*                  
		                   
		    Unterer Block wird entfernt wenn neu OrderGui fertig implementiert wurde               
		                   
		 */                  
    	if(orderFlag == 0) {
    		try {
    			
    			orderGui.btn_Order_Del.setVisible(false);
    			orderGui.start(orderStage);
    			orderGui.setOrderCustNr(mainGui.getCustNr());
    			guiState.newOrder(orderGui);
			
    		} catch (Exception e1) {
    			logger.error("com.customermanagement.listeners.OrderListeners - createNewOrderGui");
    			logger.error(e1.getStackTrace());
    		}
    	} else {
    		try {
    			fillOrderGui(orderGui,objOrder);
    			// Buttons speichern oder Delete sichtbar machen
    			orderGui.btn_Order_Del.setVisible(true);
    		
    			orderGui.start(orderStage);
    		
    		} catch (Exception e1) {
    			logger.error("com.customermanagement.listeners.OrderListeners - createNewOrderGui");
    			logger.error(e1.getStackTrace());
    		}
    		
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
	
	private void fillOrderGui(OrderGui orderGui, Obj_Order objOrder) {
		
		orderGui.setOrderCustNr(objOrder.getCustNo());
		orderGui.setOrderNr(objOrder.getOrderNo());
		orderGui.setOrderDate(dateFormat.format(objOrder.getOrderDate()));
		orderGui.setPayStart(dateFormat.format(objOrder.getPayStart()));
		orderGui.setPayEnd(dateFormat.format(objOrder.getPayEnd()));
		orderGui.setRateCount(objOrder.getRateCount());
		orderGui.setFirstRate(objOrder.getFirstRate());
		orderGui.setRate(objOrder.getRate());
		orderGui.setOrderSummary(objOrder.getOrderSummary());
		orderGui.setStillToPay(objOrder.getStillToPay());
		orderGui.setAlreadyPaid(objOrder.getAlreadyPaid());
	
	}
	


}
