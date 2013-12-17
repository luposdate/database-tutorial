/**
 * Copyright (c) 2013, Institute of Information Systems (Sven Groppe and Lifan Qi), University of Luebeck
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 	- Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * 	  disclaimer.
 * 	- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * 	  following disclaimer in the documentation and/or other materials provided with the distribution.
 * 	- Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
 * 	  products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is the class that connect the applet with hsqldb.
 * It creates a memory database called "dbtutorial".
 * It also creates four tables (flug, flugzeug, mitarbeiter, zertificiert)
 * and inserts some testing data.
 * 
 * @author Lifan Qi
 * 
 * @version 1.0
 * 
 * */
public class MemoryDB {
	// connect to the in-memory database
		public static Connection connect(){
			try {

					// load the jdbcdriver of hsqldb

	                Class.forName("org.hsqldb.jdbcDriver");

	                // create a temporary database named test, user: "sa", pwd: ""

	                Connection connect = DriverManager.getConnection("jdbc:hsqldb:mem:dbtutorial;shutdown=true;sql.enforce_size=false", "sa", "");

	                System.out.println("connect successfully");     
	                
	                return connect;

	         } catch (SQLException e) {

	                  e.printStackTrace();
	                  
	                  return null;

	         } catch (ClassNotFoundException e) {

	                 e.printStackTrace();
	                 
	                 return null;

	         }
		}
		
		// initialize some data in the memory database
		public static void init(Connection conn){
			
			Statement statement;
			try {
				statement = conn.createStatement();
				
				// create the tables
				String createTableSql = "CREATE TABLE flug	(flugnummer int  NOT NULL, ursprung varchar(30) NOT NULL, ziel varchar(30) not null, distanz int not null, abflugzeit int not null, ankunftzeit int not null, PRIMARY KEY(flugnummer));" +
						"CREATE TABLE Mitarbeiter (personalnummer char(10) not null, name varchar(30) not null, gehalt int not null, primary key(personalnummer));" +
						"CREATE TABLE flugzeug (flugzeugnummer int not null, name varchar(30) not null, reichweite int not null, primary key (flugzeugnummer));" +
						"CREATE TABLE zertifiziert (personalnummer char(10) not null, flugzeugnummer int not null, primary key (personalnummer, flugzeugnummer), foreign key (personalnummer) references mitarbeiter, foreign key (flugzeugnummer) references flugzeug);";
	            
	            statement.execute(createTableSql);
	            
	            System.out.println("Create Table successfully!");
	            
	            // insert some testing data into the database
	            String insertSql = "INSERT INTO flug VALUES ('674839', 'Hamburg', 'Rome', '2000', '1000', '1300'); INSERT INTO flug VALUES ('549293', 'Rome', 'Pisa', '531', '1330', '1500');INSERT INTO flug VALUES ('546182', 'Rome', 'Barcelona', '600', '1400', '1800');" +
	            		"INSERT INTO Mitarbeiter VALUES ('111111', 'Bob', '1000'); INSERT INTO Mitarbeiter VALUES ('222222', 'Tom', '2000'); INSERT INTO Mitarbeiter VALUES ('333333', 'Mary', '3000');" +
	            		"INSERT INTO flugzeug VALUES ('1234', 'Good', '22'); INSERT INTO flugzeug VALUES ('4321', 'Bad', '33'); INSERT INTO flugzeug VALUES ('6666','NotBad','44');" +
	            		"INSERT INTO zertifiziert VALUES ('111111', '1234'); INSERT INTO zertifiziert VALUES ('222222', '4321');";
	            
	            statement.execute(insertSql);
	            
	            System.out.println("Insert successfully!");
	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Oops! Error Occurs!");
				
				e.printStackTrace();
			}
			
			 
		}
}
