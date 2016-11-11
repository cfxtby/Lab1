package homeWork1;

import static org.junit.Assert.*;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import junit.framework.Assert;


@RunWith(Parameterized.class)
public class Lab1Test {

  
  private Lab1 l1;
  protected String request,expect;  
  public Lab1Test(String request,String expect){
    this.request=request;
    this.expect=expect;
  }
  
  
  @Before
  public void before(){
    l1=new Lab1();
    l1.lz=l1.setTable("x^2+yoo^2");
  }
  
  
  @Parameters  
  public static Collection<String[]> caseData() {  
    File file=new File("blackTest.cvs");
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(file) );
      List l=new ArrayList<String[]>();
      while(sc.hasNextLine()){
        String s=sc.nextLine();
        if(s.split(",").length>0)
        l.add(s.split(","));
      }
      return l;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
    
  }  

  
  @Test
  public void test() {
    //System.out.println(l1.simplifyTest(l1.getTable(),request));
    assertEquals(expect, l1.simplifyTest(l1.getTable(),request));
  }

}
