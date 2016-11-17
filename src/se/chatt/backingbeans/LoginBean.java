package se.chatt.backingbeans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import se.chatt.DAO.User;
import se.chatt.EJB.interfaces.UserEJBLocal;
import se.chatt.hashing.PasswordHashing;


@Named(value="loginBean")
@RequestScoped
public class LoginBean {
	
	@EJB
	private UserEJBLocal userEJB;
	
	private String textField;
	private String userName;
	private String password;
	
	public String login() {
		User userFromDB =  userEJB.getUserByUserName(userName);
		
		if(userFromDB != null){
			
			
			if(PasswordHashing.validatePassword(password, userFromDB.getPassword())){
				
			}
			
			
		}
		
		
		/*YubicoClient client = YubicoClient.getClient(30750, "OpEjOSsfeX7l1fg9hdJKViWL6xo=");

		VerificationResponse response;
		try {
			response = client.verify(textField);
			if(response.isOk()){
				System.out.println("FINE");
			}else{
				System.out.println("BLÄÄÄÄÄ");
			}
		} catch (YubicoVerificationException | YubicoValidationFailure e) {
			e.printStackTrace();
		}*/
		
			
		
		return "";
	}
	
	public String getTextField() {
		return textField;
	}
	
	public void setTextField(String textField) {
		this.textField = textField;
	}
}
