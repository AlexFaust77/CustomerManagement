import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class Save_Database_Information {

    String fileName = "InfoSys.txt";
    File database_Info_File = new File(fileName);
    PrintWriter writeFile = null;
    
    public void save_Database_Info(String str_DBname, Logger logger) {					// Save the last used Database - 
    																	                   
        try {
        	writeFile = new PrintWriter(new BufferedWriter(new FileWriter(database_Info_File)));
        	writeFile.println(str_DBname);
                   
        } catch (IOException ex) {
            logger.error("Save Database Info " + ex.getLocalizedMessage());
        } finally {
            if(writeFile != null) {
               writeFile.flush();
               writeFile.close();
            }
        }
        
        
    }
    
    
    public void check_Database_File(Gui_States gui_states, Cust_Gui obj_Gui,Logger logger) {				// Load the information about the last used Database
        
        if(database_Info_File.exists() && database_Info_File.isFile() && database_Info_File.canRead()) {
            
           BufferedReader readFile = null;
           String filePath;
          
            try {
                readFile = new BufferedReader(new FileReader(fileName));
                
                  while((filePath = readFile.readLine()) != null) {
                        
                      obj_Gui.setActiveDB(filePath);
                      obj_Gui.setGoodResult();
                  }
                
                readFile.close();
                
            } catch (FileNotFoundException ex) {
            	logger.error("File Not Found " + ex.getLocalizedMessage());
            } catch (IOException ex) {
            	logger.error("Look for File " + ex.getLocalizedMessage());
            }
      }
   }
	
	
}
