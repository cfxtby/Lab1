package BookManager;
import com.opensymphony.xwork2.ActionSupport;
public class HelloWorld extends BookManagerSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE = "HelloWorld.message";
	
	public String execute() throws Exception{
		setMessage(getText(MESSAGE));
		return SUCCESS;
	}
	
	public String message;
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
