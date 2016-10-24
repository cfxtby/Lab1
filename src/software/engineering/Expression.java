package software.engineering;

import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
  int coefficientTerm;
  Vector<Term> terms;

  /*
   * simplify the method that simplifies the expression by standard format
   * remember to use it after change the content of the expression
   */
  void simplify() {
    for (int i = 0; i < terms.size();) {
      if (terms.get(i).vars.isEmpty()) {
        coefficientTerm += terms.get(i).coefficient;
        terms.remove(i);
      } else {
        if (terms.get(i).coefficient == 0) {
          terms.remove(i);
        } else {
          terms.get(i).simplify();
          i++;
        }
      }
    }
  }

  /**
   * {@code}.
   * */
  
  
  public static String merge(String src) {
    Vector<String> group = new Vector<String>(Arrays.asList(src.split("\\+")));
    StringBuffer group2 = new StringBuffer();
    for (int i = 0; i < group.size();) {
      String term = group.get(i);
      String vars = term.replaceAll("^[0-9]*\\*", "");
      int coefficient;
      if (term.length() == vars.length()) {
        coefficient = 1;
      } else {
        coefficient = Integer.parseInt(term.substring(0, term.indexOf(vars) - 1));
      }
      for (int j = i + 1; j < group.size();) {
        String mergeterm = group.get(j);
        if (mergeterm.matches("([0-9]*\\*)?\\Q" + vars + "\\E")) {
          coefficient += mergeterm.length() == vars.length() ? 1
              : Integer.parseInt(mergeterm.substring(0, mergeterm.indexOf(vars) - 1));
          group.remove(j);
        } else {
          j++;
        }
      }
      if (coefficient == 1) {
        group2.append(vars + "+");
      } else {
        group2.append(Integer.toString(coefficient) + "*" + vars + "+");
      }
      i++;
    }
    group2.deleteCharAt(group2.length() - 1);
    return group2.toString();
  }

  /*
   * PrintExpression return the string format of the expression
   */
  String printExpression() {
    StringBuffer exp = new StringBuffer();

    if (this.coefficientTerm != 0) {
      exp.append(this.coefficientTerm);
      exp.append("+");
    } else {
      if (terms.size() == 0) {
        return "0";
      }
    }
    for (Term t : terms) {
      exp.append(t.stringFormat());
      exp.append("+");
    }

    exp.deleteCharAt(exp.length() - 1);

    return merge(exp.toString());
  }

  /*
   * Constructor constructs a new expression with the specified string after
   * the construction,the expression will be simplified
   */
  Expression(String content) {
    coefficientTerm = 0;
    this.terms = new Vector<Term>();
    Matcher matcher;
    String termgroup;
    Pattern pattern = Pattern.compile("(([a-z]|[0-9]+)\\*)*([a-z]|[0-9]+)");
    matcher = pattern.matcher(content);
    while (matcher.find()) {
      termgroup = matcher.group();
      terms.add(new Term(termgroup));
    }
    this.simplify();
  }

  /*
   * to deal with the command
   */
  String command(String content) {
    Matcher matcher1;
    Matcher matcher2;
    Pattern pattern1 = Pattern.compile("!simplify( [a-z]=[0-9]+)*[ ]*");
    Pattern pattern2 = Pattern.compile("!d/d [a-z]");
    matcher1 = pattern1.matcher(content);
    if (matcher1.matches()) {
      if (content.trim().equals("!simplify")) {
        System.out.println(this.printExpression());
        return "0";
      } else {
        return "1";
      }
    }
    matcher2 = pattern2.matcher(content);
    if (matcher2.matches()) {
      // System.out.println("!d/d");
      return derivative(String.valueOf(content.charAt(5)));
    }
    return "0";

  }
  /*count the number of a part-string in a long string
   */
  /**
   *{@author you}.
   */

  public int count(String src, String find) {
    int count = 0;
    int index = -1;
    while ((index = src.indexOf(find, index)) > -1) {
      index++;
      count++;
    }
    return count;
  }

  /* derivative function */
  String derivative(String var) {
    StringBuffer exp = new StringBuffer();
    if (this.coefficientTerm != 0) {
      exp.append("");
    } else {
      if (terms.size() == 0) {
        return "0";
      }
    }
    for (Term t : terms) {
      int count = count(t.stringFormat(), var);
      // System.out.println(count);
      if (count != 0) {
        exp.append(t.stringFormat().replaceFirst(var, String.valueOf(count)));
      }
      // else{
      // exp.append(t.stringFormat());
      // }
      exp.append("+");
    }

    exp.deleteCharAt(exp.length() - 1);
    // System.out.println(exp.toString());
    return exp.toString();
  }

}
