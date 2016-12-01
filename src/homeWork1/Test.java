package homeWork1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	private String str1;

	public boolean polyTest(final String str1) { // ���Զ���ʽ�����Ƿ�Ϸ�
        this.setStr1(str1);
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
	public boolean simplifyIsTrue(final String str) { // �ж�!simplify�����Ƿ�Ϸ�
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
    public boolean dxIsTruesTrue(final String str) { // �ж������Ƿ�Ϸ�
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
