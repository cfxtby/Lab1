package homeWork1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import homeWork1.Lab1.linkTable;



public class LinkTable {
	
	 /**
     * 头.
     */
    private Node head;
    /**
     * 尾.
     */
    private Node tail;

    /**
     * 插入每列的头节点，返回该节点.
     * 插入系数节点.
     * @param fac 参数
     * @return 返回值
     */
    public Node insert(final int fac) {
        Node n = new Node();
        n.fac = fac;
        if (this.head == null) {
            this.head = n;
            this.tail = n;
        } else {
        	this.tail.next = n;
        	this.tail = this.tail.next;
        }
        return n;
    }
    
    /**
     * 插入每一列的变量
     * @param var 参数
     * @param top 参数
     * @param num 参数
     */
    public void insert(final String var, final Node top, final int num) {
        Node p = top;
        while (p.link != null && p.link.var.compareTo(var) < 0) {
            p = p.link;
        }

        if (p.link == null) {
            Node n = new Node();
            n.var = var;
            n.exp = num;
            p.link = n;
        } else if (p.link.var.compareTo(var) == 0) {
            p.link.exp += num;
        } else {
            Node q = p.link, n = new Node();
            n.var = var;
            n.exp = num;
            p.link = n;
            n.link = q;
        }
    }
    private void setTable(final String str) {
        //linkTable lt = new linkTable();
        int n = 0;
        Pattern p1, p2, p3, p4;
        p1 = Pattern.compile("[\\*+-]");
        p2 = Pattern.compile("\\d+");
        p3 = Pattern.compile("[a-zA-Z]+[\\^]\\d+");
        p4 = Pattern.compile("[a-zA-Z]+");

        Matcher m1, m2, m3, m4;
        m1 = p1.matcher(str);
        m2 = p2.matcher(str);
        m3 = p3.matcher(str);
        m4 = p4.matcher(str);

        boolean flag1, flag2, flag3, flag4;
        flag1 = m1.find(n);
        flag2 = m2.find(n);
        flag3 = m3.find(n);
        flag4 = m4.find(n);

        String dealStr;
        Node top = null;
        do {
            if (flag1 && m1.start() == n) {
                dealStr = m1.group();
                if (dealStr.compareTo("+") == 0) {
                    top = insert(1);
                } else if (dealStr.compareTo("-") == 0) {
                    top = insert(-1);
                }
                n = m1.end();
                flag1 = m1.find();
            } else if (flag2 && m2.start() == n) {
                dealStr = m2.group();
                if (top == null) {
                    top = this.insert(Integer.parseInt(dealStr));
                } else {
                    top.fac = top.fac * Integer.parseInt(dealStr);
                }

                n = m2.end();
                flag2 = m2.find();
            } else if (flag3 && m3.start() == n) {
                dealStr = m3.group();
                if (top == null) {
                    top = this.insert(1);
                }
                Pattern p5 = Pattern.compile("([a-zA-Z])\\1");
                Matcher m5 = p5.matcher(dealStr);
                if (m5.find()) {
                    insert(dealStr.split("\\^")[0], top, Integer.parseInt(dealStr.split("\\^")[1]));
                } else {
                    String[] arrStr = dealStr.split("\\^");
                    char[] arrChar = arrStr[0].toCharArray();
                    insert(arrChar[arrChar.length - 1] + "", top, Integer.parseInt(dealStr.split("\\^")[1]));
                    for (int i = 0; i < arrChar.length - 1; i++) {
                        insert(arrChar[i] + "", top, 1);
                    }
                }

                n = m3.end();
                flag3 = m3.find();
                flag4 = m4.find(n);
                flag2 = m2.find(n);
            } else if (flag4 && m4.start() == n) {
                if (top == null) {
                    top = insert(1);
                }
                dealStr = m4.group();
                Pattern p5 = Pattern.compile("([a-zA-Z])\\1");
                Matcher m5 = p5.matcher(dealStr);
                if (m5.find()) {
                    insert(dealStr, top, 1);
                } else {
                    char[] arrChar = dealStr.toCharArray();
                    for (int i = 0; i < arrChar.length; i++) {
                        insert(arrChar[i] + "", top, 1);
                    }
                }

                n = m4.end();
                flag4 = m4.find();
            }

        } while (n != str.length());
        
    }
    
    public Node getHead() {
      return head;
    }

    public void setHead(Node head) {
      this.head = head;
    }

    private boolean compare(final Node top1, final Node top2) {
        Node p = top1.link, q = top2.link;
        while (p != null && q != null) {
            if (!(p.exp == q.exp && p.var.equals(q.var))) {
                return false;
            }
            p = p.link;
            q = q.link;
        }
        return p == q;
    }
    
    public String expression() {// 遍历表，读出多项式
        String str = "";
        Node p = this.head, q = p;
        boolean flag = false;

        while (q != null || p != null) {
            if (q == null) {
                q = p.next;
                p = p.next;
            } else {

                if (p == q) {

                    if (p.fac == 1 && p.link != null) {
                        if (!str.equals("")) {
                            str += "+";
                        }
                        flag = true;
                    } else if (p.fac == -1 && p.link != null) {
                        str += "-";
                        flag = true;
                    } else {
                        if (str.equals("")) {
                            str += p.fac;
                        } else {
                          str += (p.fac>=0?"+":"") + p.fac;
                        }
                    }
                } else {
                    str += (flag ? "" : "*") + q.var + (q.exp == 1 ? "" : "^" + q.exp);
                    if (flag) {
                        flag = false;
                    }
                }
                q = q.link;
            }
        }
        if (this.head == null) {
            str = "0";
        }
        return str;
    }
    /**
     * 化简表达式
     */
    public void simplify() {
        Node p = this.head, q = this.head.next, r = this.head, r1 = this.head;
        while (p != null) {

            if (q == null) {
                r1 = p;
                p = p.next;
                if (p == null) {
                    break;
                }
                q = p.next;
                r = p;
            } else if (this.compare(p, q)) {
                p.fac = p.fac + q.fac;

                if (q == this.tail) {
                    this.tail = r;
                }
                r.next = q.next;
                q = q.next;

                if (p.fac == 0) {
                    if (r1 == p) {
                        this.head = p.next;
                    }
                    if (this.tail == p) {
                        this.tail = null;
                    }
                }

            } else {
                r = q;
                q = q.next;
            }
        }
    }
    
    public LinkTable simplify(final String var, final int num) {
        LinkTable lz = new LinkTable();
        Node ltop = this.head;
        Node lztop = lz.head;
        boolean flag = false;
        while (ltop != null)// 寻找变量所在的位置
        {
            lztop = lz.insert(ltop.fac);
            Node lp = ltop.link;
            // node lzp=lztop.link;
            while (lp != null && lp.var.compareTo(var) < 0) { 
                // 这里应该把寻找过的节点插入到新的链表里
                lz.insert(lp.var, lztop, lp.exp);
                //
                lp = lp.link;
            }
            if (lp != null && lp.var.compareTo(var) == 0) { // 如果找到变量值的话
                lztop.fac = (int) (ltop.fac * Math.pow(num, lp.exp)); // 计算值
                flag = true;
                // ltop=ltop.next;//前往下一列找
                // continue;
            } else if (lp != null) {
                lz.insert(lp.var, lztop, lp.exp);
            }

            while (lp != null && lp.link != null) {
                lp = lp.link;
                // 这里应该把寻找过的节点插入到新的链表里
                lz.insert(lp.var, lztop, lp.exp);
            }
            ltop = ltop.next;
            // lztop.next=ltop;//横向的链要链接好;
        }
        // lp=ltop.link;
        if (flag) {
        	
            return lz;
        } else {
            return null;
        }
    }
    
    public LinkTable simplify(final String order) {
        String str1 = order.replaceAll("\\s*=\\s*", "=");
        String[] strsplit = str1.split("\\s+");
        LinkTable l2 = this;
        for (int i = 1; i < strsplit.length; i++) {
            l2 = l2.simplify(strsplit[i].split("=")[0], Integer.parseInt(strsplit[i].split("=")[1]));
            
            if (l2 == null) {
            	//if (LOG.isLoggable(Level.FINE)) {
                System.out.println(strsplit[i].split("=")[0] + "变量不存在");
            	//}
                return null;
            }
            
        }
        if(l2==null){
          simplify();return this;}
        else {l2.simplify();
        return l2;}
    }
    
    public LinkTable derivate1(final String var) {
        LinkTable lz = new LinkTable();
        Node ltop = this.head,  lztop, p;
        while (ltop != null) {
            p = ltop.link;
            // lztop=lz.insert(ltop.fac);
            while (p != null && p.var.compareTo(var) < 0) {
                // lz.insert(var, lztop, p.exp);
                p = p.link;
            }
            if (p == null || p.var.compareTo(var) != 0) {
                ltop = ltop.next;
            } else {
                p = ltop.link;
                lztop = lz.insert(ltop.fac);
                while (p != null && p.var.compareTo(var) < 0) {
                    lz.insert(p.var, lztop, p.exp);
                    p = p.link;
                }
                if (p != null && p.var.compareTo(var) == 0) {
                    if (p.exp > 1) {
                        lz.insert(p.var, lztop, p.exp - 1);
                        lztop.fac *= p.exp;
                    }
                    p = p.link;
                }
                while (p != null) {
                    lz.insert(p.var, lztop, p.exp);
                    p = p.link;
                }
                ltop = ltop.next;
            }

        }
        return lz;
    }
    public LinkTable derivate(final String var) {
        boolean findVar = false;
        LinkTable lz = new LinkTable();
        Node ltop = this.head;
        Node lztop = lz.head;
        while (ltop != null) { // 寻找变量所在的位置

            Node lfind = ltop.link;
            while (lfind != null && lfind.var.compareTo(var) < 0) { // 这里的compareTo判断条件可能不对
                // 这里应该把寻找过的节点插入到新的链表里
                // lz.insert(lp.var, lztop, lp.exp);
                //
                lfind = lfind.link;
            }
            if (lfind != null && lfind.var.compareTo(var) == 0 && lfind.exp != 0) { // 如果找到变量值的话而且变量的幂指数不为零
                findVar = true;

            }

            if (findVar) {
                lztop = lz.insert(ltop.fac);
                Node lp = ltop.link;
                while (lp != null && lp.var.compareTo(var) < 0) { // 这里的compareTo判断条件可能不对
                    // 这里应该把寻找过的节点插入到新的链表里
                    lz.insert(lp.var, lztop, lp.exp);
                    //
                    lp = lp.link;
                }
                if (lp != null && lp.var.compareTo(var) == 0) { // 如果找到变量值的话
                    if (lp.exp > 1) {
                        lztop.fac = ltop.fac * (lp.exp - 1); // 先得出系数//计算方法错误
                        lz.insert(lp.var, lztop, lp.exp - 1);
                    }

                }

                while (lp != null && lp.link != null) {
                    lp = lp.link;
                    // 这里应该把寻找过的节点插入到新的链表里
                    lz.insert(lp.var, lztop, lp.exp);

                }

            }

            ltop = ltop.next;
            findVar = false;
            // lztop.next=ltop;//横向的链要链接好;

        }

        return lz;
    }
    public String simplifyTest(String order){
      if(new Test().simplifyIsTrue(order)){
        
        LinkTable l=this.simplify(order);
        if (l==null)return "error order!";
        return l.expression();
      }
      else{
        return "error order!";
      }
    }
    
    LinkTable(String commond){
    	this.setTable(commond);
    }
    LinkTable(){
    	
    }
}
