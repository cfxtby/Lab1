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
                    System.out.println("请首先输入一个表达式");
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
                    System.out.println("请首先输入一个表达式");
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
                System.out.println("请输入合法的表达式或命令");
            	//}
            }
            //if (LOG.isLoggable(Level.FINE)) {
            System.out.println("执行本次命令花费了" + (System.currentTimeMillis() - time) + "ms");
            //}
        }
        bd.close();
    }
}
