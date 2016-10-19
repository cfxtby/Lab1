package software_engineering;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;




class Var implements Comparable<Var>{
	String name;//The name of the Variable
	int power;//The power of the Variable
	
	/*compareTo
	 * The method inherit from the interface "Comparable"
	 * Compare the <Var> type by name of the variable
	 * */
	public int compareTo(Var x){
		return Character.compare(this.name.charAt(0),x.name.charAt(0));
	}
	
	/*stringFormat
	 * return the string format of the variable
	 * example: x on power of 4 => x*x*x*x
	 * */
	String stringFormat(){
		StringBuffer x = new StringBuffer();
		for(int i=0;i < this.power;i++){
			x.append(name);
			x.append("*");
		}
		x.deleteCharAt(x.length()-1);
		return x.toString();
	}
	
	/*Constructor
	 * constructs a new variable of the specified name and power
	 * */
	Var(String name,int power){
		this.name = name;
		this.power = power;
	}
	
	
}




class Term {
	int coefficient;
	Vector<Var> vars;
	
	/*stringFormat
	 * return the stringFormat of the term
	 * */
	String stringFormat(){
		StringBuffer x = new StringBuffer();
		if(coefficient!=1){
		x.append(coefficient);
		x.append("*");
		}
		for(Var v: vars){
			x.append(v.stringFormat());
			x.append("*");
		}
		x.deleteCharAt(x.length()-1);
		return x.toString();
	}
	
	/*Find
	 * a method that finds the index of the variable with the specified name
	 * if no such variable was found,return -1
	 * */
	int Find(String var){
		int i = 0;
		for(Var v: vars){
			if(var.equals(v.name)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/*simplify
	 * a method that simplifies the term by standard format
	 * */
	void simplify(){
		for(int i = 0;i < vars.size();){//去除power为0的变量
			if(vars.get(i).power == 0){
				vars.remove(i);
			}else{
				i++;
			}
		}
		vars.sort(null);
	}
	
	/*Constructor
	 * constructs a term with the specified string
	 * */
	Term(String content){
		this.coefficient = 1;
		this.vars = new Vector<Var>();
		String vars[] = content.split("\\*");
		int index;
		for(String v : vars){
			if(Character.isDigit(v.charAt(0))){
				this.coefficient *= Integer.parseInt(v);
			}else{
				index = this.Find(v);
				if(index == -1){
					this.vars.add(new Var(v,1));
				}else{
					this.vars.get(index).power++;
				}
			}
		}
	}
	
	
	
}





class Expression{
	int coefficientTerm;
	Vector<Term> terms;
	
	/*simplify
	 * the method that simplifies the expression by standard format
	 * remember to use it after change the content of the expression
	 * */
	void simplify(){
		for (int i =0;i<terms.size();) {
			if(terms.get(i).vars.isEmpty()){
				coefficientTerm += terms.get(i).coefficient;
				terms.remove(i);
			}else{
				if(terms.get(i).coefficient == 0){
					terms.remove(i);
				}else{
					terms.get(i).simplify();
					i++;
				}
			}
		}
	}
	/*Merge
	 * the method that simplifies the expression by standard format
	 * remember to use it after change the content of the expression
	 * */
	public static String Merge(String src){
		Vector<String> group = new Vector<String>(Arrays.asList(src.split("\\+")));
		StringBuffer group2 = new StringBuffer();
		for(int i = 0;i<group.size();){
			String term = group.get(i);
			String vars = term.replaceAll("^[0-9]*\\*", "");
			int coefficient;
			if(term.length() == vars.length()){
				coefficient = 1;
			}else{
				coefficient = Integer.parseInt(term.substring(0, term.indexOf(vars)-1));
			}
			for(int j = i+1;j<group.size();){
				String mergeterm = group.get(j);
				if(mergeterm.matches("([0-9]*\\*)?\\Q"+vars+"\\E")){
					coefficient += mergeterm.length()==vars.length()?1:Integer.parseInt(mergeterm.substring(0,mergeterm.indexOf(vars)-1));
					group.remove(j);
				}else{
					j++;
				}
			}
			if(coefficient == 1){
				group2.append(vars+"+");
			}else{
			group2.append(Integer.toString(coefficient)+"*"+vars+"+");
			}
			i++;
		}
		group2.deleteCharAt(group2.length()-1);
		return group2.toString();
	}
	
	
	
	/*PrintExpression
	 * return the string format of the expression
	 * */
	String PrintExpression(){
		StringBuffer exp = new StringBuffer();
		
		if(this.coefficientTerm != 0){
			exp.append(this.coefficientTerm);
			exp.append("+");
		}else{
			if(terms.size() == 0){
				return "0";
			}
		}
		for(Term t : terms){
			exp.append(t.stringFormat());
			exp.append("+");
		}
		
		exp.deleteCharAt(exp.length()-1);
		
		
		return Merge(exp.toString());
	}
	
	
	/*Constructor
	 * constructs a new expression with the specified string
	 * after the construction,the expression will be simplified
	 * */
	Expression(String content){
		coefficientTerm = 0;
		this.terms = new Vector<Term>();
		Matcher matcher;
		String termgroup;
		Pattern pattern = Pattern.compile("(([a-z]|[0-9]+)\\*)*([a-z]|[0-9]+)");
		matcher = pattern.matcher(content);
		while (matcher.find()){
			termgroup = matcher.group();
			terms.add(new Term(termgroup));
		}
		this.simplify();
	}
	
	/*
	 *to deal with the command  */
	String Command(String content){
		Matcher matcher1;
		Matcher matcher2;
		Pattern pattern1 = Pattern.compile("!simplify( [a-z]=[0-9]+)*[ ]*");
		Pattern pattern2 = Pattern.compile("!d/d [a-z]");
		matcher1 = pattern1.matcher(content);
		if(matcher1.matches()){
			if(content.trim().equals("!simplify")){
				System.out.println(this.PrintExpression());
				return "0";
			}else{
				return "1";
			}		
		}
		matcher2 = pattern2.matcher(content);
		if(matcher2.matches()){
			//System.out.println("!d/d");
			return Derivative(String.valueOf(content.charAt(5)));
		}
		return "0";
		
	}
	
	/*count the number 
	 * of a part-string in a long string*/
	public int Count(String src, String find){
		int count = 0;
		int index = -1;
		while((index=src.indexOf(find, index)) > -1){
			index++;
			count++;
		}
		return count;
	}
	
	/*derivative function*/
	String Derivative(String var){
		StringBuffer exp = new StringBuffer();
		if(this.coefficientTerm != 0){
			exp.append("");
		}else{
			if(terms.size() == 0){
				return "0";
			}
		}
		for(Term t : terms){
			int count = Count(t.stringFormat(),var);
			//System.out.println(count);
			if(count != 0){
				exp.append(t.stringFormat().replaceFirst(var, String.valueOf(count)));
			}
			//else{
			//	exp.append(t.stringFormat());
			//}
			exp.append("+");
		}
		
		exp.deleteCharAt(exp.length()-1);
		//System.out.println(exp.toString());
		return exp.toString();
	}
	
	
}




public class caculate {
	
	
	public static void main(String[] args) {
		
		long startMili = System.currentTimeMillis();
		System.out.println("执行开始时间："+startMili);
		
		Scanner sc = new Scanner(System.in);
		
		String express = null;
		String express1 = null;
		String express_temp = null;
		String command = null;
		String command_re = null;
		Expression exp;
		Expression exp1;
		
		Matcher matcher;
		Pattern expressionRegex = Pattern.compile("^((([a-z]|[0-9]+)\\*)*([a-z]|[0-9]+)\\+)*(([a-z]|[0-9]+)\\*)*([a-z]|[0-9]+)$");
		while(true){
		//System.out.println("Expression_Input:(end with illeagal input)");
		
		//express1 = express.trim();
		

		express = sc.nextLine();

		matcher = expressionRegex.matcher(express);
		if(matcher.matches()){
			//System.out.println("MATCHES");
			exp = new Expression(express);
			express1 = exp.PrintExpression();
			System.out.println(express1);
			
			
			while(true){
				
			//System.out.println("Command_Input:(end with illeagal input)");
			
			command = sc.nextLine();
			command_re = exp.Command(command);
			if(command_re == "1"){
				for(int i=10;i+2 <= command.trim().length();){
					//System.out.println(String.valueOf(command.charAt(i)));
					//System.out.println(String.valueOf(command.charAt(i+2)));
					express_temp = express1.replaceAll(String.valueOf(command.charAt(i)), String.valueOf(command.charAt(i+2)));
					express1 = express_temp;
					i += 4;
					//System.out.println(i);
				}
				//System.out.println(express1);
				exp1 = new Expression(express1);
				System.out.println(exp1.PrintExpression());
				express1 = exp.PrintExpression();//restore!!!
			}else if(command_re != "0"){
				if(exp.Count(express1, String.valueOf(command.charAt(5))) == 0){
					System.out.println("Error, no variable");
				}else{
					exp1 = new Expression(command_re);
					System.out.println(exp1.PrintExpression());
				}
				
				
			}else{break;};
			}
					
		}else{
			sc.close();
			
			long endMili = System.currentTimeMillis();
			System.out.println("结束时间："+endMili);
			System.out.println("执行总时间："+(endMili-startMili)+"毫秒");
			
			return ;	
		}

		}
	}

	
}
