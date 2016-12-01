package homeWork1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	private String str1;

	public boolean polyTest(final String str1) { // 测试多项式输入是否合法
        this.setStr1(str1);
		String str = str1.replaceAll("\\s*", "");
        if (!(str.matches("[1-9a-zA-Z[-]][[\\*\\^+-][0-9a-zA-Z]]*"))) {
            return false; // 检查有无合法字符
        }

        if ((str.matches(".*[\\*\\^+-]"))) {
            return false; // 检查末尾字符为符号情况
        }
        Pattern p = Pattern.compile("[a-zA-Z][0-9]"); // 检查是否存在字母后为数字情况
        Matcher m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        p = Pattern.compile("[\\*\\^+-]{2,}"); // 检查连续两个运算符情况
        m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        p = Pattern.compile("[\\^][a-zA-Z]"); // 检查存在字母为幂指数
        m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        p = Pattern.compile("[\\^]\\d+[a-zA-Z]"); // 检查幂指数后直接有字母
        m = p.matcher(str);
        if (m.find()) {
            return false;
        }

        return true;
    }
	public boolean simplifyIsTrue(final String str) { // 判断!simplify输入是否合法
        String str1 = str.replaceAll("\\s*=\\s*", "=");
        str1 = str1.replaceAll("!\\s*simplify", "!simplify");
        //boolean flag = str.startsWith("!\\s*simplify");
        String[] strsplit = str1.split("\\s+");
        if(strsplit.length<1)return false;
        Pattern p;
        if(!strsplit[0].equals("!simplify"))return false;
        p = Pattern.compile("!simplify");
        Matcher m = p.matcher(strsplit[0]);
        if (!m.find()) {
            return false;
        }
        // if(strsplit.length<=1){System.out.println("请输入变量!!!!");return false;}
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
     * 判断输入是否合法.
     * @param str 参数
     * @return 返回值
     */
    public boolean dxIsTruesTrue(final String str) { // 判断输入是否合法
        Pattern p;
        p = Pattern.compile("!d/d\\s*[a-zA-Z]+");
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        }
        return true;
    }
	public String getStr1() {
		return str1;
	}
	public void setStr1(String str1) {
		this.str1 = str1;
	}
}
