package se.chatt.backingbeans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import se.chatt.DAO.User;
import se.chatt.DAO.Yubico;
import se.chatt.EJB.interfaces.UserEJBLocal;
import se.chatt.hashing.PasswordHashing;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -3876285026587562903L;

	@EJB
	private UserEJBLocal userEJB;

	private String otp;
	private String userName;
	private String password;

	private User loggedInUser;
	private boolean isLoggedIn;

	public String login() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User userFromDB = userEJB.getUserByUserName(userName);
		isLoggedIn = false;
		if (userFromDB != null) {
			if (PasswordHashing.validatePassword(password, userFromDB)) {
				VerificationResponse response;
				if(userFromDB.getKeyId() != null && YubicoClient.isValidOTPFormat(otp)){
					try {
						response = Yubico.getClient().verify(otp);
						if (response.isOk()) {
							String yubikeyId = YubicoClient.getPublicId(otp);
							if (userFromDB.getKeyId().equals(yubikeyId)) {
								System.out.println("Sussec!!!!!");
								isLoggedIn = true;
								
							}
						}
					} catch (YubicoVerificationException | YubicoValidationFailure e) {
						e.printStackTrace();
					}
				}else if(userFromDB.getKeyId() == null){
					isLoggedIn = true;
				}
			}

		}
		if (!isLoggedIn) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage("Wrong username, password or invalid key!");
			context.addMessage("userName", message);
		}else{
			loggedInUser = userFromDB;
			return "/secured/chat.xhtml?faces-redirect=true";
		}

		return "";
	}
	
	public String goToCreat(){
		return "/create-user.xhtml?faces-redirect=true";
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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
