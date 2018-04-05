package com.customermanagement.statistics;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

public class Chart_fx {

    private ArrayList<String> lst_Months = new ArrayList<String>();			    // vorher monatsliste
    private ArrayList<String> lst_Monthly_Rate = new ArrayList<String>();		// vorher monatliche Rate
    private ArrayList<Number> num_Months = new ArrayList<Number>();				// vorher monateNum
    private ArrayList<Number> num_Rates = new ArrayList<Number>(); 				// vorher rateNum
    
    @SuppressWarnings("rawtypes")
	private XYChart.Series data_Series = new XYChart.Series<>();  

@SuppressWarnings({ "unchecked", "rawtypes" })
public void createChartWithToolTips(LineChart<String, Number> orderLineChart) {
         
    final CategoryAxis xAxis = new CategoryAxis();
                       xAxis.setLabel("Monat");

    NumberAxis yAxis = new NumberAxis();
               yAxis.setLabel("Rate in Euro");
    
   orderLineChart = new LineChart<>(xAxis,yAxis);
    
    if(lst_Months.size() == 0) {
        
    } else {
    
    	convert_String_Number();
        
        reduceData(num_Rates,lst_Months);   // entfernung der Nullwerte
        
        for(int months = 0; months < lst_Months.size(); months++) {
        	data_Series.getData().add(new XYChart.Data<>(lst_Months.get(months),num_Rates.get(months)));
        }    
        
        orderLineChart.getData().add(data_Series);
                
    		for(XYChart.Series<String,Number> s : orderLineChart.getData()) {					    // create Tooltips for Chart => Display by Mouse Hover
          
    			for(XYChart.Data<String, Number> d : s.getData()) {
                        
    				Tooltip.install(d.getNode(), new Tooltip("Monat : " + d.getXValue().toString() +"\n" 
    													   + "Monatliche Rate : " 
    													   + d.getYValue() + " Euro"));
								
    				d.getNode().setOnMouseEntered(new EventHandler<Event>(){					// Adding class on hover
    					@Override
    					public void handle(Event event){
    						d.getNode().getStyleClass().add("onHover");
    					}
    				});
				
    				d.getNode().setOnMouseExited(new EventHandler<Event>() {					// Removing class on exit
    					@Override
    					public void handle(Event event) {
    						d.getNode().getStyleClass().remove("onHover");
    					}
    				});
			}
		} 
    }
}   
    
    public void convert_String_Number() { 														// convert String to Number format for chart
        rates(); 																				// Count to 48 Months to Display
        for(int position = 0; position < lst_Months.size();position++) {
        	num_Rates.add(Float.valueOf(lst_Monthly_Rate.get(position)));
        }
    }
    public void rates(){
        for(float x = 0; x < 48;x++) {
            num_Months.add(x);
        }
    }
   
    private void reduceData(ArrayList<Number> lst_Rate,ArrayList<String> lst_Months){			 // Reduce 0.00 Values Beginning on the End of List 
            boolean reduce = true;
            int int_endOfList = (lst_Rate.size()-1); 											 // size - 1 for Index
                      
            while(reduce) {
                    
                String rate = lst_Rate.get(int_endOfList).toString();
                  
                if(Double.parseDouble(rate) == 0.0) {
                   lst_Rate.remove(int_endOfList);
                   lst_Months.remove(int_endOfList);
                   int_endOfList--;
                } else {
                   reduce = false;
                }
            }
    }
        
    public void setMonthslist(ArrayList<String>lst_Months) {this.lst_Months = lst_Months; }
    public void setMonthlyRate(ArrayList<String>lst_Monthly_Rate) { this.lst_Monthly_Rate = lst_Monthly_Rate;}
    @SuppressWarnings("rawtypes")
	public XYChart.Series getChartData(){ return data_Series; }

	
	
}
