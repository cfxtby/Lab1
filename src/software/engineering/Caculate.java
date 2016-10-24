package software.engineering;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*hello*/
/**
 * {@author you}
 * */

public final class Caculate {
  private Caculate(){
    
  }
  
  /**
   * {@author you}.
   * */
  public static void main(final String[] args) {


    final long startMili = System.currentTimeMillis();
    System.out.println("执行开始时间：" + startMili);

    Scanner sc1 = new Scanner(System.in);

    String express = null;
    String express1 = null;
    String expressTemp = null;
    String command = null;
    String commandRe = null;
    final String compareString = "1";
    Expression exp;
    Expression exp1;

    Matcher matcher;
    Pattern expressionRegex = Pattern
        .compile("^((([a-z]|[0-9]+)\\*)*([a-z]|[0-9]+)\\+)*(([a-z]|[0-9]+)\\*)*([a-z]|[0-9]+)$");
    while (true) {
      // System.out.println("Expression_Input:(end with illeagal input)");

      // express1 = express.trim();

      express = sc1.nextLine();

      matcher = expressionRegex.matcher(express);
      if (matcher.matches()) {
        // System.out.println("MATCHES");
        exp = new Expression(express);
        express1 = exp.printExpression();
        System.out.println(express1);

        while (true) {

          // System.out.println("Command_Input:(end with illeagal
          // input)");

          command = sc1.nextLine();
          commandRe = exp.command(command);
          boolean flagb=commandRe.equals(compareString);
          if ( flagb ) {
            for (int i = 10; i + 2 <= command.trim().length();) {
              // System.out.println(String.valueOf(command.charAt(i)));
              // System.out.println(String.valueOf(command.charAt(i+2)));
              expressTemp = express1.replaceAll(String.valueOf(command.charAt(i)),
                  String.valueOf(command.charAt(i + 2)));
              express1 = expressTemp;
              i += 4;
              // System.out.println(i);
            }
            // System.out.println(express1);
            exp1 = new Expression(express1);
            System.out.println(exp1.printExpression()); 
            express1 = exp.printExpression();// restore!!!
          } else if (commandRe.equals("0")) {
            if (exp.count(express1, String.valueOf(command.charAt(5))) == 0) {
              System.out.println("Error, no variable");
            } else {
              exp1 = new Expression(commandRe);
              System.out.println(exp1.printExpression());
            }

          } else {
            break;
          }
        }

      } else {
        sc1.close();

        final long endMili = System.currentTimeMillis();
        System.out.println("结束时间：" + endMili);
        System.out.println("执行总时间：" + (endMili - startMili) + "毫秒");

        return;
      }

    }
  }

}
