package homeWork1;

import java.util.Scanner;

public class Boundary {
	
	private Scanner reader;
	

	public String getCommond() {
		
		String commond = reader.nextLine();
		return commond;
	}
	
	Boundary(){
		this.reader = new Scanner(System.in);
	}
	
	public void close(){
		this.reader.close();
	}
	
	
}
