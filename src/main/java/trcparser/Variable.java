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
package trcparser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a class used to store the information of the variables (like x, y, z, etc)
 *
 * @author Qi Lifan
 *
 * @version 0.1
 *
 * */
public class Variable implements Parsable{
	private final String tableName; // the name of the table which contains the variable
	private List<String> column = new ArrayList<String>(); // the list used to store the columns' names
	private final String variableName; // the name of the variable: x, y, z, etc
	private final boolean isFreeVariable = true; // whether the variable is a free variable

	// constructor
	public Variable(final String tableName, final String variableName, final Connection conn) throws SQLException{
		this.tableName = tableName;
		this.variableName = variableName;
		// get all the column name
		final Statement statement = conn.createStatement();
		final ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
		final ResultSetMetaData rsmd = rs.getMetaData();
		final int count = rsmd.getColumnCount();
		for(int i = 1; i <= count; i++){
			this.column.add(rsmd.getColumnName(i));
		}
	}

	public Variable(final String tableName, final String variableName){
		this.tableName = tableName;
		this.variableName = variableName;
	}

	// get table name
	public String getTableName(){
		return this.tableName;
	}

	// get variable name
	public String getVariableName(){
		return this.variableName;
	}

	public List<String> getColumn(){
		return this.column;
	}
	public void setColumn(final List<String> column){
		this.column = column;
	}

	// get the column name
	public String getColumnName(final int index){
		return this.column.get(index - 1);
	}

	public String getColumnName(final int index, final Connection conn){
		// get all the column name
		Statement statement;
		String column = null;
		try {
			statement = conn.createStatement();
			final ResultSet rs = statement.executeQuery("SELECT * FROM " + this.tableName);
			final ResultSetMetaData rsmd = rs.getMetaData();
			column = rsmd.getColumnName(index);
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return column;

	}

	// check if it is free variable
	public boolean isFreeVariable(){
		return this.isFreeVariable;
	}

	// check if it is the right variable
	public boolean isThisVaraialbe(final String variable){
		final String v = variable.split("[")[0];
		if(v.equals(this.variableName)) {
			return true;
		} else {
			return false;
		}
	}

	public String toSQL() {
		// TODO Auto-generated method stub
		return this.tableName + " " + this.variableName;
	}
}
