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
package drcparser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import drctree.DomainTree;
import drcparser.RegularExpressions;
import drcparser.Variable;
import drcparser.VariableList;
import drctree.ParseException;
import drctree.SimpleNode;

/**
 * This class is the parser for JavaCC generated parse tree for domain calculus
 * 
 * @author Lifan Qi
 * @version 1.0
 * 
 * */
public class DomainTreeParser {
	private SimpleNode domainTree;
	private String input;
	private VariableList list = new VariableList();
	private boolean firstCond = true; // used to decide whether it is the first condition of the conditions sequence
	private boolean firstTab = true; // used to decide whether it is the first table
	private boolean isForall = false; // used to decide if it is a for all symbol
	private boolean isNegative = false; // used to decide if it has a not symbol
	private boolean isFirst = true; // we need to ignore the first exist or for all symbol for domain calculus
	private int endBracketNum = 0; // used to calculate the number of end brackets we need at the end of the query
	private boolean hasWhere = false; // used to determine if a sub sql query already has a WHERE 
	private boolean isOr = false; // used to determine if the next conjunction between the conditions is OR
	private boolean isFirstOr = true; // used to determine if there is already an OR conjunction
	
	public DomainTreeParser(String input){
		this.input = input;
	}
	
	// this method is used to generate a tuple tree out of the input tuple calculus
	public void genDomainTree() throws UnsupportedEncodingException, ParseException, SQLException{
		InputStream ins = new ByteArrayInputStream(this.input.getBytes("UTF-8"));
		DomainTree dt = new DomainTree(ins, "UTF-8");
		this.domainTree = dt.Start();
		this.domainTree.dump(" ");
		
	}
	
	// this method is used to scan the the parse tree first, and to generate a variable list
	public void genVariableList(Connection conn) throws SQLException{
		// generate a whole variable list from the tuple tree
		// conn = MemoryDB.connect();
		list.translateDomainVariables(domainTree, conn);
	}	
	
	public void setList(VariableList list){
		this.list = list;
	}
	public VariableList getList(){
		return this.list;
	}
	
	public SimpleNode getDomainTree(){
		return domainTree;
	}
	
	// this method is used to parse the tuple tree, and convert it into a SQL query
	public String toSQL() throws SQLException, ParseException{ 
		
		// parse the whole parse tree into a SQL query
		String sql = "";
		this.firstCond = true;
		this.firstTab = true;
		this.isForall = false;
		this.isFirst = true;
		this.endBracketNum = 0;
		this.isOr = false;
		this.isFirstOr = true;
		
		sql += parse(this.domainTree);
		
		for(int i = 1; i <= endBracketNum; i++){
			sql += ")";
		}
		
		return sql;
	}
	
	// this method recursively convert the parse tree into sql
	private String parse(SimpleNode tree) throws ParseException{
		String sql = "";
		SimpleNode temp;
		int children = tree.jjtGetNumChildren();
		boolean firstConj = true;
		
		// parse all the children of the input tree
		for(int i = 0; i < children; i++){
			temp = (SimpleNode) tree.jjtGetChild(i); 
			
			// if the node's value is not null, parse the node
			if(temp.jjtGetValue() != null){
				String value = temp.jjtGetValue().toString();
				
				// if the node is the RES type node, 
				// which contains the column names which are wanted as the final search results
				if(temp.toString().startsWith("RES")){
					String[] res = value.split(",");
					String column = "";
					for(int j = 0; j < res.length; j++){
						Variable var = list.getVariable(res[j]);
						// try to get the index and column names
						Pattern num = Pattern.compile(RegularExpressions.numConstMatch);
						Matcher n = num.matcher(res[j]);
						while(n.find()){
							int index = Integer.parseInt(n.group());
							if(j == 0)
								column += var.getColumnName(index);
							else
								column += ", " + var.getColumnName(index);
						}
					}
					sql += "SELECT " + column + " FROM ";
					
					// the start of a new sub sql query
					hasWhere = false;
				}
				// if the node is a TAB type node,
				// which contains the info of the tables that we search from
				else if(temp.toString().startsWith("TAB")){
					// check if the number of variables is right
					String[] varlist = value.split("\\(")[1].split(",");
					Variable vlist = list.getVariable(varlist[0]);
					if(varlist.length != vlist.getColumnNum()){
						ParseException e = new ParseException("Wrong number of variables of the table " + vlist.getTableName() + ". Should be " + vlist.getColumnNum() + ". Not " + varlist.length);
						throw e;
					}
					// find all the table and its renamed alias
					String table = value.split("\\(")[0];
					String var = "";
					Pattern v = Pattern.compile(RegularExpressions.variableNameMatch);
					Matcher m = v.matcher(value.split("\\(")[1]);
					if(m.find())
						var = m.group();
					if(this.firstTab){						
						sql += table + " " + var;
						firstTab = false;
					}
					else
						sql += ", " + table + " " + var;
				}
				// if the node is the CONDS type node,
				// which means the beginning of an exist or for all conditions
				// it also have the info if there's a not symbol for the exist or for all
				else if(temp.toString().startsWith("TERM") && temp.jjtGetNumChildren() != 0){
					if(value.startsWith("¬")){
						isNegative = true;
					}else if(value.startsWith("∃")){
						if(isFirst)
							isFirst = false;
						else{
							if(!hasWhere){
								if(isNegative){
									sql += " WHERE NOT EXISTS (SELECT * FROM ";
								}else{
									sql += " WHERE EXISTS (SELECT * FROM ";
								}
							}else{
								if(isNegative){
									sql += " AND NOT EXISTS (SELECT * FROM ";
									hasWhere = false;
								}else{
									sql += " AND EXISTS (SELECT * FROM ";
									hasWhere = false;
								}
							}
							firstTab = true;
							firstCond = true;
							endBracketNum++;
						}
					}else if(value.startsWith("∀")){
						if(isFirst)
							isFirst = false;
						else{
							if(!hasWhere){
								if(isNegative){
									sql += " WHERE EXISTS (SELECT * FROM ";
								}else{
									sql += " WHERE NOT EXISTS (SELECT * FROM ";
								}
							}else{
								if(isNegative){
									sql += " AND EXISTS (SELECT * FROM ";
								}else{
									sql += " AND NOT EXISTS (SELECT * FROM ";
								}
							}
							firstTab = true;
							isForall = true;
							firstCond = true;
							endBracketNum++;
						}
					}
				}
				// if the node is a CONJ type node,
				// which are connected by OR
				else if(temp.toString().startsWith("CONJ")){
					if(firstConj){
						//sql += "(";
						firstConj = false;
					}else{
						isOr = true;
					}
				}
				// if the node is the COND type node,
				// which contains the info of one condition of the domain calculus
				else if(temp .toString().startsWith("COND")){
					Pattern var = Pattern.compile(RegularExpressions.variableMatch);
					Matcher matcher = var.matcher(value);
					while(matcher.find()){
						String v = matcher.group();
						//System.out.println(v);
						// try to get the index and the column names
						Pattern num = Pattern.compile(RegularExpressions.numConstMatch);
						Matcher n = num.matcher(v);
						while(n.find()){
							//System.out.println(n.group());
							String sqlreplace = "";
							Variable variable = list.getVariable(v);
							//System.out.println(variable);
							int index = Integer.parseInt(n.group());
							//System.out.println(index);
							String column = variable.getColumnName(index);
							sqlreplace += v.replace(n.group(), "") + "." + column;
							value = value.replace(v, sqlreplace);
						}
					}
					//value = value.replaceAll("^", "");
					// determine if there should be a WHERE or WHERE NOT
					if(firstCond){
						if(!isNegative && isForall){
							sql += " WHERE NOT( ";
							endBracketNum++;
							isNegative = false;
							isForall = false;
							hasWhere = true;
						}else{
							sql += " WHERE ";
							hasWhere = true;
						}
						sql += value;
						firstCond = false;
					}
					// whether it is connected by OR rather than AND
					else if(isOr){
						if(isFirstOr){
							sql += " OR (" + value;
							isOr = false;
							isFirstOr = false;
							endBracketNum++;
						}else{
							sql += ") OR (" + value;
							isOr = false;
						}
					}
					else
						sql += " AND " + value;
				}
			}
			// if the child node is not null, parse it recursively
			if(temp != null){
				sql += parse(temp);
			}
		}
		return sql;
	}
}

