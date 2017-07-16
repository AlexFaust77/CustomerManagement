package com.customermanagement.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.log4j.Logger;

import com.customermanagement.entities.Obj_Customer;

public class HibernateStatements {				                       // Collection of all Hibernate Statements

	        
	private	SessionFactory  factory = new Configuration()			   // create Session factory
			         				  .configure("hibernate.cfg.xml")
					                  .addAnnotatedClass(Obj_Customer.class)
					                  .buildSessionFactory();
	
	private boolean databaseResult = false;
	
	public boolean writeCustomer (Obj_Customer customer, Logger logger) {
		   
			// create Session
			Session session = factory.getCurrentSession();
			
			try {
																		
				// start a transaction
				session.beginTransaction();
				
				// save Customer object
				logger.info("Saving the Customer");
				session.save(customer);
				
				// commit transaction
				session.getTransaction().commit();
				databaseResult = true;
				logger.info("databaseResult");
					
			} finally {
				factory.close();
			}
	
		return databaseResult;
	}
	
	
}
