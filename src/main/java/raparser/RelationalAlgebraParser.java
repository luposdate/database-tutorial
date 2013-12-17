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
package raparser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import ratree.ParseException;
import ratree.RATree;
import ratree.SimpleNode;

/**
 * This class takes the JavaCC parse tree of relational algebra and parse it into sql queries
 * 
 * @author Lifan Qi
 * 
 * @version 1.0
 * 
 * */
public class RelationalAlgebraParser {
	private SimpleNode raTree;
	private String input;
	private String assignAlias = null; // the alias used in the assign expression
	private String alias = null; // the alias of the table in the rename expression
	private String innerjoin = null; // the inner join conditions
	private boolean hasJoinCond = false; // check if the inner join has conditions
	private boolean hasSelect = false; // check if the expression already has SELECT
	private boolean hasProjInSet = false; // check if the expression has project in set operation
	private boolean SelProjRename = false; // check if the rename operation is on a selection or projection expression
	private int bracketNum = 0; // the number of right bracket that needs to be added in the generated sql query
	
	
	// constructor
	public RelationalAlgebraParser(String input){
		this.input = input;
	}
	
	// this method is used to generate a RA jjt tree
	public void genRATree() throws UnsupportedEncodingException, ParseException{
		InputStream ins = new ByteArrayInputStream(this.input.getBytes("UTF-8"));
		RATree ra = new RATree(ins, "UTF-8");
		this.raTree = ra.Start();
		this.raTree.dump(" ");
	}
	
	// this method parse the ra tree into sql query
	public String toSQL(){
		String sql = "";
		
		sql += parse(this.raTree);
		
		// if the expression is a assign expression 
		// it will execute the query and create a new relation
		if(assignAlias != null){			
			sql = sql + "|" + assignAlias; //"Assignment Operation Success!";
			assignAlias = null;
			return sql;
		}
		else{
			return sql;
		}
	}
	
	// parse the jjt tree node by node
	private String parse(SimpleNode tree){
		String sql = "";
		String conditions  = "";
		SimpleNode temp;
		int children = tree.jjtGetNumChildren();
		
		// parse all the children of the input tree
		for(int i = 0; i < children; i++){
			temp = (SimpleNode) tree.jjtGetChild(i); 
			
			// if the node's value is not null, parse the node
			if(temp != null && temp.jjtGetValue() != null){
				String value = temp.jjtGetValue().toString();
				
				// if the node is PROJ node
				// it means it has a project operator
				if(temp.toString().startsWith("PROJ")){
					// if the generated sql already contain a SELECT
					// which means this projection is used in the middle of the whole query
					// therefore, we need to put it in a pair of brackets
					if(hasSelect == false){
						sql += "SELECT ";
						hasSelect = true;
					}else{
						sql += "(SELECT ";
						hasProjInSet = true;
					}	
				}
				// if the node is ATTRLIST node
				// it has the attribute the users want to project
				else if(temp.toString().startsWith("ATTRLIST")){
					sql += value + " FROM ";
				}
				// if the node is SEL node
				// which means it is a select expression
				else if(temp.toString().startsWith("SEL")){
					if(temp.jjtGetParent().jjtGetParent().toString().startsWith("SETXP")
							|| temp.jjtGetParent().jjtGetParent().toString().startsWith("RENAME")){
						sql += "(SELECT * FROM ";
						bracketNum++;
					}else{
						if(hasSelect == false){
							sql += "SELECT * FROM ";
						}
					}
				}
				// if the node is CONDS node
				// where contains all the conditions
				else if(temp.toString().startsWith("CONDS")){
					value = value.replace(RAConstants.AND, " AND ");
					value = value.replace(RAConstants.OR, " OR ");
					if(temp.jjtGetParent().toString().startsWith("SETOP")){
						// this is the conditions for inner join
						innerjoin = value;
					}else{
						conditions = " WHERE " + value;
					}
				}
				// if the node is SETOP node
				// it is a node contains the set operators
				else if(temp.toString().startsWith("SETOP")){
					// if there is a projection expression in the set expression
					if(hasProjInSet){
						sql += ")";
						hasProjInSet = false;
					}
					if(SelProjRename){
						sql += " AS " + alias;
						SelProjRename = false;
						alias = null;
					}
					// replace all the operands with sql
					if(value.equals(RAConstants.UNION)){
						sql += " UNION ";
					}else if(value.equals(RAConstants.INTERSECT)){
						sql += " INTERSECT ";
					}else if(value.equals(RAConstants.CARTESIAN)){
						sql += ", ";
					}else if(value.equals(RAConstants.MINUS)){
						sql += " MINUS ";
					}else if(value.equals(RAConstants.JOIN)){
						sql += " JOIN ";
					}else if(value.contains(RAConstants.JOIN + "{")){
						sql += " INNER JOIN ";
						hasJoinCond = true; // the join has conditions
					}
				}
				// if the node is RENAME node
				// its value is a rename expression
				else if(temp.toString().startsWith("RENAME")){
					alias = value.split("\\(")[0].split(RAConstants.RENAME)[1];
					// rename the projection and selection sub expressions
					if(temp.jjtGetChild(0).jjtGetChild(0).toString().startsWith("PROJ")
							||temp.jjtGetChild(0).jjtGetChild(0).toString().startsWith("SEL")){
						SelProjRename = true;
					}
				}
				// if the node is RELA node
				// it contains a relation
				else if(temp.toString().startsWith("RELA")){
					// relation as name to be assigned
					SimpleNode n = (SimpleNode)temp.jjtGetParent();
					if(n.jjtGetNumChildren() > 1 && n.jjtGetChild(i+1).toString().startsWith("ASSIGN")){
						assignAlias = value;
					}
					// if the relation is not a name to be assigned
					else{
						if(alias != null && SelProjRename == false){
							sql += value + " AS " + alias;
							alias = null;
						}else if(alias == null || SelProjRename == true){
							sql += value;
						}
						// if there is join condition
						if(hasJoinCond && innerjoin != null){
							sql += " ON " + innerjoin;
							hasJoinCond = false;
							innerjoin = null;
						}
					}
				}
				// when the relational algebra uses a table name in a set operation to represent all the table's data
				// we need to expand it into a sql select query
				else if(temp.toString().startsWith("EXPR")){
					// the table name is used as the first operand of the set expression
					if(temp.jjtGetNumChildren() > 2
							&& temp.jjtGetChild(0).toString().startsWith("RELA")
							&& temp.jjtGetChild(1).toString().startsWith("SETOP")){
						if(temp.jjtGetChild(1).toString().contains(RAConstants.INTERSECT)
								|| temp.jjtGetChild(1).toString().contains(RAConstants.UNION)
								|| temp.jjtGetChild(1).toString().contains(RAConstants.MINUS))
							sql += "SELECT * FROM ";
					}
					// if the table name is used in the middle or the last operand of the set expression
					else if(temp.jjtGetParent().jjtGetNumChildren() > 2
							&& temp.jjtGetParent().jjtGetChild(1).toString().startsWith("SETOP")
							&& temp.jjtGetChild(0).toString().startsWith("RELA")){
						if(temp.jjtGetParent().jjtGetChild(1).toString().contains(RAConstants.INTERSECT)
								|| temp.jjtGetParent().jjtGetChild(1).toString().contains(RAConstants.UNION)
								|| temp.jjtGetParent().jjtGetChild(1).toString().contains(RAConstants.MINUS))
							sql += "SELECT * FROM ";
					}
				}
			}
			// if the child node is not null, parse it recursively
			if(temp != null)
				sql += parse(temp);
		}
		
		// add the conditions to sql query
		sql += conditions;
		if(!conditions.equals("")){
			for(int i = 0; i < bracketNum; i++){
				sql += ")";
			}
			bracketNum = 0;
		}
		
		return sql;
	}
}
