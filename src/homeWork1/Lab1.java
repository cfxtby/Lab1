package homeWork1;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab1 {

	public static void main(String[] args) {//主函数主要用于调试各个部分的功能
		// TODO Auto-generated method stub
		Scanner reader=new Scanner(System.in);
		String str1=reader.nextLine();
		Lab1 l=new Lab1();
		String testString=str1.replaceAll("[\t ]", "");
		//testString=str1.replaceAll("", "");
		
		boolean flag=l.test(testString);
		System.out.println(flag);
		
		linkTable lt=l.setTable(testString);
		System.out.println(l.expression(lt));
		//
		Scanner reader1=new Scanner(System.in);
		String str2=reader.nextLine();
		//
		System.out.println(l.expression(l.derivate(lt,str2)));
		System.out.println(l.expression(lt));
		/*
		System.out.println(l.expression(l.simplify(lt,str2,2)));
		System.out.println(l.expression(lt));
		*/
	}
	
	
	String expression(linkTable lt){//遍历表，读出多项式
		String str="";
		node p=lt.head,q=p;
		boolean flag=false;
		
		while(q!=null||p!=null){
			if(q==null){
				q=p.next;
				p=p.next;
			}
			else{
				
				if(p==q){
					flag=(p.fac==1);
					if(str.equals("")&&p.fac>0)
					str+=(p.fac==1&&p.link!=null?"":""+p.fac);
					else if(p.fac>0)
						str+="+"+(p.fac==1?"":""+p.fac);
					else str+=p.fac;
				}
				else{
					str+=(flag?"":"*")+q.var+(q.exp==1?"":"^"+q.exp);
					if(flag)flag=false;
				}
				q=q.link;
			}
		}
		if(lt.head==null)str="0";
		return str;
	}
	
	linkTable setTable(String str){
		linkTable lt=new linkTable();
		int n=0;
		Pattern p1,p2,p3,p4;
		p1=Pattern.compile("[\\*+-]");
		p2=Pattern.compile("\\d+");
		p3=Pattern.compile("[a-zA-Z]+[\\^]\\d+");
		p4=Pattern.compile("[a-zA-Z]+");
		
		Matcher m1,m2,m3,m4;
		m1=p1.matcher(str);
		m2=p2.matcher(str);
		m3=p3.matcher(str);
		m4=p4.matcher(str);
		
		boolean flag1,flag2,flag3,flag4;
		flag1=m1.find(n);
		flag2=m2.find(n);
		flag3=m3.find(n);
		flag4=m4.find(n);
		
		String dealStr;node top=null;
		do{
			if(flag1&&m1.start()==n){
				dealStr=m1.group();
				if(dealStr.compareTo("+")==0){
					top=lt.insert(1);
				}
				else if(dealStr.compareTo("-")==0){
					top=lt.insert(-1);
				}
				n=m1.end();
				flag1=m1.find();
			}
			else if(flag2&&m2.start()==n){
				dealStr=m2.group();
				if(top==null){
					top=lt.insert(Integer.parseInt(dealStr));
				}
				else {
					top.fac=top.fac*Integer.parseInt(dealStr);
				}

				n=m2.end();
				flag2=m2.find();
			}
			else if(flag3&&m3.start()==n){
				dealStr=m3.group();
				if(top==null)top=lt.insert(1);
				Pattern p5=Pattern.compile("([a-zA-Z])\\1");
				Matcher m5=p5.matcher(dealStr);
				if(m5.find()){
					lt.insert(dealStr.split("^")[0], top, Integer.parseInt(dealStr.split("^")[1]));
				}
				else{
					String[] arrStr=dealStr.split("\\^");
					char[] arrChar=arrStr[0].toCharArray();
					lt.insert(arrChar[arrChar.length-1]+"",top,Integer.parseInt(dealStr.split("\\^")[1]));
					for (int i=0;i<arrChar.length-1;i++){
						lt.insert(arrChar[i]+"",top,1);
					}
				}

				n=m3.end();
				flag3=m3.find();
				flag4=m4.find(n);
				flag2=m2.find(n);
			}
			else if(flag4&&m4.start()==n){
				if(top==null)top=lt.insert(1);
				dealStr=m4.group();
				Pattern p5=Pattern.compile("([a-zA-Z])\\1");
				Matcher m5=p5.matcher(dealStr);
				if(m5.find()){
					lt.insert(dealStr, top,1);
				}
				else{
					char[] arrChar=dealStr.toCharArray();
					//lt.insert(arrChar[arrChar.length-1]+"",top,Integer.parseInt(dealStr.split("^")[1]));
					for (int i=0;i<arrChar.length;i++){
						lt.insert(arrChar[i]+"",top,1);
					}
				}

				n=m4.end();
				flag4=m4.find();
			}
			
		}while(n!=str.length());
		lt.simplify();
		return lt;
	}


	
	
	
	 boolean test(String str){//测试多项式输入是否合法
		
		if(!(str.matches("[1-9a-zA-Z[-]][[\\*\\^+-][0-9a-zA-Z]]*")))return false;//检查有无合法字符
	
		if((str.matches(".*[\\*\\^+-]")))return false;//检查末尾字符为符号情况
		Pattern p=Pattern.compile("[a-zA-Z][0-9]");//检查是否存在字母后为数字情况
		Matcher m=p.matcher(str);
		if(m.find())return false;
		
		p=Pattern.compile("[\\*\\^+-]{2,}");//检查连续两个运算符情况
		m=p.matcher(str);
		if(m.find())return false;
		
		p=Pattern.compile("[\\^][a-zA-Z]");//检查存在字母为幂指数
		m=p.matcher(str);
		if(m.find())return false;
		
		p=Pattern.compile("[\\^]\\d+[a-zA-Z]");//检查幂指数后直接有字母
		m=p.matcher(str);
		if(m.find())return false;
		
		return true;
	}
	
	class linkTable//一个二维链表类
	{
		node head;
		node tail;

		public node insert(int fac)//插入每列的头节点，返回该节点
		//插入系数节点

		{
			node n=new node();
			n.fac=fac;
			if(head==null)
			{
				head=n;
				tail=n;
			}
			else
			{
				tail.next=n;
				tail=tail.next;
			}
			return n;
		}
		public void insert(String var,node top,int num)
		//鎻掑叆鍙橀噺锛寁ar涓哄彉閲忥紝top涓虹郴鏁拌妭鐐癸紝num涓哄箓鎸囨暟
		{
			node p=top;
			while (p.link!=null&&p.link.var.compareTo(var)<0)
			{
				p=p.link;
			}
			
			if(p.link==null){
				node n=new node();
				n.var=var;n.exp=num;
				p.link=n;
			}
			else if(p.link.var.compareTo(var)==0){
				p.link.exp+=num;
			}
			else{
				node q=p.link,n=new node();
				n.var=var;n.exp=num;
				p.link=n;n.link=q;
			}
		}
	
		public void simplify(){//对于该链表进行处理，简化多项式
			node p=head,q=head.next,r=head,r1=head;
			while(p!=null){
			
			if(q==null){
				r1=p;
				p=p.next;
				if(p==null)break;
				q=p.next;r=p;
			}
			else if(compare(p,q)){
				p.fac=p.fac+q.fac;
			
				
				if(q==tail)tail=r;
				r.next=q.next;
				q=q.next;
				
				if(p.fac==0){
					if(r1==p)head=p.next;
					if(tail==p)tail=null;
				}
				
				
				}
			else{
				r=q;q=q.next;
				}
			}
		}
	
		boolean compare(node top1,node top2){//检查每一列后的变量是否完全相同
			node p=top1.link,q=top2.link;
			while(p!=null&&q!=null){
				if(!(p.exp==q.exp&&p.var.equals(q.var)))
					return false;
				p=p.link;q=q.link;
			}
			if(p==q)return true;
			else return false;
		}
		
	}
	
	class node
	{
		int fac;
		String var;
		int exp;
		node next=null,link=null;
	}
	//化简并生成新的链表
	linkTable simplify(linkTable l,String var,int num)
	{
		
		linkTable lz=new linkTable();
		node ltop=l.head;
		node lztop=lz.head;
		while (ltop!=null)//寻找变量所在的位置
		{
			lztop=lz.insert(ltop.fac);
			node lp=ltop.link;
			//node lzp=lztop.link;
			while(lp!=null&&lp.var.compareTo(var)<0)//这里的compareTo判断条件可能不对
			{
				//这里应该把寻找过的节点插入到新的链表里
				lz.insert(lp.var, lztop, lp.exp);
				//
				lp=lp.link;
			}
			if(lp!=null&&lp.var.compareTo(var)==0)  //如果找到变量值的话
			{
				lztop.fac=(int) (ltop.fac*Math.pow(num,lp.exp));//计算值
				//ltop=ltop.next;//前往下一列找
				//continue;
			}
			else if(lp!=null)
			{
				lz.insert(lp.var, lztop, lp.exp);
			}
			
			
			while(lp!=null&&lp.link!=null)
			{
				lp=lp.link;
				//这里应该把寻找过的节点插入到新的链表里
				lz.insert(lp.var, lztop, lp.exp);
				
				
			}
			ltop=ltop.next;
			//lztop.next=ltop;//横向的链要链接好;
			
		}
		//lp=ltop.link;
		return lz;	
	}
	
	linkTable derivate(linkTable l,String var)//求导函数，参数：二维链表，被求导的自变量
	{
		boolean findVar=false;
		linkTable lz=new linkTable();
		node ltop=l.head;
		node lztop=lz.head;
		while (ltop!=null)//寻找变量所在的位置
		{
			//lztop=lz.insert(ltop.fac);
			node lfind=ltop.link;
			//node lzp=lztop.link;
			while(lfind!=null&&lfind.var.compareTo(var)<0)//这里的compareTo判断条件可能不对
			{
				//这里应该把寻找过的节点插入到新的链表里
				//lz.insert(lp.var, lztop, lp.exp);
				//
				lfind=lfind.link;
			}
			if(lfind!=null&&lfind.var.compareTo(var)==0&&lfind.exp!=0)  //如果找到变量值的话而且变量的幂指数不为零
			{
				findVar=true;
				/*
				if (lp.exp>1)
				{
					lztop.fac=ltop.fac*lp.exp-1;//先得出系数
					lz.insert(lp.var, lztop, lp.exp-1);
				}
				
				else if(lp.exp==1)
				{
					//貌似不用处理直接跳过
				}
				
				else if(lp.exp==0)
				{
					findVar=false;
				}
				*/
				//lztop.fac=(int) (ltop.fac*Math.pow(num,lp.exp));//计算值
				//ltop=ltop.next;//前往下一列找
				//continue;
			}
			/*
			else if(lp!=null)
			{
				lz.insert(lp.var, lztop, lp.exp);
			}
			*/
			if (findVar)
			{
				lztop=lz.insert(ltop.fac);
				node lp=ltop.link;
				//node lzp=lztop.link;
				while(lp!=null&&lp.var.compareTo(var)<0)//这里的compareTo判断条件可能不对
				{
					//这里应该把寻找过的节点插入到新的链表里
					lz.insert(lp.var, lztop, lp.exp);
					//
					lp=lp.link;
				}
				if(lp!=null&&lp.var.compareTo(var)==0)  //如果找到变量值的话
				{
					//findVar=true;
					
					if (lp.exp>1)
					{
						lztop.fac=ltop.fac*(lp.exp-1);//先得出系数
						lz.insert(lp.var, lztop, lp.exp-1);
					}
					/*
					else if(lp.exp==1)
					{
						//貌似不用处理直接跳过
					}
					*/
					/*
					else if(lp.exp==0)
					{
						findVar=false;
					}
					*/
					//lztop.fac=(int) (ltop.fac*Math.pow(num,lp.exp));//计算值
					//ltop=ltop.next;//前往下一列找
					//continue;
				}
				/*
				else if(lp!=null)
				{
					lz.insert(lp.var, lztop, lp.exp);
				}
				*/
				while(lp!=null&&lp.link!=null)
				{
					lp=lp.link;
					//这里应该把寻找过的节点插入到新的链表里
					lz.insert(lp.var, lztop, lp.exp);
					
					
				}	
					
			}
			
			
			
			
			ltop=ltop.next;
			findVar=false;
			//lztop.next=ltop;//横向的链要链接好;
			
		}
		
		return lz;
	}
	
	boolean simplifyIsTrue(String str)//判断!simplify输入是否合法
	{
		String[] strsplit=str.split("\\s+");
		Pattern p;
		p=Pattern.compile("!simplify");
		Matcher m=p.matcher(strsplit[0]);
		if(!m.find())return false;
		for(int i=1;i<strsplit.length;i++)
		{
			Pattern p1;
			p1=Pattern.compile("[a-zA-Z]+={1}[\\d]+");
			Matcher m1=p1.matcher(strsplit[i]);
			if(!m1.matches())return false;
		}
		
		return true;
	}
	
	boolean dxIsTruesTrue(String str)//判断输入是否合法
	{
		Pattern p;
		p=Pattern.compile("!d/d[a-zA-Z]+");
		Matcher m=p.matcher(str);
		if(!m.find())return false;
		return true;
	}
	
	


}



