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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

import raparser.RelationalAlgebraParser;
import trcparser.TupleTreeParser;
import trctree.ParseException;
import drcparser.DomainTreeParser;

public class Tutorial extends JApplet implements ActionListener, KeyListener{
	/**
	 * The database Tutorial applet
	 * Supported functions:
	 * 1. execute SQL expressions;
	 * 2. Parse the relational algebra expressions into SQL queries and execute them.
	 * Supported features are: selection, projection, Cartesian product, union, complement, rename, assignment, join, intersection;
	 * 3. Parse the tuple calculus into SQL queries and execute them;
	 * 4. Parse the domain calculus into SQL queries and execute them.
	 *
	 * @author Lifan Qi
	 *
	 * @version 1.0
	 *
	 */
	private static final long serialVersionUID = 1L;

	// the connection with the HSQL database
	Connection conn;
	// the panel for each expression
	private JTabbedPane tabbedPane;
	private JPanel sql;
	private JPanel relationAlgebra;
	private JPanel tupleCalculus;
	private JPanel domainCalculus;
	// drop list for all the examples
	@SuppressWarnings("rawtypes")
	private JComboBox examples;
	// the label used to show the result
	private JPanel respanel;
	private JTextArea sqlres;
	private JTable extres;
	// operators for relational algebra and tuple/domain calculus
	private final static String selection = "σ";
	private final static String projection = "π";
	private final static String rename = "ρ";
	private final static String union = "∪";
	private final static String intersaction = "∩";
	private final static String cartesian = "×";
	private final static String join = "⋈";
	private final static String assign = "←";
	private final static String not = "¬";
	private final static String exist = "\u2203";
	private final static String forall = "\u2200";
	private final static String and = "Λ";
	private final static String or = "∨";
	// buttons for inputing the relational algebra and tuple/domain calculus operators
	private JButton selectionbutton;
	private JButton projectionbutton;
	private JButton cartesianbutton;
	private JButton unionbutton;
	private JButton joinbutton;
	private JButton intersactbutton;
	private JButton renamebutton;
	private JButton assignbutton;
	private JButton texistbutton;
	private JButton tforallbutton;
	private JButton tnotbutton;
	private JButton dexistbutton;
	private JButton dforallbutton;
	private JButton dnotbutton;
	private JButton randbutton;
	private JButton rorbutton;
	private JButton tandbutton;
	private JButton torbutton;
	private JButton dandbutton;
	private JButton dorbutton;
	// text areas for different functions
	private JTextArea sqlquery;
	private JTextArea algebra;
	private JTextArea tuple;
	private JTextArea domain;
	// execute buttons
	private JButton sqlexec;
	private JButton raexec;
	private JButton tcexec;
	private JButton dcexec;

	@Override
	public void init(){
		this.setLayout(new BorderLayout());
		this.setSize(850, 500);
		// init the panels
		this.tabbedPane = new JTabbedPane();
		this.sql = new JPanel();
		this.sql.setLayout(new BorderLayout());

		this.relationAlgebra = new JPanel();
		this.relationAlgebra.setLayout(new BorderLayout());

		this.tupleCalculus = new JPanel();
		this.tupleCalculus.setLayout(new BorderLayout());

		this.domainCalculus = new JPanel();
		this.domainCalculus.setLayout(new BorderLayout());

		// initial the combobox
		this.examples = new JComboBox<String>(Examples.egStrings);
		this.examples.addActionListener(this);

		// init the result area
		this.respanel = new JPanel();
		this.respanel.setLayout(new BorderLayout());
		this.respanel.add(this.examples, "North");

		// init the buttons
		this.selectionbutton = new JButton("<html><u>S</u>election σ</html>");
		this.selectionbutton.addActionListener(this);
		this.projectionbutton = new JButton("<html><u>P</u>roject π</html>");
		this.projectionbutton.addActionListener(this);
		this.cartesianbutton = new JButton("<html><u>C</u>artesian ×</html>");
		this.cartesianbutton.addActionListener(this);
		this.unionbutton = new JButton("<html><u>U</u>nion " + Tutorial.union + "</html>");
		this.unionbutton.addActionListener(this);
		this.joinbutton = new JButton("<html><u>J</u>oin " + Tutorial.join + "</html>");
		this.joinbutton.addActionListener(this);
		this.intersactbutton = new JButton("<html><u>I</u>ntersect ∩</html>");
		this.intersactbutton.addActionListener(this);
		this.renamebutton = new JButton("<html><u>R</u>ename " + Tutorial.rename + "</html>");
		this.renamebutton.addActionListener(this);
		this.assignbutton = new JButton("<html>Assi<u>g</u>n " + Tutorial.assign + "</html>");
		this.assignbutton.addActionListener(this);
		this.texistbutton = new JButton("<html><u>E</u>xist ∃</html>");
		this.texistbutton.addActionListener(this);
		this.tforallbutton = new JButton("<html><u>F</u>orall \u2200</html>");
		this.tforallbutton.addActionListener(this);
		this.tnotbutton = new JButton("<html><u>N</u>ot ¬</html>");
		this.tnotbutton.addActionListener(this);
		this.dexistbutton = new JButton("<html><u>E</u>xist ∃</html>");
		this.dexistbutton.addActionListener(this);
		this.dforallbutton = new JButton("<html><u>F</u>orall \u2200</html>");
		this.dforallbutton.addActionListener(this);
		this.dnotbutton = new JButton("<html><u>N</u>ot ¬</html>");
		this.dnotbutton.addActionListener(this);
		this.randbutton = new JButton("<html><u>A</u>nd Λ</html>");
		this.randbutton.addActionListener(this);
		this.rorbutton = new JButton("<html><u>O</u>r ∨</html>");
		this.rorbutton.addActionListener(this);
		this.tandbutton = new JButton("<html><u>A</u>nd Λ</html>");
		this.tandbutton.addActionListener(this);
		this.torbutton = new JButton("<html><u>O</u>r ∨</html>");
		this.torbutton.addActionListener(this);
		this.dandbutton = new JButton("<html><u>A</u>nd Λ</html>");
		this.dandbutton.addActionListener(this);
		this.dorbutton = new JButton("<html><u>O</u>r ∨</html>");
		this.dorbutton.addActionListener(this);

		// init the text areas
		this.sqlquery = new JTextArea();
		this.sqlquery.addKeyListener(this);
		this.algebra = new JTextArea();
		this.algebra.addKeyListener(this);
		this.tuple = new JTextArea();
		this.tuple.addKeyListener(this);
		this.domain = new JTextArea();
		this.domain.addKeyListener(this);

		// init the execute buttons
		this.sqlexec = new JButton("Execute SQL query");
		this.sqlexec.addActionListener(this);
		this.raexec = new JButton("Execute Relational Algebra");
		this.raexec.addActionListener(this);
		this.tcexec = new JButton("Execute Tuple Calculus");
		this.tcexec.addActionListener(this);
		this.dcexec = new JButton("Execute Domain Calclus");
		this.dcexec.addActionListener(this);

		// arrange the sql panel
		this.sqlquery.setLineWrap(true);
		this.sqlquery.setWrapStyleWord(true);
		this.sql.add(this.sqlquery, "Center");
		this.sql.add(this.sqlexec, "South");
		this.tabbedPane.addTab("SQL Query", null, this.sql, "Input and Execute SQL Queries");

		// arrange the ra panel
		final JPanel rabuttons = new JPanel();
		final JScrollPane scroller = new JScrollPane(rabuttons);
		scroller.setPreferredSize(new Dimension(700, 50));
		rabuttons.add(this.selectionbutton);
		rabuttons.add(this.projectionbutton);
		rabuttons.add(this.cartesianbutton);
		rabuttons.add(this.renamebutton);
		rabuttons.add(this.assignbutton);
		rabuttons.add(this.unionbutton);
		rabuttons.add(this.joinbutton);
		rabuttons.add(this.intersactbutton);
		rabuttons.add(this.randbutton);
		rabuttons.add(this.rorbutton);
		this.algebra.setLineWrap(true);
		this.algebra.setWrapStyleWord(true);
		this.relationAlgebra.add(scroller, "North");
		this.relationAlgebra.add(this.algebra, "Center");
		this.relationAlgebra.add(this.raexec, "South");
		this.tabbedPane.addTab("Relational Algebra", null, this.relationAlgebra, "Input and Execute Relational Algebra Queries");

		// arrange the tuple panel
		final JPanel trcbuttons = new JPanel();
		trcbuttons.add(this.texistbutton);
		trcbuttons.add(this.tforallbutton);
		trcbuttons.add(this.tnotbutton);
		trcbuttons.add(this.tandbutton);
		trcbuttons.add(this.torbutton);
		this.tuple.setLineWrap(true);
		this.tuple.setWrapStyleWord(true);
		this.tupleCalculus.add(trcbuttons, "North");
		this.tupleCalculus.add(this.tuple, "Center");
		this.tupleCalculus.add(this.tcexec, "South");
		this.tabbedPane.addTab("Tuple Calculus", null, this.tupleCalculus, "Input and Execute Tuple Calculus Queries");

		// arrange the domain panel
		final JPanel drcbuttons = new JPanel();
		drcbuttons.add(this.dexistbutton);
		drcbuttons.add(this.dforallbutton);
		drcbuttons.add(this.dnotbutton);
		drcbuttons.add(this.dandbutton);
		drcbuttons.add(this.dorbutton);
		this.domain.setLineWrap(true);
		this.domain.setWrapStyleWord(true);
		this.domainCalculus.add(drcbuttons, "North");
		this.domainCalculus.add(this.domain, "Center");
		this.domainCalculus.add(this.dcexec, "South");
		this.tabbedPane.addTab("Domain Calculus", null, this.domainCalculus, "Input and Execute Domain Calculus Queries");

		// init the result labels
		final Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
		this.sqlres = new JTextArea("The generated SQL query will show here.");
		this.sqlres.setLineWrap(true);
		this.sqlres.setWrapStyleWord(true);
		this.sqlres.setEditable(false);
		this.respanel.setPreferredSize(new Dimension(400, 400));
		this.respanel.setBorder(border);
		this.respanel.add(this.sqlres, "Center");
		this.add(this.respanel, "East");
		final String[] column = {"Execute Result"};
		final String[][] data = new String[1][1];
		data[0][0] = "Execute Result Shows Here!";
		final TableModel mode = new MyTableModel(column, data);
		this.extres = new JTable(mode);
		final JScrollPane table = new JScrollPane(this.extres);
		table.setPreferredSize(new Dimension(this.getWidth(), 100));
		this.add(table, "South");
		// add the input area
		this.add(this.tabbedPane, "Center");

		// connect to the database and initialize the data
		this.conn = MemoryDB.connect();
		MemoryDB.init(this.conn);
		this.setVisible(true);
		// revalidate();
	}

	// stop the database after using
	@Override
	public void stop(){
		try {
			this.conn.close();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paint(final Graphics g){
		g.setColor(Color.blue);
		super.paint(g);
	}

	public void actionPerformed(final ActionEvent e) {
		if(e.getSource() == this.examples){
			// set the example to the input area
			@SuppressWarnings("rawtypes")
			final
			JComboBox cb = (JComboBox)e.getSource();
			final String egType = (String)cb.getSelectedItem();
			if(egType.equalsIgnoreCase("SQL")){
				this.sqlquery.setText(Examples.sql);
			}else if(egType.equalsIgnoreCase("Relational Algebra")){
				this.algebra.setText(Examples.ra);
			}else if(egType.equalsIgnoreCase("Tuple Calculus")){
				this.tuple.setText(Examples.tc);
			}else if(egType.equalsIgnoreCase("Domain Calculus")){
				this.domain.setText(Examples.dc);
			}else if(egType.equalsIgnoreCase("Examples")){
				this.sqlquery.setText("");
				this.algebra.setText("");
				this.tuple.setText("");
				this.domain.setText("");
			}
		}else if(e.getSource() == this.selectionbutton){
			// add selection symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(selection, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.projectionbutton){
			// add projection symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(projection, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.cartesianbutton){
			// add cartesian product symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(cartesian, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.unionbutton){
			// add union symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(union, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.joinbutton){
			// add natural join symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(join, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.intersactbutton){
			// add intersection symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(intersaction, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.renamebutton){
			// add rename symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(rename, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.assignbutton){
			// add assign symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(assign, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.randbutton){
			// add and symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(and, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.rorbutton){
			// add or symbol
			final int index = this.algebra.getCaretPosition();
			this.algebra.insert(or, index);
			this.algebra.setCaretPosition(index + 1);
			this.algebra.revalidate();
		}else if(e.getSource() == this.texistbutton){
			// add exist symbol
			final int index = this.tuple.getCaretPosition();
			this.tuple.insert(exist, index);
			this.tuple.setCaretPosition(index + 1);
			this.tuple.revalidate();
		}else if(e.getSource() == this.tforallbutton){
			// add for all symbol
			final int index = this.tuple.getCaretPosition();
			this.tuple.insert(forall, index);
			this.tuple.setCaretPosition(index + 1);
			this.tuple.revalidate();
		}else if(e.getSource() == this.tnotbutton){
			// add not symbol
			final int index = this.tuple.getCaretPosition();
			this.tuple.insert(not, index);
			this.tuple.setCaretPosition(index + 1);
			this.tuple.revalidate();
		}else if(e.getSource() == this.tandbutton){
			// add and symbol
			final int index = this.tuple.getCaretPosition();
			this.tuple.insert(and, index);
			this.tuple.setCaretPosition(index + 1);
			this.tuple.revalidate();
		}else if(e.getSource() == this.torbutton){
			// add or symbol
			final int index = this.tuple.getCaretPosition();
			this.tuple.insert(or, index);
			this.tuple.setCaretPosition(index + 1);
			this.tuple.revalidate();
		}else if(e.getSource() == this.dexistbutton){
			// add exist symbol
			final int index = this.domain.getCaretPosition();
			this.domain.insert(exist, index);
			this.domain.setCaretPosition(index + 1);
			this.domain.revalidate();
		}else if(e.getSource() == this.dforallbutton){
			// add for all symbol
			final int index = this.domain.getCaretPosition();
			this.domain.insert(forall, index);
			this.domain.setCaretPosition(index + 1);
			this.domain.revalidate();
		}else if(e.getSource() == this.dnotbutton){
			// add not symbol
			final int index = this.domain.getCaretPosition();
			this.domain.insert(not, index);
			this.domain.setCaretPosition(index + 1);
			this.domain.revalidate();
		}else if(e.getSource() == this.dandbutton){
			// add and symbol
			final int index = this.domain.getCaretPosition();
			this.domain.insert(and, index);
			this.domain.setCaretPosition(index + 1);
			this.domain.revalidate();
		}else if(e.getSource() == this.dorbutton){
			// add or symbol
			final int index = this.domain.getCaretPosition();
			this.domain.insert(or, index);
			this.domain.setCaretPosition(index + 1);
			this.domain.revalidate();
		}else if(e.getSource() == this.sqlexec){
			try {
				final String sql = this.sqlquery.getText();
				final Statement statement = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = null;

				// execute the query
				if(sql.startsWith("insert") || sql.startsWith("create") || sql.startsWith("INSERT") || sql.startsWith("CREATE")) {
					statement.execute(sql);
				} else if(sql.startsWith("update") || sql.startsWith("UPDATE")) {
					statement.executeUpdate(sql);
				} else {
					res = statement.executeQuery(sql);
				}

				// show the result
				// get the result from the result set
				if(res != null){
					// get the row number
					res.last();
					final int rowcount = res.getRow();
					res.beforeFirst();
					final ResultSetMetaData rsmd = res.getMetaData();
					final int colcount = rsmd.getColumnCount();

					// create the string arrays to store the content and column title
					final String[][] content = new String[rowcount][colcount];
					final String[] title = new String[colcount];

					// get the results of each row
					String result = "";
					int row = 0;
					while(res != null && res.next()){
						for(int i = 1; i <= colcount; i++){
							result = result + rsmd.getColumnName(i) +": " + res.getObject(i) + "  ";
							content[row][i-1] = res.getObject(i).toString();
							title[i-1] = rsmd.getColumnName(i);
						}
						result += "\r\n";
						row++;
					}
					// set the result to the table
					final TableModel mode = new MyTableModel(title, content);
					this.extres.setModel(mode);
					System.out.println(result);
					res = null;
				}
				else{
					System.out.println("Execute successfully!");
					final String[] column = {"Execute Result"};
					final String[][] data = new String[1][1];
					data[0][0] = "Execute successfully!";
					final TableModel mode = new MyTableModel(column, data);
					this.extres.setModel(mode);
				}
				System.out.println("Execute successfully!");
				this.sqlres.setText("The generated SQL query will show here.");
				this.revalidate();
			} catch (final SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.sqlres.setText("Wrong syntax! " + e1.getMessage());
				this.revalidate();
			} catch (final Throwable e1) {
				e1.printStackTrace();
				this.sqlres.setText(e1.getMessage());
			}

		}else if(e.getSource() == this.raexec){
			try {
				final String ra = this.algebra.getText();
				String result = "";
				final Statement statement = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = null;

				// get the parse result
				final RelationalAlgebraParser rap = new RelationalAlgebraParser(ra);
				rap.genRATree();
				final String sql = rap.toSQL();
				System.out.println(sql);
				this.sqlres.setText(sql.split("\\|")[0]);

				// execute the query
				if(sql.contains("|")){
					// assign query
					final String[] assign = sql.split("\\|");
					result = AssignProcess.assignProcess(this.conn, assign[0], assign[1]);
				}else{
					if(sql.startsWith("insert") || sql.startsWith("create") || sql.startsWith("INSERT") || sql.startsWith("CREATE")) {
						statement.execute(sql);
					} else if(sql.startsWith("update") || sql.startsWith("UPDATE")) {
						statement.executeUpdate(sql);
					} else {
						res = statement.executeQuery(sql);
					}
				}

				// show the result
				// get the result from the result set
				if(res != null){
					// get the row number
					res.last();
					final int rowcount = res.getRow();
					res.beforeFirst();
					final ResultSetMetaData rsmd = res.getMetaData();
					final int colcount = rsmd.getColumnCount();

					// create the string arrays to store the content and column title
					final String[][] content = new String[rowcount][colcount];
					final String[] title = new String[colcount];

					// get the results of each row
					int row = 0;
					while(res != null && res.next()){
						for(int i = 1; i <= colcount; i++){
							result = result + rsmd.getColumnName(i) +": " + res.getObject(i);
							content[row][i-1] = res.getObject(i).toString();
							title[i-1] = rsmd.getColumnName(i);
						}
						result += "\r\n";
						row++;
					}
					// set the result to the table
					final TableModel mode = new MyTableModel(title, content);
					this.extres.setModel(mode);
					System.out.println(result);
					res = null;
				}
				else{
					System.out.println("Execute successfully!");
					final String[] column = {"Execute Result"};
					final String[][] data = new String[1][1];
					if(result.equals("")) {
						result = "Execute successfully!";
					}
					data[0][0] = result;
					final TableModel mode = new MyTableModel(column, data);
					this.extres.setModel(mode);
				}
				System.out.println("Execute successfully!");
				this.revalidate();
			} catch (final SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				final String[] column = {"Execute Result"};
				final String[][] data = new String[1][1];
				data[0][0] = "The genereated SQL query is invalid! " + e1.getMessage();
				final TableModel mode = new MyTableModel(column, data);
				this.extres.setModel(mode);
				this.revalidate();
			} catch (final Throwable e1){
				e1.printStackTrace();
				this.sqlres.setText("The input relational algebra is invalid! " + e1.getMessage());
				this.revalidate();
			}
		}else if(e.getSource() == this.tcexec){
			try {
				final String tc = this.tuple.getText();
				final Statement statement = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = null;

				// parse the tuple calculus into sql
				final TupleTreeParser ttp = new TupleTreeParser(tc);
				ttp.genTupleTree();
				ttp.genVariableList(this.conn);
				final String sql = ttp.toSQL();
				this.sqlres.setText(sql);
				System.out.println(sql);

				// execute the query
				if(sql.startsWith("insert") || sql.startsWith("create") || sql.startsWith("INSERT") || sql.startsWith("CREATE")) {
					statement.execute(sql);
				} else if(sql.startsWith("update") || sql.startsWith("UPDATE")) {
					statement.executeUpdate(sql);
				} else {
					res = statement.executeQuery(sql);
				}

				// show the result
				// get the result from the result set
				if(res != null){
					// get the row number
					res.last();
					final int rowcount = res.getRow();
					res.beforeFirst();
					final ResultSetMetaData rsmd = res.getMetaData();
					final int colcount = rsmd.getColumnCount();

					// create the string arrays to store the content and column title
					final String[][] content = new String[rowcount][colcount];
					final String[] title = new String[colcount];

					// get the results of each row
					String result = "";
					int row = 0;
					while(res != null && res.next()){
						for(int i = 1; i <= colcount; i++){
							result = result + rsmd.getColumnName(i) +": " + res.getObject(i);
							content[row][i-1] = res.getObject(i).toString();
							title[i-1] = rsmd.getColumnName(i);
						}
						result += "\r\n";
						row++;
					}
					// set the result to the table
					final TableModel mode = new MyTableModel(title, content);
					this.extres.setModel(mode);
					System.out.println(result);
					res = null;
				}
				else{
					System.out.println("Execute successfully!");
					final String[] column = {"Execute Result"};
					final String[][] data = new String[1][1];
					data[0][0] = "Execute successfully!";
					final TableModel mode = new MyTableModel(column, data);
					this.extres.setModel(mode);
				}
				System.out.println("Execute successfully!");
				this.revalidate();
			} catch (final SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				final String[] column = {"Execute Result"};
				final String[][] data = new String[1][1];
				data[0][0] = "The genereated SQL query is invalid! " + e1.getMessage();
				final TableModel mode = new MyTableModel(column, data);
				this.extres.setModel(mode);
				this.revalidate();
			} catch (final UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.sqlres.setText("The encoding is not supported! " + e1.getMessage());
				this.revalidate();
			} catch (final ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.sqlres.setText("The input is not a valid tuple calculus expression! " + e1.getMessage());
				this.revalidate();
			} catch (final Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.sqlres.setText("The input is not a valid tuple calculus expression! " + e1.getMessage());
				this.revalidate();
			}
		}else if(e.getSource() == this.dcexec){
			try {
				final String dc = this.domain.getText();
				final Statement statement = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = null;

				// parse the tuple calculus into sql
				final DomainTreeParser dtp = new DomainTreeParser(dc);
				dtp.genDomainTree();
				dtp.genVariableList(this.conn);
				final String sql = dtp.toSQL();
				this.sqlres.setText(sql);
				System.out.println(sql);

				// execute the query
				if(sql.startsWith("insert") || sql.startsWith("create") || sql.startsWith("INSERT") || sql.startsWith("CREATE")) {
					statement.execute(sql);
				} else if(sql.startsWith("update") || sql.startsWith("UPDATE")) {
					statement.executeUpdate(sql);
				} else {
					res = statement.executeQuery(sql);
				}

				// show the result
				// get the result from the result set
				if(res != null){
					// get the row number
					res.last();
					final int rowcount = res.getRow();
					res.beforeFirst();
					final ResultSetMetaData rsmd = res.getMetaData();
					final int colcount = rsmd.getColumnCount();

					// create the string arrays to store the content and column title
					final String[][] content = new String[rowcount][colcount];
					final String[] title = new String[colcount];

					// get the results of each row
					String result = "";
					int row = 0;
					while(res != null && res.next()){
						for(int i = 1; i <= colcount; i++){
							result = result + rsmd.getColumnName(i) +": " + res.getObject(i) + "  ";
							content[row][i-1] = res.getObject(i).toString();
							title[i-1] = rsmd.getColumnName(i);
						}
						result += "\r\n";
						row++;
					}
					// set the result to the table
					final TableModel mode = new MyTableModel(title, content);
					this.extres.setModel(mode);
					System.out.println(result);
					res = null;
				}
				else{
					System.out.println("Execute successfully!");
					final String[] column = {"Execute Result"};
					final String[][] data = new String[1][1];
					data[0][0] = "Execute successfully!";
					final TableModel mode = new MyTableModel(column, data);
					this.extres.setModel(mode);
				}
				System.out.println("Execute successfully!");
				this.revalidate();
			} catch (final SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				final String[] column = {"Execute Result"};
				final String[][] data = new String[1][1];
				data[0][0] = "The genereated SQL query is invalid! " + e1.getMessage();
				final TableModel mode = new MyTableModel(column, data);
				this.extres.setModel(mode);
				this.revalidate();
			} catch (final UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.sqlres.setText("The encoding is not supported! " + e1.getMessage());
				this.revalidate();
			} catch (final drctree.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.sqlres.setText("The input is not a valid domain calculus expression! " + e1.getMessage());
				this.revalidate();
			} catch (final Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.sqlres.setText("The input is not a valid domain calculus expression! " + e1.getMessage());
				this.revalidate();
			}
		}
	}

	// These are the methods for key listener
	public void keyTyped(final KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(final KeyEvent e) {
		// TODO Auto-generated method stub
		// all the shortcuts are in the form of "Ctl+Alt+Letter"
		if(e.isAltDown() && e.isControlDown()){
			// relational algebra shortcuts
			if(this.tabbedPane.getTitleAt(this.tabbedPane.getSelectedIndex()).equalsIgnoreCase("Relational Algebra")){
				// projection shortcut
				if(e.getKeyCode() == KeyEvent.VK_P){
					// add projection symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(projection, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// selection shortcut
				else if(e.getKeyCode() == KeyEvent.VK_S){
					// add selection symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(selection, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// cartesian shortcut
				else if(e.getKeyCode() == KeyEvent.VK_C){
					// add cartesian product symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(cartesian, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// union shortcut
				else if(e.getKeyCode() == KeyEvent.VK_U){
					// add union symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(union, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// join shortcut
				else if(e.getKeyCode() == KeyEvent.VK_J){
					// add natural join symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(join, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// intersect shortcut
				else if(e.getKeyCode() == KeyEvent.VK_I){
					// add intersection symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(intersaction, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// rename shortcut
				else if(e.getKeyCode() == KeyEvent.VK_R){
					// add rename symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(rename, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// assign shortcut
				else if(e.getKeyCode() == KeyEvent.VK_G){
					// add assign symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(assign, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// and shortcut
				else if(e.getKeyCode() == KeyEvent.VK_A){
					// add and symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(and, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
				// or shortcut
				else if(e.getKeyCode() == KeyEvent.VK_O){
					// add or symbol
					final int index = this.algebra.getCaretPosition();
					this.algebra.insert(or, index);
					this.algebra.setCaretPosition(index + 1);
					this.algebra.revalidate();
				}
			}
			// tuple calculus shortcuts
			else if(this.tabbedPane.getTitleAt(this.tabbedPane.getSelectedIndex()).equalsIgnoreCase("Tuple Calculus")){
				// exist shortcut
				if(e.getKeyCode() == KeyEvent.VK_E){
					// add exist symbol
					final int index = this.tuple.getCaretPosition();
					this.tuple.insert(exist, index);
					this.tuple.setCaretPosition(index + 1);
					this.tuple.revalidate();
				}
				// for all shortcut
				else if(e.getKeyCode() == KeyEvent.VK_F){
					// add for all symbol
					final int index = this.tuple.getCaretPosition();
					this.tuple.insert(forall, index);
					this.tuple.setCaretPosition(index + 1);
					this.tuple.revalidate();
				}
				// not shortcut
				else if(e.getKeyCode() == KeyEvent.VK_N){
					// add not symbol
					final int index = this.tuple.getCaretPosition();
					this.tuple.insert(not, index);
					this.tuple.setCaretPosition(index + 1);
					this.tuple.revalidate();
				}
				// and shortcut
				else if(e.getKeyCode() == KeyEvent.VK_A){
					// add and symbol
					final int index = this.tuple.getCaretPosition();
					this.tuple.insert(and, index);
					this.tuple.setCaretPosition(index + 1);
					this.tuple.revalidate();
				}
				// or shortcut
				else if(e.getKeyCode() == KeyEvent.VK_O){
					// add or symbol
					final int index = this.tuple.getCaretPosition();
					this.tuple.insert(or, index);
					this.tuple.setCaretPosition(index + 1);
					this.tuple.revalidate();
				}
			}
			// domain calculus shortcuts
			else if(this.tabbedPane.getTitleAt(this.tabbedPane.getSelectedIndex()).equalsIgnoreCase("Domain Calculus")){
				if(e.getKeyCode() == KeyEvent.VK_E){
					// add exist symbol
					final int index = this.domain.getCaretPosition();
					this.domain.insert(exist, index);
					this.domain.setCaretPosition(index + 1);
					this.domain.revalidate();
				}else if(e.getKeyCode() == KeyEvent.VK_F){
					// add for all symbol
					final int index = this.domain.getCaretPosition();
					this.domain.insert(forall, index);
					this.domain.setCaretPosition(index + 1);
					this.domain.revalidate();
				}else if(e.getKeyCode() == KeyEvent.VK_N){
					// add not symbol
					final int index = this.domain.getCaretPosition();
					this.domain.insert(not, index);
					this.domain.setCaretPosition(index + 1);
					this.domain.revalidate();
				}else if(e.getKeyCode() == KeyEvent.VK_A){
					// add and symbol
					final int index = this.domain.getCaretPosition();
					this.domain.insert(and, index);
					this.domain.setCaretPosition(index + 1);
					this.domain.revalidate();
				}else if(e.getKeyCode() == KeyEvent.VK_O){
					// add or symbol
					final int index = this.domain.getCaretPosition();
					this.domain.insert(or, index);
					this.domain.setCaretPosition(index + 1);
					this.domain.revalidate();
				}
			}
		}
	}

	public void keyReleased(final KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
