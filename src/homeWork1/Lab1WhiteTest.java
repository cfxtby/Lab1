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
public class Lab1WhiteTest {

  private Lab1 l1;
  protected String request, expect;

  public Lab1WhiteTest(String request, String expect) {
    this.request = request;
    this.expect = expect;
  }

  @Before
  public void before() {
    l1 = new Lab1();
  }

  @Parameters
  public static Collection<String[]> caseData() {
    File file = new File("WhiteTest.cvs");
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(file));
      List l = new ArrayList<String[]>();
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.split(",").length > 0)
          l.add(s.split(","));
      }
      return l;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;

  }

  @Test
  public void testExpression() {
    l1.lz = l1.setTable(request);
    if (request.equals("testNull"))
      l1.lz.head = null;

    System.out.println(l1.expression(l1.lz));
    assertEquals(expect, l1.expression(l1.lz));
  }

}
