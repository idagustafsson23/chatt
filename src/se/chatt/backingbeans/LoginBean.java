package se.chatt.backingbeans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import se.chatt.DAO.User;
import se.chatt.EJB.interfaces.UserEJBLocal;
import se.chatt.hashing.PasswordHashing;


@Named(value="loginBean")
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = -3876285026587562903L;

	@EJB
	private UserEJBLocal userEJB;
	
	private String textField;
	private String userName;
	private String password;
	
	private User loggedInUser;
	
	public String login() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User userFromDB =  userEJB.getUserByUserName(userName);
		
		if(userFromDB != null){
			if(PasswordHashing.validatePassword(password, userFromDB.getPassword())){
				//inloggad
				
				loggedInUser = userFromDB;
				System.out.println("Du är inloggad! hurra hurra!" + userFromDB.getUserName());
				//TODO: Navigate to chat main page;
			}else{
				//fel lösenord
				//TODO: Return message wrong username/password
			}
			
		}else{
			//fel användarnamn
			//TODO: Return message wrong username/password
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
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String getTextField() {
		return textField;
	}
	
	public void setTextField(String textField) {
		this.textField = textField;
	}
}
