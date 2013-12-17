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

/**
 * This class is used to process the assignment relational algebra queries
 * This class select the data from the database and use the data to create a new table
 * Then users can use the newly created table as it is an alias of the whole assignment
 * 
 * @author Lifan Qi
 * 
 * @version 1.0
 * 
 * */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class AssignProcess {
	static public String assignProcess(Connection Conn, String sql, String rela) throws SQLException{
		// get the data that need to be assigned
		Statement statement = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet res = statement.executeQuery(sql);
		String result = "";
		
		// create a new relation and insert the sql query results
		if(res != null){
			// get the number of rows and columns of the query results
			res.last();
			int rowcount = res.getRow();
			res.beforeFirst();
			ResultSetMetaData rsmd = res.getMetaData();			
			int colcount = rsmd.getColumnCount();
			// create the string arrays to store the content and column title
			String[][] content = new String[rowcount][colcount];
			String[] title = new String[colcount];
			
			// if nothing is fetched from the database
			if(rowcount == 0){
				return "No data to assign! Assignment failed!";
			}else{
				// get the results of each row
				int row = 0;
				while(res != null && res.next()){									
					for(int i = 1; i <= colcount; i++){
						result = result + rsmd.getColumnName(i) +": " + res.getObject(i);
						content[row][i-1] = res.getObject(i).toString();
						title[i-1] = rsmd.getColumnName(i);
					}					
					System.out.println(result);
					result += "\r\n";
					row++;
				}
				
				// create a table with assigned name
				String create = "CREATE TABLE " + rela + "(";
				for(int i = 0; i < colcount; i++){
					create += title[i] + " " + rsmd.getColumnTypeName(i + 1);
					if(i != colcount - 1){
						create += ", ";
					}
				}
				create += ");";
				statement.execute(create);
				
				// insert all the data
				String insert = "";
				System.out.println(rowcount);
				for(int i = 0; i < rowcount; i++){
					insert += "INSERT INTO " + rela + " VALUES (";
					for(int j = 0; j < colcount; j++){
						insert += "'" + content[i][j] + "'";
						if(j != colcount - 1){
							insert += ", ";
						}
					}
					insert += ");";
				}
				statement.execute(insert);
				return "Assign successfully, new relation " + rela + " is created.";
			}
		}
		else{
			return "No data to assign! Assignment failed!";
		}
	}
}
