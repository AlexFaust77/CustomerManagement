package com.customermanagement.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.customermanagement.entities.Obj_Customer;

public class HibernateStatements {				                       // Collection of all Hibernate Statements

	        
	private	SessionFactory  factory = new Configuration()			   // create Session factory
			         				  .configure("hibernate.cfg.xml")
					                  .addAnnotatedClass(Obj_Customer.class)
					                  .buildSessionFactory();
	
	private boolean databaseResult = false;
	
	public boolean writeCustomer (Obj_Customer customer) {
		   
			// create Session
			Session session = factory.getCurrentSession();
			
			try {
				// use the session object Java object
				System.out.println("Creating new Customer object ..... ");
														
				// start a transaction
				session.beginTransaction();
				
				// save the student object
				System.out.println("Saving the Customer");
				session.save(customer);
				
				// commit transaction
				session.getTransaction().commit();
				System.out.println("It´s Done!");
					
			} finally {
				factory.close();
			}
	
		return databaseResult;
	}
	
	
}
