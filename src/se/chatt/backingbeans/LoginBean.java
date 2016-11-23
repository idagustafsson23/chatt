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
	private boolean isLoggedIn;
	
	public String login() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User userFromDB =  userEJB.getUserByUserName(userName);
		isLoggedIn = false;
		if(userFromDB != null){
			if(PasswordHashing.validatePassword(password, userFromDB.getPassword())){
				isLoggedIn = true;
				loggedInUser = userFromDB;
				return "/secured/chat.xhtml?faces-redirect=true";
			}
			
		}
		if(!isLoggedIn){
			//fel lösen eller användarnamn
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

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
