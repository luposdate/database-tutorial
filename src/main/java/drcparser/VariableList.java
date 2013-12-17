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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import drctree.SimpleNode;

/**
 * This is the class for a list of variables
 *
 * @author Lifan Qi
 * @version 1.0
 *
 * */
public class VariableList implements Parsable{

	private List<Variable> list;

	public void setList(final List<Variable> list){
		this.list = list;
	}
	public List<Variable> getList(){
		return this.list;
	}

	public VariableList(){
		this.list = new ArrayList<Variable>();
	}

	public String toSQL() {
		// TODO Auto-generated method stub
		String result = "FROM ";
		String comma = ", ";
		for(final Variable v : this.list){
			if(v.equals(this.list.get(this.list.size() - 1))) {
				comma = "";
			}
			result += v.toSQL() + comma;
		}
		return result;
	}

	// this method is used to translate the tables in domain calculus
	public void translateDomainVariables(final SimpleNode jjtTree, final Connection conn) throws SQLException{
		final int children = jjtTree.jjtGetNumChildren();
		for(int i = 0; i < children; i++){
			final SimpleNode n = (SimpleNode)jjtTree.jjtGetChild(i);
			if(n.toString().contains("TAB")){
				final String value = n.jjtGetValue().toString();
				final String[] s = value.split("\\(");
				final String tab = s[0].trim();
				String var = null;
				final Pattern pattern = Pattern.compile(RegularExpressions.variableNameMatch);
				final Matcher matcher = pattern.matcher(s[1]);
				while(matcher.find()){
					var = matcher.group();
					if(this.getVariable(var) == null) {
						this.list.add(new Variable(tab, var, conn));
					}
				}
			}
			if(n != null) {
				this.translateDomainVariables(n, conn);
			}
		}
	}
	public void translateDomainVariables(final SimpleNode jjtTree){
		final int children = jjtTree.jjtGetNumChildren();
		for(int i = 0; i < children; i++){
			final SimpleNode n = (SimpleNode)jjtTree.jjtGetChild(i);
			if(n.toString().contains("TAB")){
				final String value = n.jjtGetValue().toString();
				final String[] s = value.split("\\(");
				final String tab = s[0].trim();
				String var = null;
				final Pattern pattern = Pattern.compile(RegularExpressions.variableNameMatch);
				final Matcher matcher = pattern.matcher(s[1]);
				while(matcher.find()){
					var = matcher.group();
					if(this.getVariable(var) == null) {
						this.list.add(new Variable(tab, var));
					}
				}
			}
			if(n != null) {
				this.translateDomainVariables(n);
			}
		}
	}

	// get the corresponding table name for a specific variable name
	public String getTableByVariable(final String var){
		String table = null;
		for(final Variable v : this.list){
			if(v.getVariableName().equalsIgnoreCase(var)){
				table = v.getTableName();
				break;
			}
		}
		return table;
	}

	// get the variable corresponding to the input var,
	// which is in the form of VAR INDEX
	public Variable getVariable(final String var){
		Variable variable = null;
		for(final Variable v : this.list){
			if(var.startsWith(v.getVariableName())){
				variable = v;
				break;
			}
		}
		return variable;
	}

}
