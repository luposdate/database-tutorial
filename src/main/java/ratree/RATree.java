/* Generated By:JJTree&JavaCC: Do not edit this line. RATree.java */
package ratree;

public class RATree/*@bgen(jjtree)*/implements RATreeTreeConstants, RATreeConstants {/*@bgen(jjtree)*/
  protected JJTRATreeState jjtree = new JJTRATreeState();public static void main(String args [])
  {
    System.out.println("Please input a relational algebra expression: ");
    RATree ra = new RATree(System.in);
    try
    {
      SimpleNode n = ra.Start();
      n.dump("");
      System.out.println("Thank you.");
    }
    catch(ParseException e)
    {
      System.out.println("THE INPUT IS NOT A RELATIONAL ALGEBRA EXPRESSION!");
      System.out.println(e.getMessage());
    }
    catch(TokenMgrError e)
    {
      System.out.println("UNRECOGNISABLE TOKEN!");
      System.out.println(e.getMessage());
    }
  }

/**
* The JavaCC grammar for Relational Algebra expressions
* Used to generate a JavaCC parse tree.
*/
  final public SimpleNode Start() throws ParseException {
 /*@bgen(jjtree) Start */
  SimpleNode jjtn000 = new SimpleNode(JJTSTART);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      expression();
      jj_consume_token(22);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public int one_line() throws ParseException {
 /*@bgen(jjtree) one_line */
  SimpleNode jjtn000 = new SimpleNode(JJTONE_LINE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROJECT:
      case SELECT:
      case RENAME:
      case ASSIGN:
      case WORD:
        expression();
        jj_consume_token(22);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return 0;}
        break;
      case 22:
        jj_consume_token(22);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return 1;}
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// the relational algebra expressions
// they can be just relations, or projection, selection, set, rename or assignment expressions  final public String expression() throws ParseException {
 /*@bgen(jjtree) EXPR */
 SimpleNode jjtn000 = new SimpleNode(JJTEXPR);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);String s; String expr = "";
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case WORD:
        expr = relation();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ASSIGN:
          assignexpr();
          break;
        default:
          jj_la1[1] = jj_gen;
          ;
        }
        break;
      case PROJECT:
        expr = projectexpr();
        break;
      case SELECT:
        expr = selectexpr();
        break;
      case RENAME:
        expr = renameexpr();
        break;
      case ASSIGN:
        expr = assignexpr();
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
 jjtn000.value = expr;
      label_1:
      while (true) {
        if (jj_2_1(2)) {
          ;
        } else {
          break label_1;
        }
        s = setop();
                  expr += s;
        s = expression();
                       expr += s;
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return expr;}
    } catch (Throwable jjte000) {
  if (jjtc000) {
    jjtree.clearNodeScope(jjtn000);
    jjtc000 = false;
  } else {
    jjtree.popNode();
  }
  if (jjte000 instanceof RuntimeException) {
    {if (true) throw (RuntimeException)jjte000;}
  }
  if (jjte000 instanceof ParseException) {
    {if (true) throw (ParseException)jjte000;}
  }
  {if (true) throw (Error)jjte000;}
    } finally {
  if (jjtc000) {
    jjtree.closeNodeScope(jjtn000, true);
  }
    }
    throw new Error("Missing return statement in function");
  }

// assignment expressions. Assign the result of a relational algebra to another name
  final public String assignexpr() throws ParseException {
 /*@bgen(jjtree) ASSIGN */
 SimpleNode jjtn000 = new SimpleNode(JJTASSIGN);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String assign = "";
    try {
      t = jj_consume_token(ASSIGN);
                   assign += t.image.toString();
      s = expression();
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    assign += s; jjtn000.value = assign; {if (true) return assign;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// the operators for the set expressions
  final public String setop() throws ParseException {
 /*@bgen(jjtree) SETOP */
 SimpleNode jjtn000 = new SimpleNode(JJTSETOP);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String set = "";
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOIN:
        t = jj_consume_token(JOIN);
                      set += t.image.toString();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 23:
          jj_consume_token(23);
          s = conditions();
          jj_consume_token(24);
                                 set += "{" + s + "}";
          break;
        default:
          jj_la1[3] = jj_gen;
          ;
        }
        break;
      case CARTESIAN:
        t = jj_consume_token(CARTESIAN);
                          set += t.image.toString();
        break;
      case DIFFERENCE:
        t = jj_consume_token(DIFFERENCE);
                           set += t.image.toString();
        break;
      case UNION:
        t = jj_consume_token(UNION);
                      set += t.image.toString();
        break;
      case INTERSECT:
        t = jj_consume_token(INTERSECT);
                          set += t.image.toString();
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    jjtn000.value = set; {if (true) return set;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// rename expression. Rename a relation, projection or selection expression
  final public String renameexpr() throws ParseException {
 /*@bgen(jjtree) RENAME */
 SimpleNode jjtn000 = new SimpleNode(JJTRENAME);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String rename = "";
    try {
      t = jj_consume_token(RENAME);
                   rename += t.image.toString();
      t = jj_consume_token(WORD);
                  rename += t.image.toString();
      jj_consume_token(25);
      s = expression();
      jj_consume_token(26);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    rename += "(" + s + ")"; jjtn000.value = rename; {if (true) return rename;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// selection expression
  final public String selectexpr() throws ParseException {
 /*@bgen(jjtree) SEL */
 SimpleNode jjtn000 = new SimpleNode(JJTSEL);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String sel = "";
    try {
      t = jj_consume_token(SELECT);
                   sel += t.image.toString();
      s = conditions();
                     sel += s;
      jj_consume_token(25);
      s = expression();
      jj_consume_token(26);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    sel += "(" + s + ")"; jjtn000.value = sel; {if (true) return sel;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// project expression
  final public String projectexpr() throws ParseException {
 /*@bgen(jjtree) PROJ */
 SimpleNode jjtn000 = new SimpleNode(JJTPROJ);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String proj = "";
    try {
      t = jj_consume_token(PROJECT);
                    proj += t.image.toString();
      s = attributelist();
                        proj += s;
      jj_consume_token(25);
      s = expression();
      jj_consume_token(26);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    proj += "(" + s + ")"; jjtn000.value = proj; {if (true) return proj;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// the relations
  final public String relation() throws ParseException {
 /*@bgen(jjtree) RELA */
 SimpleNode jjtn000 = new SimpleNode(JJTRELA);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String rela = "";
    try {
      t = jj_consume_token(WORD);
                 rela += t.image.toString();
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_2;
        }
        t = jj_consume_token(NUMBER);
                    rela += t.image.toString();
      }
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case WORD:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_3;
        }
        t = jj_consume_token(WORD);
                  rela += t.image.toString();
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    jjtn000.value = rela; {if (true) return rela;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// the attributes
  final public String attribute() throws ParseException {
 /*@bgen(jjtree) ATTR */
 SimpleNode jjtn000 = new SimpleNode(JJTATTR);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s = "";
    try {
      t = jj_consume_token(WORD);
                  s += t.image.toString();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUMBER:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_4;
        }
        t = jj_consume_token(NUMBER);
                      s += t.image.toString();
      }
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case WORD:
          ;
          break;
        default:
          jj_la1[8] = jj_gen;
          break label_5;
        }
        t = jj_consume_token(WORD);
                    s += t.image.toString();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 27:
        jj_consume_token(27);
        t = jj_consume_token(WORD);
                     s += "." + t.image.toString();
        label_6:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case NUMBER:
            ;
            break;
          default:
            jj_la1[9] = jj_gen;
            break label_6;
          }
          t = jj_consume_token(NUMBER);
                      s += t.image.toString();
        }
        label_7:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case WORD:
            ;
            break;
          default:
            jj_la1[10] = jj_gen;
            break label_7;
          }
          t = jj_consume_token(WORD);
                    s += t.image.toString();
        }
        break;
      default:
        jj_la1[11] = jj_gen;
        ;
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    jjtn000.value = s; {if (true) return s;}
    } finally {
  if (jjtc000) {
    jjtree.closeNodeScope(jjtn000, true);
  }
    }
    throw new Error("Missing return statement in function");
  }

// a list of attributes, used in projectiong expressions
  final public String attributelist() throws ParseException {
 /*@bgen(jjtree) ATTRLIST */
 SimpleNode jjtn000 = new SimpleNode(JJTATTRLIST);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String attrlist = "";
    try {
      s = attribute();
                    attrlist += s;
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 28:
          ;
          break;
        default:
          jj_la1[12] = jj_gen;
          break label_8;
        }
        jj_consume_token(28);
          attrlist += ",";
        s = attribute();
                      attrlist += s;
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    jjtn000.value = attrlist; {if (true) return attrlist;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// operands used in conditions
  final public String operand() throws ParseException {
 /*@bgen(jjtree) OPER */
  SimpleNode jjtn000 = new SimpleNode(JJTOPER);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t; String s = "";
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case WORD:
        s = attribute();
        break;
      case CONSTRAIN:
        t = jj_consume_token(CONSTRAIN);
                        s += t.image.toString();
        break;
      case NUMBER:
        t = jj_consume_token(NUMBER);
                     s += t.image.toString();
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    jjtn000.value = s; {if (true) return s;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// one simple condition used in the conditions
  final public String condition() throws ParseException {
 /*@bgen(jjtree) COND */
 SimpleNode jjtn000 = new SimpleNode(JJTCOND);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String s; String cond = "";
    try {
      cond = operand();
      t = jj_consume_token(COMPARE);
                    cond += t.image.toString();
      s = operand();
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    cond += s; jjtn000.value = cond; {if (true) return cond;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// a list of simple conditions used in selection expression
  final public String conditions() throws ParseException {
 /*@bgen(jjtree) CONDS */
  SimpleNode jjtn000 = new SimpleNode(JJTCONDS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t; String s; String conds = "";
    try {
      s = condition();
                   conds += s;
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
        case OR:
          ;
          break;
        default:
          jj_la1[14] = jj_gen;
          break label_9;
        }
        s = andor();
               conds += s;
        s = condition();
                   conds += s;
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
   jjtn000.value = conds; {if (true) return conds;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

// AND or OR operands connect the conditions
  final public String andor() throws ParseException {
 /*@bgen(jjtree) ANDOR */
 SimpleNode jjtn000 = new SimpleNode(JJTANDOR);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t; String andor = "";
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        t = jj_consume_token(AND);
        break;
      case OR:
        t = jj_consume_token(OR);
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
   andor = t.image.toString(); jjtn000.value = andor; {if (true) return andor;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3R_27() {
    if (jj_scan_token(ASSIGN)) return true;
    return false;
  }

  private boolean jj_3R_24() {
    if (jj_scan_token(PROJECT)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_10()) return true;
    if (jj_3R_11()) return true;
    return false;
  }

  private boolean jj_3R_25() {
    if (jj_scan_token(SELECT)) return true;
    return false;
  }

  private boolean jj_3R_21() {
    if (jj_3R_27()) return true;
    return false;
  }

  private boolean jj_3R_20() {
    if (jj_3R_26()) return true;
    return false;
  }

  private boolean jj_3R_19() {
    if (jj_3R_25()) return true;
    return false;
  }

  private boolean jj_3R_18() {
    if (jj_3R_24()) return true;
    return false;
  }

  private boolean jj_3R_26() {
    if (jj_scan_token(RENAME)) return true;
    return false;
  }

  private boolean jj_3R_17() {
    if (jj_3R_23()) return true;
    return false;
  }

  private boolean jj_3R_11() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_17()) {
    jj_scanpos = xsp;
    if (jj_3R_18()) {
    jj_scanpos = xsp;
    if (jj_3R_19()) {
    jj_scanpos = xsp;
    if (jj_3R_20()) {
    jj_scanpos = xsp;
    if (jj_3R_21()) return true;
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3R_16() {
    if (jj_scan_token(INTERSECT)) return true;
    return false;
  }

  private boolean jj_3R_15() {
    if (jj_scan_token(UNION)) return true;
    return false;
  }

  private boolean jj_3R_14() {
    if (jj_scan_token(DIFFERENCE)) return true;
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_scan_token(CARTESIAN)) return true;
    return false;
  }

  private boolean jj_3R_22() {
    if (jj_scan_token(23)) return true;
    return false;
  }

  private boolean jj_3R_12() {
    if (jj_scan_token(JOIN)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_22()) jj_scanpos = xsp;
    return false;
  }

  private boolean jj_3R_10() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_12()) {
    jj_scanpos = xsp;
    if (jj_3R_13()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) return true;
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3R_23() {
    if (jj_scan_token(WORD)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public RATreeTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x442260,0x2000,0x42260,0x800000,0x1d80,0x20000,0x40000,0x20000,0x40000,0x20000,0x40000,0x8000000,0x10000000,0xe0000,0x18000,0x18000,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public RATree(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public RATree(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new RATreeTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public RATree(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new RATreeTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public RATree(RATreeTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(RATreeTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[29];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 29; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
