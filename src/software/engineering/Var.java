package software.engineering;

public class Var implements Comparable<Var> {
  private String name;// The name of the Variable
  private int power;// The power of the Variable
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }

  /*
 　　* compareTo The method inherit from the interface "Comparable" Compare the
 　　* <Var> type by name of the variable
  */
  /**
   * {@author you}.
   * */
  
  public int compareTo(Var x1) {
    return Character.compare(this.name.charAt(0), x1.name.charAt(0));
  }

  /*
   * stringFormat return the string format of the variable example: x on power
   * of 4 => x*x*x*x
   */
  String stringFormat() {
    StringBuffer x1 = new StringBuffer();
    for (int i = 0; i < this.power; i++) {
      x1.append(name);
      x1.append("*");
    }
    x1.deleteCharAt(x1.length() - 1);
    return x1.toString();
  }

  /*
   * Constructor constructs a new variable of the specified name and power
   */
  Var(String name,final int power) {
    this.name = name;
    this.power = power;
  }

}