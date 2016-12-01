package homeWork1;

public class Controller {
	public  void begin() {
    	
        Boundary bd = new Boundary();
        Test test = new Test();
        LinkTable lz =null;
        String order ;
        long time;
        while (true) {
            order = bd.getCommond();
            time = System.currentTimeMillis();
            if (test.polyTest(order)) {
                lz = new LinkTable(order.replaceAll("\\s*", ""));
                ////if (LOG.isLoggable(Level.FINE)) {}
                System.out.println(lz.expression());
                
            } else if (test.simplifyIsTrue(order)) {
                if (lz == null) {
                	//if (LOG.isLoggable(Level.FINE)) {
                    System.out.println("����������һ�����ʽ");
                	//}
                } else {
                    LinkTable l = lz.simplify(order);
                    //if (LOG.isLoggable(Level.FINE)) {
                    System.out.println(l.expression());
                    //}
                }
            } else if (test.dxIsTruesTrue(order)) {
                if (lz == null) {
                	//if (LOG.isLoggable(Level.FINE)) {
                    System.out.println("����������һ�����ʽ");
                	//}
                } else {
                    String str = order.replaceAll("\\s*", "");
                    LinkTable l = lz.derivate1(str.replaceAll("!d/d", ""));
                    //if (LOG.isLoggable(Level.FINE)) {
                    System.out.println(l.expression());
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
        bd.close();
    }
}
