package homeWork1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sonny
 *
 */
public class Lab1 {
    /**
     * @param args ����������
     */
    public static void main(final String[] args) {
        Lab1 l = new Lab1();
        l.begin();
    }
    
    /**
     * @param str �ַ�
     * @return �ַ�ת�����
     */
    final String getVar(final String str) {
        Pattern p;
        p = Pattern.compile("[a-zA-z]+");
        Matcher m;
        m = p.matcher(str);
        String result;
        if (m.find()) {
            result = m.group();
            return result;
        } else {
            return "wrong";
        }
    }

    /**
     * @param str �ַ�
     * @return �ַ�ת�����
     */
    final Object getNum(final String str) {
        Pattern p;
        p = Pattern.compile("\\d+");
        Matcher m;
        m = p.matcher(str);
        if (m.find()) {
            int result = Integer.parseInt(str.substring(m.start(), m.end()));
            return result;
        } else {
            return null;
        }
    }


    /**
     * ��ں���.
     */
    public final void begin() {
        Scanner reader = new Scanner(System.in);
        String order;
        linkTable lz = null;
        long time;
        while (true) {
            order = reader.nextLine();
            time = System.currentTimeMillis();
            if (test(order)) {
                lz = setTable(order.replaceAll("\\s*", ""));
                ////if (LOG.isLoggable(Level.FINE)) {}
                System.out.println(expression(lz));
                
            } else if (simplifyIsTrue(order)) {
                if (lz == null) {
                	//if (LOG.isLoggable(Level.FINE)) {
                    System.out.println("����������һ�����ʽ");
                	//}
                } else {
                    linkTable l = simplify(lz, order);
                    //if (LOG.isLoggable(Level.FINE)) {
                    System.out.println(expression(l));
                    //}
                }
            } else if (dxIsTruesTrue(order)) {
                if (lz == null) {
                	//if (LOG.isLoggable(Level.FINE)) {
                    System.out.println("����������һ�����ʽ");
                	//}
                } else {
                    String str = order.replaceAll("\\s*", "");
                    linkTable l = derivate1(lz, str.replaceAll("!d/d", ""));
                    //if (LOG.isLoggable(Level.FINE)) {
                    System.out.println(expression(l));
                    //}
                }
            } else if (order.compareTo("!exit") == 0) {
                break;
            } else {
            	//if (LOG.isLoggable(Level.FINE)) {
                System.out.println("������Ϸ��ı��ʽ������");
            	//}
            }
            //if (LOG.isLoggable(Level.FINE)) {
            System.out.println("ִ�б����������" + (System.currentTimeMillis() - time) + "ms");
            //}
        }
        reader.close();
    }

    /**
     * @param lt �������
     * @return ��������ֵ
     */
    final String expression(final linkTable lt) {// ��������������ʽ
        String str = "";
        node p = lt.head, q = p;
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
                            str += "+" + p.fac;
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
        if (lt.head == null) {
            str = "0";
        }
        return str;
    }

    /**
     * @return ���������
     */
    final String getCommand() {
        Scanner reader = new Scanner(System.in);
        String str = reader.nextLine();
        reader.close();
        return str;
    }

    /**
     * @param str ����
     * @return ����ֵ
     */
    final linkTable setTable(final String str) {
        linkTable lt = new linkTable();
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
        node top = null;
        do {
            if (flag1 && m1.start() == n) {
                dealStr = m1.group();
                if (dealStr.compareTo("+") == 0) {
                    top = lt.insert(1);
                } else if (dealStr.compareTo("-") == 0) {
                    top = lt.insert(-1);
                }
                n = m1.end();
                flag1 = m1.find();
            } else if (flag2 && m2.start() == n) {
                dealStr = m2.group();
                if (top == null) {
                    top = lt.insert(Integer.parseInt(dealStr));
                } else {
                    top.fac = top.fac * Integer.parseInt(dealStr);
                }

                n = m2.end();
                flag2 = m2.find();
            } else if (flag3 && m3.start() == n) {
                dealStr = m3.group();
                if (top == null) {
                    top = lt.insert(1);
                }
                Pattern p5 = Pattern.compile("([a-zA-Z])\\1");
                Matcher m5 = p5.matcher(dealStr);
                if (m5.find()) {
                    lt.insert(dealStr.split("\\^")[0], top, Integer.parseInt(dealStr.split("\\^")[1]));
                } else {
                    String[] arrStr = dealStr.split("\\^");
                    char[] arrChar = arrStr[0].toCharArray();
                    lt.insert(arrChar[arrChar.length - 1] + "", top, Integer.parseInt(dealStr.split("\\^")[1]));
                    for (int i = 0; i < arrChar.length - 1; i++) {
                        lt.insert(arrChar[i] + "", top, 1);
                    }
                }

                n = m3.end();
                flag3 = m3.find();
                flag4 = m4.find(n);
                flag2 = m2.find(n);
            } else if (flag4 && m4.start() == n) {
                if (top == null) {
                    top = lt.insert(1);
                }
                dealStr = m4.group();
                Pattern p5 = Pattern.compile("([a-zA-Z])\\1");
                Matcher m5 = p5.matcher(dealStr);
                if (m5.find()) {
                    lt.insert(dealStr, top, 1);
                } else {
                    char[] arrChar = dealStr.toCharArray();
                    for (int i = 0; i < arrChar.length; i++) {
                        lt.insert(arrChar[i] + "", top, 1);
                    }
                }

                n = m4.end();
                flag4 = m4.find();
            }

        } while (n != str.length());
        return lt;
    }

    /**
     * @param str1 ����
     * @return ����ֵ
     */
    final boolean test(final String str1) { // ���Զ���ʽ�����Ƿ�Ϸ�
        String str = str1.replaceAll("\\s*", "");
        if (!(str.matches("[1-9a-zA-Z[-]][[\\*\\^+-][0-9a-zA-Z]]*"))) {
            return false; // ������޺Ϸ��ַ�
        }

        if ((str.matches(".*[\\*\\^+-]"))) {
            return false; // ���ĩβ�ַ�Ϊ�������
        }
        Pattern p = Pattern.compile("[a-zA-Z][0-9]"); // ����Ƿ������ĸ��Ϊ�������
        Matcher m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        p = Pattern.compile("[\\*\\^+-]{2,}"); // �������������������
        m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        p = Pattern.compile("[\\^][a-zA-Z]"); // ��������ĸΪ��ָ��
        m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        p = Pattern.compile("[\\^]\\d+[a-zA-Z]"); // �����ָ����ֱ������ĸ
        m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        return true;
    }

    /**
     * @author sonny
     *һ����λ������.
     */
    class linkTable { // һ����ά������
        /**
         * ͷ.
         */
        node head;
        /**
         * β.
         */
        node tail;

        /**
         * ����ÿ�е�ͷ�ڵ㣬���ظýڵ�.
         * ����ϵ���ڵ�.
         * @param fac ����
         * @return ����ֵ
         */
        public node insert(final int fac) {
            node n = new node();
            n.fac = fac;
            if (head == null) {
                head = n;
                tail = n;
            } else {
                tail.next = n;
                tail = tail.next;
            }
            return n;
        }

        /**
         * @param var ����
         * @param top ����
         * @param num ����
         */
        public void insert(final String var, final node top, final int num) {
            node p = top;
            while (p.link != null && p.link.var.compareTo(var) < 0) {
                p = p.link;
            }

            if (p.link == null) {
                node n = new node();
                n.var = var;
                n.exp = num;
                p.link = n;
            } else if (p.link.var.compareTo(var) == 0) {
                p.link.exp += num;
            } else {
                node q = p.link, n = new node();
                n.var = var;
                n.exp = num;
                p.link = n;
                n.link = q;
            }
        }

        /**
         *  ���ڸ�������д����򻯶���ʽ.
         */
        public void simplify() {
            node p = head, q = head.next, r = head, r1 = head;
            while (p != null) {

                if (q == null) {
                    r1 = p;
                    p = p.next;
                    if (p == null) {
                        break;
                    }
                    q = p.next;
                    r = p;
                } else if (compare(p, q)) {
                    p.fac = p.fac + q.fac;

                    if (q == tail) {
                        tail = r;
                    }
                    r.next = q.next;
                    q = q.next;

                    if (p.fac == 0) {
                        if (r1 == p) {
                            head = p.next;
                        }
                        if (tail == p) {
                            tail = null;
                        }
                    }

                } else {
                    r = q;
                    q = q.next;
                }
            }
        }

        /**
         * ���ÿһ�к�ı����Ƿ���ȫ��ͬ.
         * @param top1 ����
         * @param top2 ����
         * @return ����ֵ
         */
        boolean compare(final node top1, final node top2) {
            node p = top1.link, q = top2.link;
            while (p != null && q != null) {
                if (!(p.exp == q.exp && p.var.equals(q.var))) {
                    return false;
                }
                p = p.link;
                q = q.link;
            }
            return p == q;
        }

    }

    /**
     * @author sonny
     *
     */
    class node {
        /**
         *  1.
         */
        int fac;
        /**
         *  2.
         */
        String var;
        /**
         *  3.
         */
        int exp;
        /**
         *  4.
         */
        node next = null, link = null;
    }
    // ���������µ�����

    /**
     * �������������н����������м�.
     * @param l ����
     * @param order ����
     * @return ����ֵ
     */
    final linkTable simplify(final linkTable l, final String order) {
        String str1 = order.replaceAll("\\s*=\\s*", "=");
        String[] strsplit = str1.split("\\s+");
        linkTable l1 = l, l2;
        for (int i = 1; i < strsplit.length; i++) {
            l2 = simplify(l1, strsplit[i].split("=")[0], Integer.parseInt(strsplit[i].split("=")[1]));
            if (l2 == null) {
            	//if (LOG.isLoggable(Level.FINE)) {
                System.out.println(strsplit[i].split("=")[0] + "����������");
            	//}
                return null;
            }
            l1 = l2;
        }
        l1.simplify();
        return l1;
    }

    /**
     * @param l ����
     * @param var ����
     * @param num ����
     * @return ����ֵ
     */
    final linkTable simplify(final linkTable l, final String var, final int num) {
        linkTable lz = new linkTable();
        node ltop = l.head;
        node lztop = lz.head;
        boolean flag = false;
        while (ltop != null)// Ѱ�ұ������ڵ�λ��
        {
            lztop = lz.insert(ltop.fac);
            node lp = ltop.link;
            // node lzp=lztop.link;
            while (lp != null && lp.var.compareTo(var) < 0) { // �����compareTo�ж��������ܲ���
                // ����Ӧ�ð�Ѱ�ҹ��Ľڵ���뵽�µ�������
                lz.insert(lp.var, lztop, lp.exp);
                //
                lp = lp.link;
            }
            if (lp != null && lp.var.compareTo(var) == 0) { // ����ҵ�����ֵ�Ļ�
                lztop.fac = (int) (ltop.fac * Math.pow(num, lp.exp)); // ����ֵ
                flag = true;
                // ltop=ltop.next;//ǰ����һ����
                // continue;
            } else if (lp != null) {
                lz.insert(lp.var, lztop, lp.exp);
            }

            while (lp != null && lp.link != null) {
                lp = lp.link;
                // ����Ӧ�ð�Ѱ�ҹ��Ľڵ���뵽�µ�������
                lz.insert(lp.var, lztop, lp.exp);
            }
            ltop = ltop.next;
            // lztop.next=ltop;//�������Ҫ���Ӻ�;
        }
        // lp=ltop.link;
        if (flag) {
            return lz;
        } else {
            return null;
        }
    }

    /**
     * �󵼺�������������ά�������󵼵��Ա���.
     * @param l ����
     * @param var ����
     * @return ����ֵ
     */
    final linkTable derivate1(final linkTable l, final String var) {
        linkTable lz = new linkTable();
        node ltop = l.head,  lztop, p;
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

    /**
     * �󵼺�������������ά�������󵼵��Ա���.
     * @param l ����
     * @param var ����
     * @return ����ֵ
     */
    final linkTable derivate(final linkTable l, final String var) {
        boolean findVar = false;
        linkTable lz = new linkTable();
        node ltop = l.head;
        node lztop = lz.head;
        while (ltop != null) { // Ѱ�ұ������ڵ�λ��

            // lztop=lz.insert(ltop.fac);
            node lfind = ltop.link;
            // node lzp=lztop.link;
            while (lfind != null && lfind.var.compareTo(var) < 0) { // �����compareTo�ж��������ܲ���
                // ����Ӧ�ð�Ѱ�ҹ��Ľڵ���뵽�µ�������
                // lz.insert(lp.var, lztop, lp.exp);
                //
                lfind = lfind.link;
            }
            if (lfind != null && lfind.var.compareTo(var) == 0 && lfind.exp != 0) { // ����ҵ�����ֵ�Ļ����ұ�������ָ����Ϊ��
                findVar = true;

            }

            if (findVar) {
                lztop = lz.insert(ltop.fac);
                node lp = ltop.link;
                while (lp != null && lp.var.compareTo(var) < 0) { // �����compareTo�ж��������ܲ���
                    // ����Ӧ�ð�Ѱ�ҹ��Ľڵ���뵽�µ�������
                    lz.insert(lp.var, lztop, lp.exp);
                    //
                    lp = lp.link;
                }
                if (lp != null && lp.var.compareTo(var) == 0) { // ����ҵ�����ֵ�Ļ�
                    if (lp.exp > 1) {
                        lztop.fac = ltop.fac * (lp.exp - 1); // �ȵó�ϵ��//���㷽������
                        lz.insert(lp.var, lztop, lp.exp - 1);
                    }

                }

                while (lp != null && lp.link != null) {
                    lp = lp.link;
                    // ����Ӧ�ð�Ѱ�ҹ��Ľڵ���뵽�µ�������
                    lz.insert(lp.var, lztop, lp.exp);

                }

            }

            ltop = ltop.next;
            findVar = false;
            // lztop.next=ltop;//�������Ҫ���Ӻ�;

        }

        return lz;
    }

    /**
     * �ж�!simplify�����Ƿ�Ϸ�.
     * @param str ����
     * @return ����ֵ
     */
    final boolean simplifyIsTrue(final String str) { // �ж�!simplify�����Ƿ�Ϸ�
        String str1 = str.replaceAll("\\s*=\\s*", "=");
        str1 = str1.replaceAll("!\\s*simplify", "!simplify");
        //boolean flag = str.startsWith("!\\s*simplify");
        String[] strsplit = str1.split("\\s+");
        Pattern p;
        p = Pattern.compile("!simplify");
        Matcher m = p.matcher(strsplit[0]);
        if (!m.find()) {
            return false;
        }
        // if(strsplit.length<=1){System.out.println("���������!!!!");return false;}
        for (int i = 1; i < strsplit.length; i++) {
            Pattern p1;
            p1 = Pattern.compile("[a-zA-Z]+={1}[\\d]+");
            Matcher m1 = p1.matcher(strsplit[i]);
            if (!m1.matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * �ж������Ƿ�Ϸ�.
     * @param str ����
     * @return ����ֵ
     */
    final boolean dxIsTruesTrue(final String str) { // �ж������Ƿ�Ϸ�
        Pattern p;
        p = Pattern.compile("!d/d\\s*[a-zA-Z]+");
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        }
        return true;
    }

}
